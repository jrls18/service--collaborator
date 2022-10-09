package br.com.developcorporation.collaborator.jpa.mapper;

import br.com.developcorporation.collaborator.jpa.entity.CollaboratorRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CollaboratorRoleMapper {
    CollaboratorRoleMapper INSTANCE = Mappers.getMapper(CollaboratorRoleMapper.class);

    @Mapping(source = "idCollaborator", target = "collaboratorRoleId.idCollaborator")
    @Mapping(source = "idRole", target = "collaboratorRoleId.idRole")
    CollaboratorRole toEntity(final Long idCollaborator, final Long idRole);
}
