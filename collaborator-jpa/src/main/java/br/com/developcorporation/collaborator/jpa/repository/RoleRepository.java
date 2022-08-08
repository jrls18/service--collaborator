package br.com.developcorporation.collaborator.jpa.repository;

import br.com.developcorporation.collaborator.domain.enums.RoleName;
import br.com.developcorporation.collaborator.jpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
