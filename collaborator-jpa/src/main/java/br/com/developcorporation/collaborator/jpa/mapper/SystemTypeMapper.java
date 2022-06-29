package br.com.developcorporation.collaborator.jpa.mapper;

import br.com.developcorporation.collaborator.domain.model.SystemType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SystemTypeMapper {
    SystemTypeMapper INSTANCE = Mappers.getMapper(SystemTypeMapper.class);

    SystemType toDto(final br.com.developcorporation.collaborator.jpa.entity.SystemType entity);

    List<SystemType> toEntityList(final List<br.com.developcorporation.collaborator.jpa.entity.SystemType> entityList);
}
