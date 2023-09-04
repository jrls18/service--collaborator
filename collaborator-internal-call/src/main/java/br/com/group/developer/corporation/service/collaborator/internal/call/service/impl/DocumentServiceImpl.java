package br.com.group.developer.corporation.service.collaborator.internal.call.service.impl;

import br.com.group.developer.corporation.libparametrizador.schedule.ParameterizeService;
import br.com.group.developer.corporation.service.collaborator.domain.constants.ParametrizeConstants;
import br.com.group.developer.corporation.service.collaborator.internal.call.model.DocumentCallResponse;
import br.com.group.developer.corporation.service.collaborator.internal.call.service.DocumentService;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import br.com.grupo.developer.corporation.libcommons.constants.FieldAssistantConstants;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {


    private final WebClient webClient;

    private final ParameterizeService parameterizeService;


    @Retry(name = "callGetImageCompanyServiceDocument")
    @Override
    public DocumentCallResponse getByCompanyDocumentImage(String idCompany, String nomeImage) {

        if(StringUtils.isEmpty(idCompany) || StringUtils.isEmpty(nomeImage))
            return null;

        boolean enabledServiceExternal = Boolean.parseBoolean(parameterizeService.getPropertiesString(ParametrizeConstants.ENABLED_SERVICE_EXTERNAL_HOST));

        String urlHost;

        if(Boolean.TRUE.equals(enabledServiceExternal))
            urlHost = parameterizeService.getPropertiesString(ParametrizeConstants.SERVICE_DOCUMENT_EXTERNAL_HOST);
        else
            urlHost = parameterizeService.getPropertiesString(ParametrizeConstants.SERVICE_DOCUMENT_INTERNAL_HOST);


        return  webClient.get()
                .uri(urlHost.concat("/documents/v1/image/base64/company/".concat(idCompany.concat("/catalog/1/document=").concat(nomeImage))))
                .header(FieldAssistantConstants.CURRENTCORRELATION_ID, ContextHolder.get().getCorrelationId())
                .retrieve()
                .bodyToMono(DocumentCallResponse.class)
                .onErrorReturn(new DocumentCallResponse())
                .block();
    }
}
