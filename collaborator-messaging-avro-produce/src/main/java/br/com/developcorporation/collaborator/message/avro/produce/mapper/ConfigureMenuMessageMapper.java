package br.com.developcorporation.collaborator.message.avro.produce.mapper;


import br.com.group.developer.corporation.configure.menu.ConfigureMenu;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConfigureMenuMessageMapper {

    ConfigureMenuMessageMapper INSTANCE = Mappers.getMapper(ConfigureMenuMessageMapper.class);

    ConfigureMenu toAvro(br.com.developcorporation.collaborator.domain.message.ConfigureMenu domain);
}
