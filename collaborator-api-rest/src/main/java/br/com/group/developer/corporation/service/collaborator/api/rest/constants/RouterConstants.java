package br.com.group.developer.corporation.service.collaborator.api.rest.constants;

public class RouterConstants {
    private RouterConstants(){}

    public static final String APPLICATION_JSON = "application/json";
    private static final String PREFIX_API = "api/";
    public static final String ROUTER_AUTHENTICATION = "auth";
    public static final String COLLABORATOR = "colaborador";
    public static final String STATUS = "situacao";
    public static final String TIPO_USUARIO = "tipo_usuario";
    public static final String ROUTER_COLLABORATOR = PREFIX_API + COLLABORATOR ;
    public static final String ROUTER_STATUS = PREFIX_API + STATUS;
    public static final String ROUTER_TIPO_USUARIO = PREFIX_API + TIPO_USUARIO;
}
