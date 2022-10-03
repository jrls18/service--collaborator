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

    @Mapping(source = "birthDate", target = "birthDate", dateFormat = DataConstant.DATA_FORMAT)
    @Mapping(source = "mainPhone", target = "contact.mainPhone")
    @Mapping(source = "telephone", target = "contact.telephone")
    @Mapping(source = "email", target = "contact.email")

    @Mapping(source = "id", target = "id")

    @Mapping(source = "typeUser", target = "typeCollaborator.id")
    @Mapping(source = "zipCode", target = "address.zipCode")
    @Mapping(source = "publicPlace", target = "address.address")
    @Mapping(source = "neighborhood", target = "address.neighborhood")
    @Mapping(source = "location", target = "address.location")
    @Mapping(source = "state", target = "address.state")
    @Mapping(source = "number", target = "address.number")
    @Mapping(source = "complement", target = "address.complement")
    Collaborator toDomain(final CollaboratorMessage.Collaborator request);


    CollaboratorMessage toDomainAvro(final Colaborador colaborador);

    @Mapping(source = "details", target = "detailsList")
    CollaboratorMessage.Message toMessage(final DomainException ex);

}
