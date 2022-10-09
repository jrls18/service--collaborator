package br.com.developcorporation.collaborator.jpa.repository;

import br.com.developcorporation.collaborator.jpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    @Query(value = "SELECT A.cod_tipoacesso, A.descricao FROM tipoacesso AS A " +
            "INNER JOIN colaboradoracesso AS B ON A.cod_tipoacesso = B.cod_tipoacesso " +
            "WHERE B.cod_colaborador = :id  " +
            "limit 1", nativeQuery = true)
    Role findByIdCollaboratorTypeAccess(@Param("id") final Long id);
}
