package br.com.developcorporation.collaborator.jpa.repository;

import br.com.developcorporation.collaborator.jpa.entity.CollaboratorRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorRoleRepository extends JpaRepository<CollaboratorRole, CollaboratorRole.CollaboratorRoleId> {
}
