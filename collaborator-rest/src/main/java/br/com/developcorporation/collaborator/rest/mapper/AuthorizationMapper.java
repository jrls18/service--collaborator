package br.com.developcorporation.collaborator.rest.mapper;

import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.domain.model.Authorization;
import br.com.developcorporation.collaborator.rest.message.request.AuthorizationRequest;
import br.com.developcorporation.collaborator.rest.message.response.AuthorizationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = BaseMapper.class)
public interface AuthorizationMapper {
    AuthorizationMapper INSTANCE = Mappers.getMapper(AuthorizationMapper.class);

    Authorization toDto(final AuthorizationRequest request);

    @Mapping(source = "dateRegister", target = "dateRegister", dateFormat = MessageConstant.DATA_FORMAT)
    AuthorizationResponse toResponse(final Authorization dto);

    List<AuthorizationResponse> toResponseList(final List<Authorization> dtoList);
}
