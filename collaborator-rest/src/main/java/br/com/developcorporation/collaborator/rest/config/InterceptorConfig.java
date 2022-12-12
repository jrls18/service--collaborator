package br.com.developcorporation.collaborator.rest.config;

import br.com.developcorporation.collaborator.rest.infrastructure.ContextHandleInterceptor;
import br.com.developcorporation.collaborator.rest.validation.AuthorizationValidator;
import br.com.developcorporation.collaborator.core.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(enableSwagger){
            registry.addInterceptor(new ContextHandleInterceptor(service, validator))
                    .excludePathPatterns(AntMatchersConstants.AUTH_URL_SWAGGER_WHITELIST);
        }else {
            registry.addInterceptor(new ContextHandleInterceptor(service, validator));
        }
    }
}
