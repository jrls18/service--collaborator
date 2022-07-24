package br.com.developcorporation.collaborator.jpa.mapper;

import br.com.developcorporation.collaborator.domain.model.Collaborator;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CollaboratorMapper {
    CollaboratorMapper INSTANCE = Mappers.getMapper(CollaboratorMapper.class);

    Collaborator toDomain(final br.com.developcorporation.collaborator.jpa.entity.Collaborator entity);

     br.com.developcorporation.collaborator.jpa.entity.Collaborator toEntity(final Collaborator domain);

    List<Collaborator> toDomainList(final List<br.com.developcorporation.collaborator.jpa.entity.Collaborator> entityList);
}
