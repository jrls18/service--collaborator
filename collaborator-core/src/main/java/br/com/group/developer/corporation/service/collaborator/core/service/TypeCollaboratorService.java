package br.com.group.developer.corporation.service.collaborator.core.service;


import br.com.group.developer.corporation.service.collaborator.domain.model.TypeCollaborator;

import java.util.List;

public interface TypeCollaboratorService {
    TypeCollaborator getById(final Long id);

    List<TypeCollaborator> getByAll();
}
