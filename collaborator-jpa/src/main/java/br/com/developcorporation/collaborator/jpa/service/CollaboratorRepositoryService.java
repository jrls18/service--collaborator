package br.com.developcorporation.collaborator.jpa.service;

import br.com.developcorporation.collaborator.jpa.entity.Collaborator;

import java.util.Optional;

public interface CollaboratorRepositoryService {
    Optional<Collaborator> findByUserName(final String username);

    Optional<Collaborator> findByEmail(final String email);

    Long save(final Collaborator collaborator);

    Optional<Collaborator> consultaPorCodigo(final Long id);
}
