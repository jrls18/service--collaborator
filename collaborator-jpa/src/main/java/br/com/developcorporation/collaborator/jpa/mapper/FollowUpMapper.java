package br.com.developcorporation.collaborator.jpa.mapper;

import br.com.developcorporation.collaborator.domain.model.FollowUp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FollowUpMapper {
    FollowUpMapper INSTANCE = Mappers.getMapper(FollowUpMapper.class);

    FollowUp toDto(final br.com.developcorporation.collaborator.jpa.entity.FollowUp entity);

    List<FollowUp> toEntityList(final List<br.com.developcorporation.collaborator.jpa.entity.FollowUp> entityList);
}
