package br.com.group.developer.corporation.service.collaborator.api.rest.mapper;

import br.com.group.developer.corporation.service.collaborator.api.rest.security.model.Jwt;
import br.com.grupo.developer.corporation.libcommons.message.response.JwtResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = BaseMapper.class)
public interface JwtMapper {

    JwtMapper INSTANCE = Mappers.getMapper(JwtMapper.class);

    JwtResponse toResponse(Jwt jwt);
}
