package br.com.group.developer.corporation.service.collaborator.jpa.service;

import br.com.group.developer.corporation.service.collaborator.jpa.entity.Collaborator;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CollaboratorRepositoryService {
    Optional<Collaborator> findByUserName(final String username);

    Optional<Collaborator> findByEmail(final String email);

    Long save(final Collaborator collaborator);

    Boolean existsCompany(final Long id);

    Optional<Collaborator> findById(final Long id);

     Page<Collaborator> search(final String searchTerm,
            final String codEmpresa,
            final int page,
            final int size);


    Page<Collaborator> findAll();

    Collaborator findByIdActive(final String uuid);


    void updateStatus(final Long idCollaborator, final Long idStatus);

}
