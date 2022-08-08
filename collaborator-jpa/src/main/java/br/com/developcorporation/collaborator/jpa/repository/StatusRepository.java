package br.com.developcorporation.collaborator.jpa.repository;

import br.com.developcorporation.collaborator.jpa.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    Optional<Status> findByDescription(final String description);

}
