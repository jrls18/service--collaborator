package br.com.developcorporation.collaborator.jpa.mapper;

import br.com.developcorporation.collaborator.jpa.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

   // Company toDto(final br.com.developcorporation.collaborator.jpa.entity.Company entity);

    //br.com.developcorporation.collaborator.jpa.entity.Company toEntity(final Company dto);

    //List<Company> toEntityList(final List<br.com.developcorporation.collaborator.jpa.entity.Company> entityList);
}
