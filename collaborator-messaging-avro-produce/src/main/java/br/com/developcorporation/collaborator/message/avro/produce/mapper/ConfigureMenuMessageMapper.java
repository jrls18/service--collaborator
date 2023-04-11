package br.com.developcorporation.collaborator.message.avro.produce.mapper;


import br.com.group.developer.corporation.configure.menu.ConfigureMenu;
import br.com.group.developer.corporation.configure.menu.User;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConfigureMenuMessageMapper {

    ConfigureMenuMessageMapper INSTANCE = Mappers.getMapper(ConfigureMenuMessageMapper.class);

    @Mapping(source = "obj", target = "user")
    ConfigureMenu toAvro(MessageAsync<br.com.developcorporation.collaborator.domain.message.ConfigureMenu> domain);


    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.collaborator", target = "collaborator")
    User toAvro(br.com.developcorporation.collaborator.domain.message.ConfigureMenu domain);
}
