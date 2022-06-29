package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.model.Company;

public interface CompanyPort {
    Long add(Company dto);

    void update(Company dto);

    Company getById(Long id);

    Company getByCnpj(String cnpj);

    Company getByCorporateName(String corporateName);
}
