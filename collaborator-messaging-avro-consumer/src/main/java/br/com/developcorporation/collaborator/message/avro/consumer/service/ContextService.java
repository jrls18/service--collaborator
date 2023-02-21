package br.com.developcorporation.collaborator.message.avro.consumer.service;

import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.RequestContext;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

@Service
public class ContextService {

    @SneakyThrows
    public void context(final String applicationName, final String correlationId, final String topic){
        final RequestContext requestContext = new RequestContext();
        requestContext.setApplicationName(applicationName);
        requestContext.setCorrelationId(correlationId);
        requestContext.setInstanceId(InetAddress.getLocalHost().getHostName());
        requestContext.setMethod("onReceive");
        requestContext.setRequestUri(topic);
        ContextHolder.set(requestContext);
    }
}
