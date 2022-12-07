package br.com.developcorporation.collaborator.rest.config;

import br.com.developcorporation.collaborator.rest.infrastructure.ContextHandleInterceptor;
import br.com.developcorporation.collaborator.rest.validation.AuthorizationValidator;
import br.com.developcorporation.collaborator.core.service.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@AllArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final AuthorizationService service;
    private final AuthorizationValidator validator;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ContextHandleInterceptor(service, validator))
                .excludePathPatterns(AntMatchersConstants.AUTH_URL_SWAGGER_WHITELIST);
    }
}
