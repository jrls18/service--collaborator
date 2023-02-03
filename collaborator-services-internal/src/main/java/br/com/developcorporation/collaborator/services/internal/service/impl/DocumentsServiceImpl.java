package br.com.developcorporation.collaborator.services.internal.service.impl;

import br.com.developcorporation.collaborator.services.internal.constant.MessageConstants;
import br.com.developcorporation.collaborator.services.internal.model.DocumentsModel;
import br.com.developcorporation.collaborator.services.internal.service.DocumentsService;
import br.com.developcorporation.collaborator.services.internal.service.httpUtils.HttpUtil;
import br.com.grupo.developer.corporation.libcommons.exception.DomainException;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentsServiceImpl implements DocumentsService {

    @Value(value = "${url.api.documents.host}")
    private String urlHost;

    private final HttpUtil httpUtil;

    private final RestTemplate restTemplate;

    @Retry(name = "serviceRetry", fallbackMethod = "fallback")
    @Override
    public DocumentsModel consultaImagem(final String idCompany, final String nomeImage) {
        DocumentsModel imageModel = null;
        try {

            log.info("001 - Modulo -> Service-Internal { Inicio da consulta da documents }");

            String urlDocuments =  idCompany.concat("/catalog/1/document=").concat(nomeImage);

            ResponseEntity<DocumentsModel> response = restTemplate.exchange(
                    urlHost.concat(urlDocuments), HttpMethod.GET, httpUtil.getRequestEntityBase(), DocumentsModel.class);

            imageModel = response.getBody();

            log.info("002 - Modulo -> Service-Internal { Fim da consulta da documents }");

        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND) ||
                    e.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)){
                log.info("003 - Modulo -> Service-Internal { Falha na consulta do service--documents }");
                throw new DomainException(
                        MessageConstants.CODE,
                        MessageConstants.MICROSERICE_DA_DOCUMENTS_ENCONTRA_SE_FORA_OU_ROTA_NAO_EXISTE,
                        null);
            }
        }
        return imageModel;
    }

    private DocumentsModel fallback(final String idCompany, final String nomeImage, RuntimeException runtimeException) {
        log.error("Falha ao fazer a requisição com a api do service--documents detalhes do erro: " + runtimeException.getMessage());
        log.error("004 - Modulo -> Service-Internal { Fim da consulta da imagem. (fallback) }");
        return null;
    }
}
