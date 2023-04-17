package br.com.developcorporation.collaborator.message.avro.produce.mapper;

import br.com.developcorporation.collaborator.domain.message.Notification;
import br.com.group.developer.corporation.push.message.PushMessage;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PushNotificationMapper {

    PushNotificationMapper INSTANCE = Mappers.getMapper(PushNotificationMapper.class);

    @Mapping(source = "obj", target = "pushNotification")
    PushMessage toAvro(MessageAsync<Notification> domain);

}
