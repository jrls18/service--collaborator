package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.domain.model.Pagination;

import java.util.Optional;

public interface CollaboratorPort {
    Long add(Collaborator dto);

    void update(Collaborator dto);

    Boolean existeEmpresa(final String id);

    Collaborator getById(Long id);

    Collaborator getEmail(String email);

    Optional<Collaborator> findByUserName(final String username);

    Pagination<Collaborator> search(final String searchTerm, final int page, final int size);
}
