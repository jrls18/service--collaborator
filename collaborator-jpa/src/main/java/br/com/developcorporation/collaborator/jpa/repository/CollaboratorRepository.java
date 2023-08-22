package br.com.developcorporation.collaborator.jpa.repository;

import br.com.developcorporation.collaborator.jpa.entity.Collaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {

    @Query(value = "SELECT * FROM colaborador WHERE (email = :username OR telefone_principal = :username)", nativeQuery = true)
    Optional<Collaborator> findByUserName(@Param("username") final String username);

    @Query(value = "SELECT * FROM colaborador WHERE email = :email ", nativeQuery = true)
    Optional<Collaborator> findByEmail(@Param("email") final String email);

    Optional<Collaborator> findByIdActive(final String uuid);

    @Query(value = "SELECT COUNT(*) FROM colaborador WHERE cod_empresa = :id", nativeQuery = true)
    Integer existsByIdCompany(Long id);


    @Modifying
    @Transactional
    @Query(value = "UPDATE colaborador SET cod_situacao = :idstatus WHERE cod_colaborador = :idcollaborator ", nativeQuery = true)
    void updateStatus(@Param("idcollaborator") final Long idCollaborator, @Param("idstatus") final Long idStatus );

    @Query(value = "SELECT * FROM colaborador WHERE COD_EMPRESA = :codEmpresa AND (cod_colaborador = :searchTerm OR nome LIKE %:searchTerm% OR :searchTerm IS NULL)",
            countQuery = "SELECT * FROM colaborador WHERE COD_EMPRESA = :codEmpresa AND (cod_colaborador = :searchTerm OR nome LIKE %:searchTerm% OR :searchTerm IS NULL)",
            nativeQuery = true)
    Page<Collaborator> search(@Param("searchTerm") String searchTerm,@Param("codEmpresa") String codEmpresa, Pageable pageable);
}
