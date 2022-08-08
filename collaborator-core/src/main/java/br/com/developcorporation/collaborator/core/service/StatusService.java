package br.com.developcorporation.collaborator.core.service;

import br.com.developcorporation.collaborator.domain.model.Status;

import java.util.List;

public interface StatusService {

    Status getById(final Long id);

    List<Status> getByAll();
}
