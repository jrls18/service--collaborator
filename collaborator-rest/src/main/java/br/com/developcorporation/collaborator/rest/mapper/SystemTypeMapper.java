package br.com.developcorporation.collaborator.rest.mapper;

import br.com.developcorporation.collaborator.domain.model.SystemType;
import br.com.developcorporation.collaborator.rest.message.response.SystemTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = BaseMapper.class)
public interface SystemTypeMapper {
    SystemTypeMapper INSTANCE = Mappers.getMapper(SystemTypeMapper.class);

    SystemTypeResponse toResponse(final SystemType dto);

    List<SystemTypeResponse> toResponseList(final List<SystemType> dtoList);
}
