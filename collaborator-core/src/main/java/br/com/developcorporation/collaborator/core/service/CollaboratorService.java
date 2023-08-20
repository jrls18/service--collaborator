package br.com.developcorporation.collaborator.core.service;

import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.message.Pagination;

import java.util.Optional;

public interface CollaboratorService {

    Message add(final Collaborator dto);

    void addAsync(final Collaborator dto);

    void updateStatusCollaboratorAsync(final Collaborator collaborator, final boolean isError,
                                      final boolean isMenu);

    Message update(final Collaborator dto);

    Collaborator getById(Long id);

    Collaborator getByIdNotImage(Long id);

    Optional<Collaborator> findByUsername(final String username);

    Pagination<Collaborator> search(final String searchTerm, final String codEmpresa, final int page, final int size);

}
