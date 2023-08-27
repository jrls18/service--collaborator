package br.com.group.developer.corporation.service.collaborator.jpa.repository;

import br.com.group.developer.corporation.service.collaborator.jpa.entity.CollaboratorRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorRoleRepository extends JpaRepository<CollaboratorRole, CollaboratorRole.CollaboratorRoleId> {
}
