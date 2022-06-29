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
@Table(
        name = "seguimento",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"descricao"})
        })
public class FollowUp implements Serializable {

    private static final long serialVersionUID = 1968416516467013753L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_seguimento")
    private Long id;

    @Column(name = "descricao", length = 50, nullable = false)
    private String description;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dateRegister;
}
