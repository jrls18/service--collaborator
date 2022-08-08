package br.com.developcorporation.collaborator.services.internal.service.impl;

import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.services.internal.constant.MessageConstants;
import br.com.developcorporation.collaborator.services.internal.model.CompanyModel;
import br.com.developcorporation.collaborator.services.internal.service.CompanyService;
import br.com.developcorporation.collaborator.services.internal.service.httpUtils.HttpUtil;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    @Value(value = "${url.api.company.host}")
    private String urlHost;

    private final HttpUtil httpUtil;

    private final RestTemplate restTemplate;

    @Retry(name = "serviceRetry", fallbackMethod = "fallback")
    @Override
    public CompanyModel consultaPorCodigoEmpresa(Long id) {
        CompanyModel companyModel = null;
        try {

            log.info("001 - Modulo -> Service-Internal { Inicio da consulta da empresa }");

            ResponseEntity<CompanyModel> response = restTemplate.exchange(
                    urlHost.concat(id.toString()), HttpMethod.GET, httpUtil.getRequestEntityBase(), CompanyModel.class);

            companyModel = response.getBody();

            log.info("002 - Modulo -> Service-Internal { Fim da consulta da empresa }");

        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND) ||
                    e.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)){
                log.info("003 - Modulo -> Service-Internal { Falha na consulta do service--company }");
                throw new DomainException(
                        MessageConstants.CODE,
                        MessageConstants.MICROSERICE_DA_EMPRESA_ENCONTRA_SE_FORA_OU_ROTA_NAO_EXISTE,
                        null);
            }
        }
        return companyModel;
    }

    private CompanyModel fallback(Long id, RuntimeException runtimeException) {
        log.error("Falha ao fazer a requisição com a api do service--company detalhes do erro: " + runtimeException.getMessage());
        log.error("004 - Modulo -> Service-Internal { Fim da consulta da empresa. (fallback) }");
        return null;
    }
}
