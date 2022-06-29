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
        name = "empresa",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"razao_social"})
                ,@UniqueConstraint(columnNames = {"cnpj"})
        })
public class Company implements Serializable {

    private static final long serialVersionUID = 9165838500012386509L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_empresa")
    private Long id;

    @Column(name = "razao_social", length = 150, nullable = false, unique = true)
    private String corporateName;

    @Column(name = "nome_fantasia", length = 150, nullable = false)
    private String fantasyName;

    @Column(name = "cnpj", length = 14, nullable = false, unique = true)
    private String cnpj;

    @Column(name = "inscricao_estadual", length = 30)
    private String stateRegistration;

    @Column(name = "data_fundacao", nullable = false)
    private LocalDate foundationDate;

    @Column(name = "telefone_principal", length = 11, nullable = false)
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

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dateRegister;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_situacao", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_seguimento", nullable = false)
    private FollowUp followUp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_grupo")
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_tiposistema", nullable = false)
    private SystemType systemType;

    @Column(name = "aceita_os_termos_de_uso", nullable = false)
    private boolean acceptTerms;

    @Column(name = "nome_logotipo")
    private String nameImage;
}
