package br.com.developcorporation.collaborator.rest.mapper;

import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import br.com.developcorporation.lib.commons.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = BaseMapper.class)
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageResponse toResponse(final Message message);
    MessageResponse dtoToResponse(final br.com.developcorporation.collaborator.domain.message.Message message);

    MessageResponse.DetailsResponse toDetailsResponse(final br.com.developcorporation.lib.commons.message.Message.Details details);

    List<MessageResponse.DetailsResponse> toDetailsResponseList(final  List<br.com.developcorporation.lib.commons.message.Message.Details> detailsList);

    MessageResponse.DetailsResponse toDetailsResponse(final br.com.developcorporation.collaborator.domain.message.Message.Details details);

    List<MessageResponse.DetailsResponse> domainToDetailsResponseList(final  List<br.com.developcorporation.collaborator.domain.message.Message.Details> domainDetailsList);


}
