package br.com.group.developer.corporation.service.collaborator.jpa.mapper;

import br.com.group.developer.corporation.service.collaborator.domain.model.Status;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StatusMapper {
    StatusMapper INSTANCE = Mappers.getMapper(StatusMapper.class);

    Status toDto(final br.com.group.developer.corporation.service.collaborator.jpa.entity.Status entity);

    List<Status> toEntityList(final List<br.com.group.developer.corporation.service.collaborator.jpa.entity.Status> entityList);
}
