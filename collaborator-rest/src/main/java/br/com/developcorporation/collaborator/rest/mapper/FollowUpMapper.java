package br.com.developcorporation.collaborator.rest.mapper;

import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.domain.model.FollowUp;
import br.com.developcorporation.collaborator.rest.message.response.FollowUpResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = BaseMapper.class)
public interface FollowUpMapper {
    FollowUpMapper INSTANCE = Mappers.getMapper(FollowUpMapper.class);

    @Mapping(source = "dateRegister", target = "dateRegister", dateFormat = MessageConstant.DATA_FORMAT)
    FollowUpResponse toResponse(final FollowUp dto);

    List<FollowUpResponse> toResponseList(final List<FollowUp> dtoList);
}
