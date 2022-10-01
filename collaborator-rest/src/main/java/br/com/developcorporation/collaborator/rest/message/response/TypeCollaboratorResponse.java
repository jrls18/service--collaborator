package br.com.developcorporation.collaborator.rest.message.response;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(FieldConstant.TIPO_USUARIO)
public class TypeCollaboratorResponse implements Serializable {

    private static final long serialVersionUID = 1318860786421795635L;

    @JsonProperty(value = FieldConstant.CODIGO, index = 0)
    private String id;

    @JsonProperty(value = FieldConstant.DESCRICAO, index = 1)
    private String name;
}
