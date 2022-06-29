package br.com.developcorporation.collaborator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemType implements Serializable {

    private static final long serialVersionUID = -8163934442093004884L;

    private Long id;

    private String description;
}
