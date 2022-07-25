package br.com.developcorporation.collaborator.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(
        name = "colaborador",
        uniqueConstraints = {
                 @UniqueConstraint(columnNames = {"email"})
                ,@UniqueConstraint(columnNames = {"telefonePrincipal"})
        })
public class Collaborator implements Serializable {

    private static final long serialVersionUID = 7210073589481938603L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_colaborador")
    private Long id;

    @Column(name = "nome", length = 150, nullable = false, unique = true)
    private String name;

    @Column(name = "data_fundacao", nullable = false)
    private LocalDate birthDate;

    @Column(name = "dataCadastro", nullable = false)
    private LocalDateTime dataRegister;

    @Column(name = "senha", nullable = false)
    private String password;

    @Column(name = "tipoUsuario", nullable = false)
    private String typeUser;

    @Column(name = "codEmpresa", nullable = false)
    private int idCompany;

    @Column(name = "cpfCnpj", length = 14, nullable = false, unique = true)
    private String cpfCnpj;


    @Column(name = "email", length = 120, nullable = false)
    private String email;

    @Column(name = "telefonePrincipal", length = 11, nullable = false)
    private String  mainPhone;

    @Column(name = "telefone", length = 11)
    private String telephone;

    @Column(name = "cep", length = 8, nullable = false)
    private String zipCode;

    @Column(name = "logradouro", length = 255, nullable = false)
    private String publicPlace;

    @Column(name = "complemento", length = 150)
    private String complement;

    @Column(name = "bairro", length = 150, nullable = false)
    private String neighborhood;

    @Column(name = "localidade", length = 150, nullable = false)
    private String location;

    @Column(name = "uf", length = 2, nullable = false)
    private String state;

    @Column(name = "numero", nullable = false)
    private int number;

    @Column(name = "dataCadastro", nullable = false)
    private LocalDateTime dateRegister;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codSituacao", nullable = false)
    private Status status;

    @Column(name = "nomeImage")
    private String nameImage;
}
