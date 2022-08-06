package br.com.developcorporation.collaborator.jpa.repository;

import br.com.developcorporation.collaborator.jpa.entity.Collaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {

    @Query(value = "SELECT * FROM colaborador WHERE (email = :username OR telefone_principal = :username)", nativeQuery = true)
    Optional<Collaborator> findByUserName(@Param("username") final String username);

    @Query(value = "SELECT * FROM colaborador WHERE email = :email ", nativeQuery = true)
    Optional<Collaborator> findByEmail(@Param("email") final String email);


    @Query(value = "SELECT CASE WHEN count(*) > 0 THEN true ELSE false END FROM colaborador WHERE cod_empresa = :id", nativeQuery = true)
    Boolean existsByIdCompany(Long id);


    @Query(value = "SELECT * FROM colaborador WHERE (cod_colaborador = :searchTerm OR nome LIKE %:searchTerm% OR :searchTerm IS NULL)",
            countQuery = "SELECT * FROM colaborador WHERE (cod_colaborador = :searchTerm OR nome LIKE %:searchTerm% OR :searchTerm IS NULL)",
            nativeQuery = true)
    Page<Collaborator> search(@Param("searchTerm") String searchTerm, Pageable pageable);
}
