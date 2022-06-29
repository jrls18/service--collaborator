package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.model.FollowUp;

import java.util.List;

public interface FollowUpPort {
    FollowUp getById(final Long id);

    List<FollowUp> getByAll();
}
