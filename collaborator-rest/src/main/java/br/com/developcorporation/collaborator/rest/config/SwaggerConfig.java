package br.com.developcorporation.collaborator.rest.config;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfo("Management Collaborator Rest APIs",
                "APIs for Collaborator",
                "1.0.0",
                null,
                null,
                "Repositório do git",
                "https://gitlab.com/software-engineer2/service-management-collaborator",
                Collections.emptyList());
    }

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(apiKey())
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.developcorporation.collaborator.rest.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameterList())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .globalResponseMessage(RequestMethod.PUT, responseMessages())
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, responseMessages())
                .globalResponseMessage(RequestMethod.PATCH, responseMessages());
    }

    private List<ResponseMessage> responseMessages(){
        List<ResponseMessage> responseMessages = new ArrayList<>();

        responseMessages.add(new ResponseMessageBuilder()
                .code(400)
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .responseModel(new ModelRef("MessageResponse")).build()
        );

        responseMessages.add(new ResponseMessageBuilder()
                .code(401)
                .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .responseModel(new ModelRef("MessageResponse")).build()
        );

        responseMessages.add(new ResponseMessageBuilder()
                .code(403)
                .message(HttpStatus.FORBIDDEN.getReasonPhrase())
                .responseModel(new ModelRef("MessageResponse"))
                .build()

        );

        responseMessages.add(new ResponseMessageBuilder()
                .code(422)
                .message(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .responseModel(new ModelRef("MessageResponse")).build()
        );

        responseMessages.add(new ResponseMessageBuilder()
                .code(500)
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .responseModel(new ModelRef("MessageResponse")).build()
        );

        return responseMessages;
    }


    private List<Parameter> parameterList(){
        List<Parameter> aParameters = new ArrayList<>(2);

        aParameters.add(aParameterBuilder(
                FieldConstant.CURRENTCORRELATION_ID,
                "string",
                "header",
                true,
                "trace id"));

        aParameters.add(aParameterBuilder(
                "Authorization",
                "string",
                "header",
                true,
                "Bearer {token}"));

        return aParameters;
    }


    private Parameter aParameterBuilder(
            final String fieldName,
            final String type,
            final String parameterType,
            final boolean required,
            final String description ){

        return new ParameterBuilder().name(fieldName).description(description)
                .modelRef(new ModelRef(type))
                .parameterType(parameterType)
                .required(required)
                .build();

    }

    private List<SecurityScheme> apiKey() {

        List<SecurityScheme> securityScheme = new ArrayList<>(2);
        securityScheme.add(new ApiKey(FieldConstant.CLIENT_ID, FieldConstant.CLIENT_ID, "header"));
        securityScheme.add(new ApiKey(FieldConstant.CLIENT_SECRET, FieldConstant.CLIENT_SECRET, "header"));

        return securityScheme;
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

   private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        List<SecurityReference> references = new ArrayList<>(2);
        references.add(new SecurityReference(FieldConstant.CLIENT_ID, authorizationScopes));
        references.add(new SecurityReference(FieldConstant.CLIENT_SECRET, authorizationScopes));

        return references;
    }

}
