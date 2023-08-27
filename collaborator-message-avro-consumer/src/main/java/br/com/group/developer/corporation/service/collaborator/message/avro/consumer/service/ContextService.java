package br.com.group.developer.corporation.service.collaborator.message.avro.consumer.service;

import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.RequestContext;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class ContextService {

    @SneakyThrows
    public void context(final String applicationName, MessageAsync<?> message, final String topic, final boolean isReturn){
        final RequestContext requestContext = new RequestContext();
        requestContext.setApplicationName(applicationName);
        requestContext.setCorrelationId(message.getCorrelationId());
        requestContext.setInstanceId(InetAddress.getLocalHost().getHostName());
        requestContext.setMethod("onReceive");
        requestContext.setTopicName(topic);
        requestContext.setMap("originSystem", message.getOriginSystem());
        requestContext.setMap("postDateTime", message.getPostDateTime());
        requestContext.setMap("dateTimeStartProcessing",message.getDateTimeStartProcessing());

        if(isReturn)
            requestContext.setMap("dateTimeEndProcessing",message.getDateTimeEndProcessing());

        ContextHolder.set(requestContext);
    }

    public void remove(){
        ContextHolder.remove();
    }
}
