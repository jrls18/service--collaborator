package br.com.group.developer.corporation.service.collaborator.api.rest.message.response;

import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(FieldDomainConstants.SITUACAO)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StatusResponse implements Serializable {

    private static final long serialVersionUID = 2230202088564724687L;

    @JsonProperty(value = FieldDomainConstants.CODIGO, index = 0)
    private Long id;

    @JsonProperty(value = FieldDomainConstants.DESCRICAO, index = 1)
    private String description;

    @JsonProperty(value = FieldDomainConstants.DATA_HORA_CADASTRO, index = 2)
    private String dateRegister;
}
