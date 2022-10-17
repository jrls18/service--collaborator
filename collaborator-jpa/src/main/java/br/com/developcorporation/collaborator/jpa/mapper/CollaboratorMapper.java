package br.com.developcorporation.collaborator.jpa.mapper;

import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.domain.model.Pagination;
import br.com.developcorporation.collaborator.jpa.entity.Role;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Mapping(target = "document.nameDocument", source = "nameImage")
    Collaborator toDomain(final br.com.developcorporation.collaborator.jpa.entity.Collaborator entity);

    Collaborator.TypeCollaborator toDomain(final Role role);


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
    @Mapping(target = "nameImage", source = "document.nameDocument")
    br.com.developcorporation.collaborator.jpa.entity.Collaborator toEntity(final Collaborator domain);

    List<Collaborator> toDomainList(final List<br.com.developcorporation.collaborator.jpa.entity.Collaborator> entityList);


    default Pagination<Collaborator> toDomain(final Page<br.com.developcorporation.collaborator.jpa.entity.Collaborator> collaboratorPage) {

        if ( collaboratorPage == null ) {
            return null;
        }

        Pagination<Collaborator> collaboratorPagination = new Pagination<>();

        collaboratorPagination.setCurrentPages(collaboratorPage.getNumber());
        collaboratorPagination.setTotalItems(collaboratorPage.getTotalElements());
        collaboratorPagination.setTotalPages(collaboratorPage.getTotalPages());

        collaboratorPagination.setItems(toDomainList(collaboratorPage.getContent()));

        return collaboratorPagination;
    }


}
