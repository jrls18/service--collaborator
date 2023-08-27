package br.com.group.developer.corporation.service.collaborator.api.rest.message.request;

import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(FieldDomainConstants.SITUACAO)
public class StatusRequest implements Serializable {

    private static final long serialVersionUID = -3344150711150585440L;

    @JsonProperty(value = FieldDomainConstants.CODIGO, index = 0)
    private String id;

    @JsonProperty(value = FieldDomainConstants.DESCRICAO, index = 1)
    private String description;

}
