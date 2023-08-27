package br.com.group.developer.corporation.service.collaborator.core.service;


import br.com.group.developer.corporation.service.collaborator.domain.model.Status;

import java.util.List;

public interface StatusService {

    Status getById(final Long id);

    List<Status> getByAll();
}
