package br.com.developcorporation.collaborator.rest.mapper;

import br.com.developcorporation.collaborator.rest.security.model.Jwt;

import br.com.grupo.developer.corporation.libcommons.message.response.JwtResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = BaseMapper.class)
public interface JwtMapper {

    JwtMapper INSTANCE = Mappers.getMapper(JwtMapper.class);

    JwtResponse toResponse(Jwt jwt);
}
