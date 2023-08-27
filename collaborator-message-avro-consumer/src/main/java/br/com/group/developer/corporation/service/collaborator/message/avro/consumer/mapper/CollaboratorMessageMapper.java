package br.com.group.developer.corporation.service.collaborator.message.avro.consumer.mapper;

import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import br.com.grupo.developer.corporation.libcommons.constants.OtherConstants;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;
import br.com.grupo.developer.corporation.msg.avro.collaborator.CollaboratorAsync;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CollaboratorMessageMapper {
    CollaboratorMessageMapper INSTANCE = Mappers.getMapper(CollaboratorMessageMapper.class);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dateTimeStartProcessing", ignore = true)
    @Mapping(target = "dateTimeEndProcessing", ignore = true)
    @Mapping(source = "collaborator.birthDate", target = "obj.birthDate", dateFormat = OtherConstants.YYYY_MM_DD)
    @Mapping(source = "collaborator.id", target = "obj.id")
    @Mapping(source = "collaborator.cpfCnpj", target = "obj.cpfCnpj")
    @Mapping(source = "collaborator.name", target = "obj.name")
    @Mapping(source = "collaborator.idCompany", target = "obj.idCompany")
    @Mapping(source = "collaborator.operationType", target = "obj.operationType")

    @Mapping(source = "collaborator.contact.mainPhone", target = "obj.contact.mainPhone")
    @Mapping(source = "collaborator.contact.telephone", target = "obj.contact.telephone")
    @Mapping(source = "collaborator.contact.email", target = "obj.contact.email")

    @Mapping(source = "collaborator.typeCollaborator.id", target = "obj.typeCollaborator.id")
    @Mapping(source = "collaborator.address.zipCode", target = "obj.address.zipCode")
    @Mapping(source = "collaborator.address.publicPlace", target = "obj.address.publicPlace")
    @Mapping(source = "collaborator.address.neighborhood", target = "obj.address.neighborhood")
    @Mapping(source = "collaborator.address.location", target = "obj.address.location")
    @Mapping(source = "collaborator.address.state", target = "obj.address.state")
    @Mapping(source = "collaborator.address.number", target = "obj.address.number")
    @Mapping(source = "collaborator.address.complement", target = "obj.address.complement")
    MessageAsync<Collaborator> toDomain(final CollaboratorAsync avro);


}
