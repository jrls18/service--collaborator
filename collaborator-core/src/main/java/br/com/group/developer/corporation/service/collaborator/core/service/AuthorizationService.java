package br.com.group.developer.corporation.service.collaborator.core.service;

import br.com.group.developer.corporation.service.collaborator.domain.model.Authorization;
import br.com.grupo.developer.corporation.libcommons.message.Message;

import java.util.List;

public interface AuthorizationService {

    Message add(final Authorization dto);

    Message update(final Authorization dto);

    List<Authorization> getAll();

    void isAuthentication();
}
