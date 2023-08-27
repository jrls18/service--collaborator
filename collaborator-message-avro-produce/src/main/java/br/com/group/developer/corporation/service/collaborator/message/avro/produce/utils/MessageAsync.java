package br.com.group.developer.corporation.service.collaborator.message.avro.produce.utils;

import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class MessageAsync<T> {

    protected br.com.grupo.developer.corporation.libcommons.message.MessageAsync<T> setMessage(final T obj){
        if(Objects.isNull(obj))
            return null;

        br.com.grupo.developer.corporation.libcommons.message.MessageAsync<T> message = new br.com.grupo.developer.corporation.libcommons.message.MessageAsync<>();
        message.setCorrelationId(ContextHolder.get().getCorrelationId());
        message.setPostDateTime(LocalDateTime.now().toString());
        message.setOriginSystem(ContextHolder.get().getApplicationName());
        message.setObj(obj);

        return message;
    }
}
