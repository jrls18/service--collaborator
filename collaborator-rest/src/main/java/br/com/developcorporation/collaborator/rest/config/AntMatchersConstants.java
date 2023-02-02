package br.com.developcorporation.collaborator.rest.config;

public final class AntMatchersConstants {

    private AntMatchersConstants(){}

    public static final String[] AUTH_URL_SERVICE_WHITELIST = {
            "/auth/v1/**",
            "/api/colaborador/internal/**",
            "/actuator/**"
    };

    public static final String[] AUTH_URL_SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/csrf",
            "/"
    };

    public static final String[] AUTH_URL_H2_WHITELIST = {
            "/h2-console/**",
            "/console/**"
    };
}
