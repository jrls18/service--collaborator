package br.com.developcorporation.collaborator.domain.logger;


import br.com.grupo.developer.corporation.lib.logger.logger.Logger;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor
public class LogDomain {


    @SneakyThrows
    public String springLogger(final Object value, final String info, final String statusCode, final Exception ex){
        return  Logger.severe(value, info, statusCode,ex);
    }

    @SneakyThrows
    public String setLogger(final Object value, final String info, final String statusCode, final Exception ex){
       return   Logger.severe(value,info,statusCode,ex);

    }



}
