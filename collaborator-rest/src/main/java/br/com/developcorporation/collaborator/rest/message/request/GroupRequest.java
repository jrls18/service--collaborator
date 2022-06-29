package br.com.developcorporation.collaborator.rest.message.request;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(FieldConstant.GROUP)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GroupRequest implements Serializable {

    private static final long serialVersionUID = -1778875735408001688L;

    @JsonProperty(value = FieldConstant.CODIGO, index = 0)
    private String id;

    @JsonProperty(value = FieldConstant.DESCRICAO, index = 1)
    private String description;

    @JsonProperty(value = FieldConstant.DATA_CONTROL, index = 2)
    private DataControlRequest dataControl;
}
