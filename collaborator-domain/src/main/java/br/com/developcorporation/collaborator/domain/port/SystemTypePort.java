package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.model.SystemType;

import java.util.List;

public interface SystemTypePort {
    SystemType getById(final Long id);

    List<SystemType> getByAll();
}
