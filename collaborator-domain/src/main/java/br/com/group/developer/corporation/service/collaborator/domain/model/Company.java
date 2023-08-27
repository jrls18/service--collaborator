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
public class Company implements Serializable {

    private static final long serialVersionUID = -1718255039511769027L;

    private String codigo;

    private String razaoSocial;

    private String nomeFantasia;

    private String cnpj;

    private String dataFundacao;
}
