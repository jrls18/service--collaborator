package br.com.group.developer.corporation.service.collaborator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TypeCollaborator implements Serializable {
    private static final long serialVersionUID = -417998709819694255L;

    private Long id;

    private String name;
}
