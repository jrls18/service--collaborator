package br.com.group.developer.corporation.service.collaborator.jpa.mapper;

import br.com.group.developer.corporation.service.collaborator.domain.model.Authorization;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthorizationMapper {
    AuthorizationMapper INSTANCE = Mappers.getMapper(AuthorizationMapper.class);

    Authorization toDto(final br.com.group.developer.corporation.service.collaborator.jpa.entity.Authorization entity);

    br.com.group.developer.corporation.service.collaborator.jpa.entity.Authorization toEntity(final Authorization dto);

    List<Authorization> toEntityList(final List<br.com.group.developer.corporation.service.collaborator.jpa.entity.Authorization> entityList);
}
