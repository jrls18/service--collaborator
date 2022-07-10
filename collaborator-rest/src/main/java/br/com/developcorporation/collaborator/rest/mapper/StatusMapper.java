package br.com.developcorporation.collaborator.rest.mapper;

import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.domain.model.Status;
import br.com.developcorporation.collaborator.rest.message.response.StatusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = BaseMapper.class)
public interface StatusMapper {

    StatusMapper INSTANCE = Mappers.getMapper(StatusMapper.class);

    @Mapping(source = "dateRegister", target = "dateRegister", dateFormat = MessageConstant.DATA_HORA_FORMAT)
    StatusResponse toResponse(final Status dto);

    List<StatusResponse> toResponseList(final List<Status> dtoList);
}
