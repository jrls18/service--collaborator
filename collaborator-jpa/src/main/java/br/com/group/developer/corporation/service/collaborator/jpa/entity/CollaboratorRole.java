package br.com.group.developer.corporation.service.collaborator.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import jakarta.persistence.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "colaboradoracesso")
public class CollaboratorRole implements Serializable {

        private static final long serialVersionUID = 1555913223812597908L;

        @EmbeddedId
        private CollaboratorRoleId collaboratorRoleId;

        @Getter
        @Setter
        public static class CollaboratorRoleId implements Serializable {
                private static final long serialVersionUID = 6319864674120411976L;

                @Column(name = "cod_colaborador", nullable = false)
                private Long idCollaborator;

                @Column(name = "cod_tipoacesso", nullable = false)
                private Long idRole;
        }
}
