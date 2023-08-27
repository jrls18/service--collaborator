package br.com.group.developer.corporation.service.collaborator.domain.model;

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
public class Authorization implements Serializable {

    private static final long serialVersionUID = -7094667664206514744L;

    private Long id;

    private String clientId;

    private String clientSecret;

    private String applicationName;

    private String siglaApp;

    private LocalDateTime dateRegister;

    private Status status;
}
