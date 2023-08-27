package br.com.group.developer.corporation.service.collaborator.jpa.repository;

import br.com.group.developer.corporation.service.collaborator.jpa.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
