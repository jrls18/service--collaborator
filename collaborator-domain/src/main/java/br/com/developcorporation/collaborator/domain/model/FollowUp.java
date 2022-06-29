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
public class FollowUp implements Serializable {

    private static final long serialVersionUID = -7523014467225741889L;

    private Long id;

    private String description;

    private LocalDateTime dateRegister;
}
