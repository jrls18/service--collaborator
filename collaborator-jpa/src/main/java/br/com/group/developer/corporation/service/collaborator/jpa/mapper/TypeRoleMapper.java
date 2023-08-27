package br.com.group.developer.corporation.service.collaborator.jpa.mapper;

import br.com.group.developer.corporation.service.collaborator.domain.model.TypeCollaborator;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TypeRoleMapper {

    TypeRoleMapper INSTANCE = Mappers.getMapper(TypeRoleMapper.class);

    TypeCollaborator toDto(final br.com.group.developer.corporation.service.collaborator.jpa.entity.Role entity);

    List<TypeCollaborator> toEntityList(final List<br.com.group.developer.corporation.service.collaborator.jpa.entity.Role> entityList);
}
