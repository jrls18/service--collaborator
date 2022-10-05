package br.com.developcorporation.collaborator.core.service;

import br.com.developcorporation.collaborator.domain.model.TypeCollaborator;

import java.util.List;

public interface TypeCollaboratorService {
    TypeCollaborator getById(final Long id);

    List<TypeCollaborator> getByAll();
}
