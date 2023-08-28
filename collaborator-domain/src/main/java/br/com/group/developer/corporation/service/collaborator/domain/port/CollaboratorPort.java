package br.com.group.developer.corporation.service.collaborator.domain.port;

import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import br.com.grupo.developer.corporation.libcommons.message.Pagination;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CollaboratorPort {
    Long save(Collaborator dto);

    void updateStatus(final Long idCollaborator, final Long idStatus);

    void updateDateTimeLastAccess(final Long idCollaborator, final LocalDateTime dateTimeLastAccess);

    Boolean existsCompany(final String id);

    Collaborator getById(Long id);

    Collaborator getByIdActive(final String uuid);

    Collaborator getEmail(String email);

    Optional<Collaborator> findByUserName(final String username);

    Pagination<Collaborator> search(final String searchTerm, final String codEmpresa, final int page, final int size);
}
