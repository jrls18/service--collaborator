package br.com.developcorporation.collaborator.rest.message.response;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
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
@JsonRootName(FieldConstant.STATUS)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GroupResponse implements Serializable {

    private static final long serialVersionUID = -9195643565354707923L;

    @JsonProperty(value = FieldConstant.CODIGO, index = 0)
    private Long id;

    @JsonProperty(value = FieldConstant.DESCRICAO, index = 1)
    private String description;

    @JsonProperty(value = FieldConstant.DATA_CONTROL, index = 2)
    private DataControlResponse dataControl;
}
