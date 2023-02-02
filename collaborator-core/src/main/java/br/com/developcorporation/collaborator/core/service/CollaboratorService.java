package br.com.developcorporation.collaborator.core.service;

import br.com.developcorporation.collaborator.domain.message.CollaboratorMessage;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.domain.model.Pagination;

import java.util.Optional;

public interface CollaboratorService {

    Message add(final Collaborator dto);

    void addAsync(final Collaborator dto);

    void unlockCollaboratorAsync(final Collaborator collaborator);

    Message update(final Collaborator dto);

    Collaborator getById(Long id);

    Collaborator getByIdNotImage(Long id);

    void sendMessage(final CollaboratorMessage collaboratorMessage);

    Optional<Collaborator> findByUsername(final String username);

    Pagination<Collaborator> search(final String searchTerm, final int page, final int size);

}
