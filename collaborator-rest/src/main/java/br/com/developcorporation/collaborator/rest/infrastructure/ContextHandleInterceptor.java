package br.com.developcorporation.collaborator.rest.infrastructure;

import br.com.developcorporation.collaborator.rest.validation.AuthorizationValidator;

import br.com.developcorporation.collaborator.core.service.AuthorizationService;
import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.RequestContext;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Log4j2
public class ContextHandleInterceptor implements HandlerInterceptor {

    private AuthorizationService service;

    private AuthorizationValidator validator;

    private String applicationName;

    public ContextHandleInterceptor(final AuthorizationService service,
                                    final AuthorizationValidator validator,
                                    final String applicationName){
        this.service = service;
        this.validator = validator;
        this.applicationName = applicationName;
    }

    @SneakyThrows
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)  {

        if(Boolean.TRUE.equals(request.getRequestURI().contains("/error")))
            return true;

        final RequestContext requestContext = new RequestContext();

        requestContext.setClientId(request.getHeader(FieldConstant.CLIENT_ID));
        requestContext.setClientSecret(request.getHeader(FieldConstant.CLIENT_SECRET));
        requestContext.setRequestUri(request.getRequestURI());
        requestContext.setRequestUriParameterString(setParameters(request.getParameterMap()));
        requestContext.setRequestUriHeaders(setHeaders(request));
        requestContext.setMethod(request.getMethod());
        requestContext.setCorrelationId(request.getHeader(FieldConstant.CURRENTCORRELATION_ID));
        requestContext.setInstanceId(InetAddress.getLocalHost().getHostName());
        requestContext.setApplicationName(applicationName);

        requestContext.setPathVariable(
                setPathVariable(
                        request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)));

        ContextHolder.set(requestContext);

        validator.validClientIdAndClientSecret(request.getHeader(FieldConstant.CLIENT_ID), request.getHeader(FieldConstant.CLIENT_SECRET));

        validator.validCorrelationId(request.getHeader(FieldConstant.CURRENTCORRELATION_ID));

        service.isAuthentication();

        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, @Nullable final Exception ex)  {
        ContextHolder.remove();
    }

    private Map<String, String> setHeaders(HttpServletRequest request) {

        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);

            if(key.equalsIgnoreCase("Authorization")
                    || key.equalsIgnoreCase("client_id")
                    || key.equalsIgnoreCase("client_secret"))
                value = "********";

            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> setParameters(Map<String, String[]> value){
        if(value.isEmpty())
            return null;

        Map<String, String> map = new HashMap<>();

        for(Map.Entry<String, String[]> line : value.entrySet()){
            map.put(line.getKey(), line.getValue()[0]);
        }
        return map;
    }

    private Map<String, String> setPathVariable(Object obj){
        Map<String, String> pathVariables = null;

        if(Objects.nonNull(obj)) {
            pathVariables = (Map) obj;

            if(pathVariables.isEmpty())
                return null;
        }
        return pathVariables;
    }
}
