package br.com.group.developer.corporation.service.collaborator.jpa.repository;

import br.com.group.developer.corporation.service.collaborator.jpa.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizationRepository  extends JpaRepository<Authorization, Long> {

    Optional<Authorization> findByClientIdAndClientSecret(final String clientId, final String clientSecret);

    Optional<Authorization> findByApplicationName(final String applicationName);

    Optional<Authorization> findBySiglaApp(final String siglaApp);
}
