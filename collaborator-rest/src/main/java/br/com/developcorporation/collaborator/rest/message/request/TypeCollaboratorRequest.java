package br.com.developcorporation.collaborator.rest.message.request;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(FieldConstant.TIPO_USUARIO)
public class TypeCollaboratorRequest implements Serializable {

    private static final long serialVersionUID = 1318860786421795635L;

    @JsonProperty(value = FieldConstant.CODIGO, index = 0)
    private String id;
}
