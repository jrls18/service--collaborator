package br.com.developcorporation.collaborator.jpa.mapper;

import br.com.developcorporation.collaborator.domain.model.Collaborator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CollaboratorMapper {
    CollaboratorMapper INSTANCE = Mappers.getMapper(CollaboratorMapper.class);

    @Mapping(target = "contact.telephone", source = "telephone")
    @Mapping(target = "contact.mainPhone", source = "mainPhone")
    @Mapping(target = "contact.email", source = "email")

    @Mapping(target = "address.zipCode", source = "zipCode")
    @Mapping(target = "address.address", source = "publicPlace")
    @Mapping(target = "address.complement", source = "complement")
    @Mapping(target = "address.neighborhood", source = "neighborhood")
    @Mapping(target = "address.location", source = "location")
    @Mapping(target = "address.state", source = "state")
    @Mapping(target = "address.number", source = "number")
    Collaborator toDomain(final br.com.developcorporation.collaborator.jpa.entity.Collaborator entity);


    @Mapping(source = "contact.telephone", target = "telephone")
    @Mapping(source = "contact.mainPhone", target = "mainPhone")
    @Mapping(source = "contact.email", target = "email")

    @Mapping(source = "address.zipCode", target = "zipCode")
    @Mapping(source = "address.address", target = "publicPlace")
    @Mapping(source = "address.complement", target = "complement")
    @Mapping(source = "address.neighborhood", target = "neighborhood")
    @Mapping(source = "address.location", target = "location")
    @Mapping(source = "address.state", target = "state")
    @Mapping(source = "address.number", target = "number")
    br.com.developcorporation.collaborator.jpa.entity.Collaborator toEntity(final Collaborator domain);

    List<Collaborator> toDomainList(final List<br.com.developcorporation.collaborator.jpa.entity.Collaborator> entityList);
}
