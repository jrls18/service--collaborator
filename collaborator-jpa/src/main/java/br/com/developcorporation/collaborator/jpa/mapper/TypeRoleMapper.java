package br.com.developcorporation.collaborator.jpa.mapper;

import br.com.developcorporation.collaborator.domain.model.TypeCollaborator;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TypeRoleMapper {

    TypeRoleMapper INSTANCE = Mappers.getMapper(TypeRoleMapper.class);

    TypeCollaborator toDto(final br.com.developcorporation.collaborator.jpa.entity.Role entity);

    List<TypeCollaborator> toEntityList(final List<br.com.developcorporation.collaborator.jpa.entity.Role> entityList);
}
