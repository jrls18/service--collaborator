package br.com.developcorporation.collaborator.jpa.service;

import br.com.developcorporation.collaborator.jpa.entity.Collaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public interface CollaboratorRepositoryService {
    Optional<Collaborator> findByUserName(final String username);

    Optional<Collaborator> findByEmail(final String email);

    Long save(final Collaborator collaborator);

    Boolean existeEmpresa(final Long id);

    Optional<Collaborator> consultaPorCodigo(final Long id);

     Page<Collaborator> search(final String searchTerm,
            final int page,
            final int size);


    Page<Collaborator> findAll();


    void updateStatus(final Long idCollaborator, final Long idStatus);

}
