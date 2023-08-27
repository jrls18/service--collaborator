package br.com.group.developer.corporation.service.collaborator.api.rest.mapper;


import br.com.group.developer.corporation.service.collaborator.api.rest.message.request.TypeCollaboratorRequest;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.response.TypeCollaboratorResponse;
import br.com.group.developer.corporation.service.collaborator.domain.model.TypeCollaborator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = BaseMapper.class)
public interface TypeCollaboratorMapper {

    TypeCollaboratorMapper INSTANCE = Mappers.getMapper(TypeCollaboratorMapper.class);

    @Mapping(target = "name", ignore = true)
    TypeCollaborator toDomain(final TypeCollaboratorRequest request);

    TypeCollaboratorResponse toResponse(final TypeCollaborator dto);

    List<TypeCollaboratorResponse> toResponseList(final List<TypeCollaborator> dtoList);
}
