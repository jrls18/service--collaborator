package br.com.group.developer.corporation.service.collaborator.domain.port;


import br.com.group.developer.corporation.service.collaborator.domain.model.Status;

import java.util.List;

public interface StatusPort {
    Status getById(final Long id);

    List<Status> getByAll();
}
