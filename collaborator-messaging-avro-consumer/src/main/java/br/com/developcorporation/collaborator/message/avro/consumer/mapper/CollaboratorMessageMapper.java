package br.com.developcorporation.collaborator.message.avro.consumer.mapper;

import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.message.avro.Colaborador;
import br.com.developcorporation.collaborator.message.avro.consumer.constans.DataConstant;
import br.com.developcorporation.collaborator.domain.message.CollaboratorMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CollaboratorMessageMapper {
    CollaboratorMessageMapper INSTANCE = Mappers.getMapper(CollaboratorMessageMapper.class);

    @Mapping(source = "collaborator.birthDate", target = "birthDate", dateFormat = DataConstant.DATA_FORMAT)
    @Mapping(source = "collaborator.mainPhone", target = "contact.mainPhone")
    @Mapping(source = "collaborator.telephone", target = "contact.telephone")
    @Mapping(source = "collaborator.email", target = "contact.email")

    @Mapping(source = "collaborator.id", target = "id")

    @Mapping(source = "collaborator.typeUser", target = "typeCollaborator.id")
    @Mapping(source = "collaborator.zipCode", target = "address.zipCode")
    @Mapping(source = "collaborator.publicPlace", target = "address.address")
    @Mapping(source = "collaborator.neighborhood", target = "address.neighborhood")
    @Mapping(source = "collaborator.location", target = "address.location")
    @Mapping(source = "collaborator.state", target = "address.state")
    @Mapping(source = "collaborator.number", target = "address.number")
    @Mapping(source = "collaborator.complement", target = "address.complement")
    Collaborator toDomain(final CollaboratorMessage request);


    CollaboratorMessage toDomainAvro(final Colaborador colaborador);

    @Mapping(source = "details", target = "detailsList")
    CollaboratorMessage.Message toMessage(final DomainException ex);

}
