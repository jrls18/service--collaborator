package br.com.developcorporation.collaborator.core.service;

import br.com.developcorporation.collaborator.domain.model.FollowUp;

import java.util.List;

public interface FollowUpService {
    FollowUp getById(final Long id);

    List<FollowUp> getByAll();
}
