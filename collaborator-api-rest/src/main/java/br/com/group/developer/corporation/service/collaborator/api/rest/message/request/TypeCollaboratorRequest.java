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
@JsonRootName(FieldDomainConstants.TIPO_USUARIO)
public class TypeCollaboratorRequest implements Serializable {

    private static final long serialVersionUID = 1318860786421795635L;

    @JsonProperty(value = FieldDomainConstants.CODIGO, index = 0)
    private String id;
}
