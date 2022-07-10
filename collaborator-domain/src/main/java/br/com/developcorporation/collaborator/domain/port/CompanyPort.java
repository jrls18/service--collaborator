package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.model.Collaborator;

public interface CompanyPort {
    Long add(Collaborator dto);

    void update(Collaborator dto);

    Collaborator getById(Long id);

    Collaborator getByCnpj(String cnpj);

    Collaborator getByCorporateName(String corporateName);
}
