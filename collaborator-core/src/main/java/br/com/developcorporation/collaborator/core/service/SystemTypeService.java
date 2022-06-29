package br.com.developcorporation.collaborator.core.service;

import br.com.developcorporation.collaborator.domain.model.SystemType;

import java.util.List;

public interface SystemTypeService {
    SystemType getById(final Long id);

    List<SystemType> getByAll();
}
