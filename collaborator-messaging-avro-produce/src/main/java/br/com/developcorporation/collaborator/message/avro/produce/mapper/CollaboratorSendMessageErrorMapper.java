package br.com.developcorporation.collaborator.message.avro.produce.mapper;

import br.com.developcorporation.collaborator.domain.exception.DomainException;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CollaboratorSendMessageErrorMapper {

    CollaboratorSendMessageErrorMapper INSTANCE = Mappers.getMapper(CollaboratorSendMessageErrorMapper.class);

    br.com.developcorporation.collaborator.message.avro.CollaboratorMessageError toAvro(DomainException domainException);
}
