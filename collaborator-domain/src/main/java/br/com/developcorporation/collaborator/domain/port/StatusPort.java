package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.model.Status;

import java.util.List;

public interface StatusPort {
    Status getById(final Long id);

    List<Status> getByAll();
}
