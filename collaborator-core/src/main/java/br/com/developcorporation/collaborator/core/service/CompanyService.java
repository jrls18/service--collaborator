package br.com.developcorporation.collaborator.core.service;

import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Company;

public interface CompanyService {

    Message add(final Company dto);

    Message update(final Company dto);

    Company getById(Long id);

    Company getByCnpj(final String cnpj);

}
