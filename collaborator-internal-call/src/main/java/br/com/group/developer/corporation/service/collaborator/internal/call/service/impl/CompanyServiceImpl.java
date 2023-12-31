package br.com.group.developer.corporation.service.collaborator.internal.call.service.impl;

import br.com.group.developer.corporation.libparametrizador.schedule.ParameterizeService;
import br.com.group.developer.corporation.service.collaborator.domain.constants.ParametrizeConstants;
import br.com.group.developer.corporation.service.collaborator.internal.call.model.CompanyCallResponse;
import br.com.group.developer.corporation.service.collaborator.internal.call.service.CompanyService;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import br.com.grupo.developer.corporation.libcommons.constants.FieldAssistantConstants;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {

    private final WebClient webClient;

    private final ParameterizeService parameterizeService;

    @Retry(name = "callGetCompanyIdServiceCompany")
    @Override
    public CompanyCallResponse getByCompanyId(Long id) {

        if(Objects.isNull(id) || id <= 0)
            return null;

        boolean enabledServiceExternal = Boolean.parseBoolean(parameterizeService.getPropertiesString(ParametrizeConstants.ENABLED_SERVICE_EXTERNAL_HOST));

        String urlHost;

        if(Boolean.TRUE.equals(enabledServiceExternal))
            urlHost = parameterizeService.getPropertiesString(ParametrizeConstants.SERVICE_COMPANY_EXTERNAL_HOST);
        else
            urlHost = parameterizeService.getPropertiesString(ParametrizeConstants.SERVICE_COMPANY_INTERNAL_HOST);

        return  webClient.get()
                .uri(urlHost.concat("/empresa/v1/get/id=").concat(id.toString()))
                .header(FieldAssistantConstants.CURRENTCORRELATION_ID, ContextHolder.get().getCorrelationId())
                .retrieve()
                .bodyToMono(CompanyCallResponse.class)
                .onErrorResume(Mono::error)
                .block();
    }
}
