package br.com.developcorporation.collaborator.rest.mapper;

import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.message.request.CollaboratorRequest;
import br.com.developcorporation.collaborator.rest.message.response.CollaboratorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = BaseMapper.class)
public interface CollaboratorMapper {

    CollaboratorMapper INSTANCE = Mappers.getMapper(CollaboratorMapper.class);

    @Mapping(source = "birthDate", target = "birthDate", dateFormat = MessageConstant.DATA_FORMAT)
    Collaborator toDomain(final CollaboratorRequest request);

    @Mapping(source = "birthDate", target = "birthDate", dateFormat = MessageConstant.DATA_FORMAT)
    @Mapping(source = "dataRegister", target = "dataRegister", dateFormat = MessageConstant.DATA_HORA_FORMAT)
    CollaboratorResponse toResponse(final Collaborator domain);
}
