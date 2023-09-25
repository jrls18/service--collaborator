package br.com.group.developer.corporation.service.collaborator.message.avro.produce.mapper;

import br.com.group.developer.corporation.push.message.PushNewCollaboratorAndRecoverNotification;
import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PushNewCollaboratorAndRecoverPasswordNotificationMapper {

    PushNewCollaboratorAndRecoverPasswordNotificationMapper INSTANCE = Mappers.getMapper(PushNewCollaboratorAndRecoverPasswordNotificationMapper.class);

    @Mapping(source = "obj", target = "collaborator")
    PushNewCollaboratorAndRecoverNotification toAvro(MessageAsync<Collaborator> message);

}
