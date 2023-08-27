package br.com.group.developer.corporation.service.collaborator.core.mapper;

import br.com.grupo.developer.corporation.libcommons.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    Message toDto(final Message message);

    Message.Details toDetailsDto(final Message.Details details);

    List<Message.Details> toDetailsDtoList(final  List<Message.Details> detailsList);
}
