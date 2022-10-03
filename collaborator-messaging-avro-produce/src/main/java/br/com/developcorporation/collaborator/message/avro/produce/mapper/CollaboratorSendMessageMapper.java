package br.com.developcorporation.collaborator.message.avro.produce.mapper;

import br.com.developcorporation.collaborator.domain.message.CollaboratorMessage;
import br.com.developcorporation.collaborator.message.avro.Colaborador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CollaboratorSendMessageMapper {

    CollaboratorSendMessageMapper INSTANCE = Mappers.getMapper(CollaboratorSendMessageMapper.class);


    @Mapping(source = "message.detailsList", target = "message.details")
    Colaborador toAvro(CollaboratorMessage collaboratorMessage);
}
