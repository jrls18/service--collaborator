package br.com.group.developer.corporation.service.collaborator.internal.call.service;


import br.com.group.developer.corporation.service.collaborator.internal.call.model.CompanyCallResponse;

public interface CompanyService {
    CompanyCallResponse getByCompanyId(final Long id);
}
