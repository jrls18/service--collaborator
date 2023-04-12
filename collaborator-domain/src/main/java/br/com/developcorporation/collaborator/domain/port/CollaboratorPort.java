package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.grupo.developer.corporation.libcommons.message.Pagination;

import java.util.Optional;

public interface CollaboratorPort {
    Long add(Collaborator dto);

    void update(Collaborator dto);

    void updateStatus(final Long idCollaborator, final Long idStatus);

    Boolean existeEmpresa(final String id);

    Collaborator getById(Long id);

    Collaborator getEmail(String email);

    Optional<Collaborator> findByUserName(final String username);

    Pagination<Collaborator> search(final String searchTerm, final String codEmpresa, final int page, final int size);
}
