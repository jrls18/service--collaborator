package br.com.developcorporation.collaborator.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "authorization", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"client_id"})
        , @UniqueConstraint(columnNames = {"client_secret"})
        , @UniqueConstraint(columnNames = {"application_name"})
        , @UniqueConstraint(columnNames = {"sigla_app"})
})
public class Authorization implements Serializable {

    private static final long serialVersionUID = 3324817770903542353L;

    @Id
    @Column(name = "cod_authorization")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false, length = 40)
    private String clientId;

    @Column(name = "client_secret", nullable = false, length = 40)
    private String clientSecret;

    @Column(name = "application_name", nullable = false, length = 150)
    private String applicationName;

    @Column(name = "sigla_app", nullable = false, length = 20)
    private String siglaApp;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dateRegister;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_situacao", nullable = false)
    private Status status;
}
