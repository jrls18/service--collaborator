package br.com.group.developer.corporation.service.collaborator.internal.call.mapper;

import br.com.group.developer.corporation.service.collaborator.domain.model.Company;
import br.com.group.developer.corporation.service.collaborator.internal.call.model.CompanyCallResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    Company toDomain(final CompanyCallResponse response);
}
