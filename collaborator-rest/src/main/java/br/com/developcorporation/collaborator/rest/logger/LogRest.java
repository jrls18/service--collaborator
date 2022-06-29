package br.com.developcorporation.collaborator.rest.logger;

import br.com.developcorporation.collaborator.core.infrastructure.ContextHolder;
import br.com.developcorporation.lib.commons.monitorable.SpringLogger;
import br.com.developcorporation.lib.commons.util.Convert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class LogRest  {

    public String jsonLogInfo(final Object payload, final int statusCode, final String message){

       return Convert.toJson( new  SpringLogger(
               ContextHolder.get().getCorrelationId(),
        "company",
               ContextHolder.get().getMethod(),
               ContextHolder.get().getRequestUri(),
               payload,
               StringUtils.EMPTY,
               ContextHolder.get().getInstanceId(),
                "INFO",
               String.valueOf(statusCode),
               message));
    }

    public String jsonLogInfo(final Object payload, final String message){

        return Convert.toJson( new  SpringLogger(
                ContextHolder.get().getCorrelationId(),
                "company",
                ContextHolder.get().getMethod(),
                ContextHolder.get().getRequestUri(),
                payload,
                StringUtils.EMPTY,
                ContextHolder.get().getInstanceId(),
                "INFO",
                StringUtils.EMPTY,
                message));
    }
}
