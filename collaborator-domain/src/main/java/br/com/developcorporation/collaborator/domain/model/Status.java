package br.com.developcorporation.collaborator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status implements Serializable {

    private static final long serialVersionUID = -6552173904691766779L;

    private Long id;

    private String description;

    private LocalDateTime dateRegister;
}
