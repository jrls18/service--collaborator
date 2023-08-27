package br.com.group.developer.corporation.service.collaborator.api.rest.mapper;


import br.com.group.developer.corporation.service.collaborator.api.rest.message.response.StatusResponse;
import br.com.group.developer.corporation.service.collaborator.domain.model.Status;
import br.com.grupo.developer.corporation.libcommons.constants.OtherConstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = BaseMapper.class)
public interface StatusMapper {

    StatusMapper INSTANCE = Mappers.getMapper(StatusMapper.class);

    @Mapping(source = "dateRegister", target = "dateRegister", dateFormat = OtherConstants.DATA_FORMAT)
    StatusResponse toResponse(final Status dto);

    List<StatusResponse> toResponseList(final List<Status> dtoList);
}
