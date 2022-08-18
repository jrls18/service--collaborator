package br.com.developcorporation.collaborator.services.internal.mapper;

import br.com.developcorporation.collaborator.domain.model.Company;
import br.com.developcorporation.collaborator.services.internal.model.CompanyModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    Company toDomain(final CompanyModel companyModel);
}
