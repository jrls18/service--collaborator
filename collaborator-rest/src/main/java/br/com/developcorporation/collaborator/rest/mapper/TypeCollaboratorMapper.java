package br.com.developcorporation.collaborator.rest.mapper;

import br.com.developcorporation.collaborator.domain.model.TypeCollaborator;
import br.com.developcorporation.collaborator.rest.message.request.TypeCollaboratorRequest;
import br.com.developcorporation.collaborator.rest.message.response.TypeCollaboratorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = BaseMapper.class)
public interface TypeCollaboratorMapper {

    TypeCollaboratorMapper INSTANCE = Mappers.getMapper(TypeCollaboratorMapper.class);

    TypeCollaborator toDomain(final TypeCollaboratorRequest request);

    TypeCollaboratorResponse toResponse(final TypeCollaborator dto);

    List<TypeCollaboratorResponse> toResponseList(final List<TypeCollaborator> dtoList);
}
