package br.com.developcorporation.collaborator.core.mapper;

import br.com.developcorporation.collaborator.domain.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    Message toDto(final br.com.grupo.developer.corporation.libcommons.message.Message message);

    Message.Details toDetailsDto(final br.com.grupo.developer.corporation.libcommons.message.Message.Details details);

    List<Message.Details> toDetailsDtoList(final  List<br.com.grupo.developer.corporation.libcommons.message.Message.Details> detailsList);
}
