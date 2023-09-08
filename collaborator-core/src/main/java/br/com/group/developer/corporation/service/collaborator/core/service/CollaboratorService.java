package br.com.group.developer.corporation.service.collaborator.core.service;

import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.message.Pagination;

import java.util.Optional;

public interface CollaboratorService {

    Message add(final Collaborator dto);

    void saveAsync(final Collaborator dto);

    Message update(final Collaborator dto);

    Collaborator getById(Long id);

    Collaborator getByIdNotImage(Long id);

    Optional<Collaborator> findByUsername(final String username);

    Message profileActivation(final String uuid);

    Message recoverPassword(final String username);

    Pagination<Collaborator> search(final String searchTerm, final String codEmpresa, final int page, final int size);

}
