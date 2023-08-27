package br.com.group.developer.corporation.service.collaborator.domain.port;


import br.com.group.developer.corporation.service.collaborator.domain.model.TypeCollaborator;

import java.util.List;

public interface TypeCollaboratorPort {

    TypeCollaborator getById(final Long id);

    List<TypeCollaborator> getByAll();

    TypeCollaborator findByIdCollaboratorTypeAccess(final Long id);
}
