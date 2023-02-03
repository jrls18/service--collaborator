package br.com.developcorporation.collaborator.core.service;

import br.com.developcorporation.collaborator.domain.model.Authorization;
import br.com.grupo.developer.corporation.libcommons.message.Message;

import java.util.List;

public interface AuthorizationService {

    Message add(final Authorization dto);

    Message update(final Authorization dto);

    List<Authorization> getAll();

    void isAuthentication();
}
