package br.com.group.developer.corporation.service.collaborator.domain.port;


import br.com.group.developer.corporation.service.collaborator.domain.model.Company;

public interface CompanyPort {
    Company findByCompanyId(final Long id);
}
