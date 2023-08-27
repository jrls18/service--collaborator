package br.com.group.developer.corporation.service.collaborator.api.rest.mapper;


import br.com.group.developer.corporation.service.collaborator.api.rest.message.request.AuthorizationRequest;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.response.AuthorizationResponse;
import br.com.group.developer.corporation.service.collaborator.domain.model.Authorization;
import br.com.grupo.developer.corporation.libcommons.constants.FieldAssistantConstants;
import br.com.grupo.developer.corporation.libcommons.constants.OtherConstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = BaseMapper.class)
public interface AuthorizationMapper {
    AuthorizationMapper INSTANCE = Mappers.getMapper(AuthorizationMapper.class);

    @Mapping(target = "dateRegister", ignore = true)
    @Mapping(target = "clientSecret", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    Authorization toDto(final AuthorizationRequest request);

    @Mapping(source = "dateRegister", target = "dateRegister", dateFormat = OtherConstants.DATA_FORMAT)
    AuthorizationResponse toResponse(final Authorization dto);

    List<AuthorizationResponse> toResponseList(final List<Authorization> dtoList);
}
