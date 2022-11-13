package br.com.developcorporation.collaborator.rest.infrastructure;

import br.com.developcorporation.collaborator.rest.validation.AuthorizationValidator;
import br.com.developcorporation.collaborator.domain.infrastructure.ContextHolder;
import br.com.developcorporation.collaborator.domain.infrastructure.RequestContext;
import br.com.developcorporation.collaborator.core.service.AuthorizationService;
import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ContextHandleInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(ContextHandleInterceptor.class);

    private AuthorizationService service;

    private AuthorizationValidator validator;

    public ContextHandleInterceptor(final AuthorizationService service, final  AuthorizationValidator validator){
        this.service = service;
        this.validator = validator;
    }

    @SneakyThrows
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)  {

        final RequestContext requestContext = new RequestContext();

        requestContext.setClientId(request.getHeader(FieldConstant.CLIENT_ID));
        requestContext.setClientSecret(request.getHeader(FieldConstant.CLIENT_SECRET));
        requestContext.setRequestUri(request.getRequestURI());
        requestContext.setRequestUriParameterString(setParameters(request.getParameterMap()));
        requestContext.setRequestUriHeaders(setHeaders(request));
        requestContext.setMethod(request.getMethod());
        requestContext.setCorrelationId(request.getHeader(FieldConstant.CURRENTCORRELATION_ID));
        requestContext.setInstanceId(InetAddress.getLocalHost().getHostName());


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
}
