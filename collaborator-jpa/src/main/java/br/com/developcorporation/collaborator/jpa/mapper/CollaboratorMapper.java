package br.com.developcorporation.collaborator.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CollaboratorMapper {
    CollaboratorMapper INSTANCE = Mappers.getMapper(CollaboratorMapper.class);

   // Company toDto(final br.com.developcorporation.collaborator.jpa.entity.Company entity);

    //br.com.developcorporation.collaborator.jpa.entity.Company toEntity(final Company dto);

    //List<Company> toEntityList(final List<br.com.developcorporation.collaborator.jpa.entity.Company> entityList);
}
