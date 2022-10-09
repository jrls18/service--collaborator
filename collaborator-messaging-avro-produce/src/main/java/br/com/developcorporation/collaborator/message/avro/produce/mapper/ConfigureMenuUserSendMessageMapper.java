package br.com.developcorporation.collaborator.message.avro.produce.mapper;

import br.com.developcorporation.menu.configure.user.message.avro.ConfigureMenuUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConfigureMenuUserSendMessageMapper {

    ConfigureMenuUserSendMessageMapper INSTANCE = Mappers.getMapper(ConfigureMenuUserSendMessageMapper.class);

    ConfigureMenuUser toAvro(br.com.developcorporation.collaborator.domain.message.ConfigureMenuUser domain);
}
