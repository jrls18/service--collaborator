package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.model.Collaborator;

import java.util.Optional;

public interface CollaboratorPort {
    Long add(Collaborator dto);

    void update(Collaborator dto);

    Collaborator getById(Long id);

    Collaborator getEmail(String email);

    Optional<Collaborator> findByUserName(final String username);

}
