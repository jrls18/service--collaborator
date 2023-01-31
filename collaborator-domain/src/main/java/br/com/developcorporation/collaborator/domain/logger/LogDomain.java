package br.com.developcorporation.collaborator.domain.logger;

import br.com.developcorporation.collaborator.domain.infrastructure.ContextHolder;

import br.com.grupo.developer.corporation.lib.logger.monitorable.SpringLogger;
import br.com.grupo.developer.corporation.lib.logger.service.CryptographyService;
import br.com.grupo.developer.corporation.libcommons.utils.Convert;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class LogDomain {

    @Value("${spring.application.name}")
    private String applicationName;

    public String jsonLogInfo(final Object payload, final int statusCode, final String message){

       return Convert.toJson( new SpringLogger(
               ContextHolder.get().getCorrelationId(),
               applicationName,
               ContextHolder.get().getMethod(),
               ContextHolder.get().getRequestUri(),
               ContextHolder.get().getRequestUriParameterString(),
               ContextHolder.get().getRequestUriHeaders(),
               CryptographyService.mapCryptography(payload),
               StringUtils.EMPTY,
               ContextHolder.get().getInstanceId(),
                "INFO",
               String.valueOf(statusCode),
               message));
    }

    public String jsonLogInfo(final Object payload, final String message){

        return Convert.toJson( new  SpringLogger(
                ContextHolder.get().getCorrelationId(),
                applicationName,
                ContextHolder.get().getMethod(),
                ContextHolder.get().getRequestUri(),
                ContextHolder.get().getRequestUriParameterString(),
                ContextHolder.get().getRequestUriHeaders(),
                CryptographyService.mapCryptography(payload),
                StringUtils.EMPTY,
                ContextHolder.get().getInstanceId(),
                "INFO",
                StringUtils.EMPTY,
                message));
    }

    public String jsonLogInfoParams(final Object payload, final String message){

        return Convert.toJson( new  SpringLogger(
                ContextHolder.get().getCorrelationId(),
                applicationName,
                ContextHolder.get().getMethod(),
                ContextHolder.get().getRequestUri(),
                ContextHolder.get().getRequestUriParameterString(),
                ContextHolder.get().getRequestUriHeaders(),
                payload,
                StringUtils.EMPTY,
                ContextHolder.get().getInstanceId(),
                "INFO",
                StringUtils.EMPTY,
                message));
    }

    @SneakyThrows
    public String springLogger(final Object value, final String info, final String statusCode, final Exception ex){
        return   Convert.toJson(new SpringLogger(
                ContextHolder.get().getCorrelationId(),
                applicationName,
                ContextHolder.get().getMethod(),
                ContextHolder.get().getRequestUri(),
                ContextHolder.get().getRequestUriParameterString(),
                ContextHolder.get().getRequestUriHeaders(),
                CryptographyService.mapCryptography(value),
                ex,
                ContextHolder.get().getInstanceId(),
                info,
                statusCode
        ));
    }

    @SneakyThrows
    public String setLogger(final Object value, final String info, final String statusCode, final Exception ex){
       return   Convert.toJson(

                new SpringLogger(
                        ContextHolder.get().getCorrelationId(),
                        applicationName,
                        ContextHolder.get().getMethod(),
                        ContextHolder.get().getRequestUri(),
                        ContextHolder.get().getRequestUriParameterString(),
                        ContextHolder.get().getRequestUriHeaders(),
                        value,
                        ex,
                        ContextHolder.get().getInstanceId(),
                        info,
                        statusCode
                )
        );
    }



}
