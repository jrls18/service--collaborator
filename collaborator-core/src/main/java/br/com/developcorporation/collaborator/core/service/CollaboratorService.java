package br.com.developcorporation.collaborator.core.service;

import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.domain.model.Pagination;

import java.util.Optional;

public interface CollaboratorService {

    Message add(final Collaborator dto);

    void addAsync(final Collaborator dto);

    Message update(final Collaborator dto);

    Collaborator getById(Long id);

    void sendMessageError(final DomainException ex);

    Optional<Collaborator> findByUsername(final String username);

    Pagination<Collaborator> search(final String searchTerm, final int page, final int size);

}
