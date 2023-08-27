package br.com.group.developer.corporation.service.collaborator.api.rest.mapper;

import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = BaseMapper.class)
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageResponse toResponse(final Message message);
    MessageResponse dtoToResponse(final Message message);

    MessageResponse.DetailsResponse toDetailsResponse(final Message.Details details);

    List<MessageResponse.DetailsResponse> domainToDetailsResponseList(final  List<Message.Details> domainDetailsList);

}
