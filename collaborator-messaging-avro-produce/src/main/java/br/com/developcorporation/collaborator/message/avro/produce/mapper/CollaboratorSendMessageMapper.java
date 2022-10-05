package br.com.developcorporation.collaborator.message.avro.produce.mapper;

import br.com.developcorporation.collaborator.domain.message.CollaboratorMessage;
import br.com.developcorporation.collaborator.message.avro.Colaborador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CollaboratorSendMessageMapper {

    CollaboratorSendMessageMapper INSTANCE = Mappers.getMapper(CollaboratorSendMessageMapper.class);


    @Mapping(source = "collaborator", target = "collaborator")
    @Mapping(source = "messageControl", target = "messageControl")
    @Mapping(source = "message", target = "message")
    Colaborador toAvro(CollaboratorMessage collaboratorMessage);
}
