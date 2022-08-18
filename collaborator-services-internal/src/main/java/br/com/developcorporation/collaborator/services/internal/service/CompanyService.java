package br.com.developcorporation.collaborator.services.internal.service;

import br.com.developcorporation.collaborator.services.internal.model.CompanyModel;

public interface CompanyService {
    CompanyModel consultaPorCodigoEmpresa(final Long id);
}
