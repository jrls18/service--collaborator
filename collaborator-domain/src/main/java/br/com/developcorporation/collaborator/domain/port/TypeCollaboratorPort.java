package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.model.TypeCollaborator;

import java.util.List;

public interface TypeCollaboratorPort {

    TypeCollaborator getById(final Long id);

    List<TypeCollaborator> getByAll();

    TypeCollaborator findByIdCollaboratorTypeAccess(final Long id);
}
