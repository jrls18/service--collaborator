package br.com.group.developer.corporation.service.collaborator.internal.call.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCallResponse implements Serializable {

    private static final long serialVersionUID = 7670074512019984207L;

    private String codigo;

    private String razaoSocial;

    private String nomeFantasia;

    private String cnpj;

    private String dataFundacao;
}
