package br.com.group.developer.corporation.service.collaborator.api.rest.message.response;

import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(FieldDomainConstants.TIPO_USUARIO)
public class TypeCollaboratorResponse implements Serializable {

    private static final long serialVersionUID = 1318860786421795635L;

    @JsonProperty(value = FieldDomainConstants.CODIGO, index = 0)
    private String id;

    @JsonProperty(value = FieldDomainConstants.DESCRICAO, index = 1)
    private String name;
}
