package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.model.Authorization;

import java.util.List;
import java.util.Optional;

public interface AuthorizationPort {

    void add(Authorization dto);

    void update(Authorization dto);

    List<Authorization> getAll();

    Authorization getById(Long id);

    Authorization getByApplicationName(String name);

    Authorization getBySiglaApp(String sigla);

    boolean existsByName (final String name);

    boolean existsBySiglaApp(final String app);

    Optional<Authorization> getByClientIdAndClientSecret(String clientId, String clientSecret);
}
