package br.com.developcorporation.collaborator.jpa.repository;

import br.com.developcorporation.collaborator.jpa.entity.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {

    @Query(value = "SELECT * FROM colaborador WHERE (email = :username OR telefonePrincipal = :username)", nativeQuery = true)
    Optional<Collaborator> findByUserName(@Param("username") final String username);

    @Query(value = "SELECT * FROM colaborador WHERE email = :email ", nativeQuery = true)
    Optional<Collaborator> findByEmail(@Param("email") final String email);
}
