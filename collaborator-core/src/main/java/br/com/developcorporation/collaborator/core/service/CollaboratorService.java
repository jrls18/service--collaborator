package br.com.developcorporation.collaborator.core.service;

import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Collaborator;

public interface CollaboratorService {

    Message add(final Collaborator dto);

    Message update(final Collaborator dto);

    Collaborator getById(Long id);

    Collaborator getByCnpj(final String cnpj);

}
