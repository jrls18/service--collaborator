package br.com.group.developer.corporation.service.collaborator.api.rest.config;

import br.com.group.developer.corporation.service.collaborator.api.rest.infrastructure.ContextHandleInterceptor;
import br.com.group.developer.corporation.service.collaborator.api.rest.validation.AuthorizationValidator;
import br.com.group.developer.corporation.service.collaborator.core.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final AuthorizationService service;
    private final AuthorizationValidator validator;

    public InterceptorConfig(AuthorizationService service, AuthorizationValidator validator){
        this.service = service;
        this.validator = validator;
    }

    @Value("${prop.swagger.enabled:false}")
    private boolean enableSwagger;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(enableSwagger){
            registry.addInterceptor(new ContextHandleInterceptor(service, validator, applicationName))
                    .excludePathPatterns(AntMatchersConstants.AUTH_URL_SWAGGER_WHITELIST);
        }else {
            registry.addInterceptor(new ContextHandleInterceptor(service, validator,applicationName));
        }
    }
}
