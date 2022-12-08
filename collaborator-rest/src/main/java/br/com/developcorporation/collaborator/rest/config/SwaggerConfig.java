package br.com.developcorporation.collaborator.rest.config;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

import static springfox.documentation.builders.PathSelectors.regex;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiInfo apiInfo() {
        return new ApiInfo("MyApp Rest APIs",
                "APIs for MyApp.",
                "1.0",
                "Terms of service",
                new Contact("test", "www.org.com", "test@emaildomain.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }



    @Bean
    public Docket api() {

        ParameterBuilder aParameterBuilder = new ParameterBuilder();

        List<Parameter> aParameters = new ArrayList<>();
        aParameterBuilder.name(FieldConstant.CURRENTCORRELATION_ID)
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .description("trace id")
                .build();
        aParameters.add(aParameterBuilder.build());

        ParameterBuilder aParameterBuilde = new ParameterBuilder();
        aParameterBuilde.name("Authorization")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .description("Bearer {token}")
                .build();
        aParameters.add(aParameterBuilde.build());


        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("/v1")
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(apiKey())
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.developcorporation.collaborator.rest.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(aParameters);
    }

    private List<SecurityScheme> basicScheme() {
        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(new BasicAuth("basicAuth"));
        return schemeList;
    }

    private List<ApiKey> apiKey() {

        List<ApiKey> apiKeyList = new ArrayList<>(2);

        apiKeyList.add(new ApiKey(FieldConstant.CLIENT_ID, FieldConstant.CLIENT_ID, "header"));
        apiKeyList.add(new ApiKey(FieldConstant.CLIENT_SECRET, FieldConstant.CLIENT_SECRET, "header"));

        return apiKeyList;
        //return new ApiKey(AUTHORIZATION_HEADER, "JWT", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("apiKey",
                authorizationScopes));
    }
}
