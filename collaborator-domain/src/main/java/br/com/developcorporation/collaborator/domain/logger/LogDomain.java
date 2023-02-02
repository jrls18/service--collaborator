package br.com.developcorporation.collaborator.domain.logger;


import br.com.grupo.developer.corporation.lib.logger.logger.Logger;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor
public class LogDomain {

    @Value("${spring.application.name}")
    private String applicationName;

    public String jsonLogInfo(final Object payload, final int statusCode, final String message){

       return Logger.info(payload, statusCode, message);

    }

    public String jsonLogInfo(final Object payload, final String message){

        return Logger.info(payload, message);
    }

    public String jsonLogInfoParams(final Object payload, final String message){

        return Logger.info(payload, message);
    }

    @SneakyThrows
    public String springLogger(final Object value, final String info, final String statusCode, final Exception ex){
        return  Logger.severe(value, info, statusCode,ex);
    }

    @SneakyThrows
    public String setLogger(final Object value, final String info, final String statusCode, final Exception ex){
       return   Logger.severe(value,info,statusCode,ex);

    }



}
