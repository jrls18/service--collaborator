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
public class StatusResponse implements Serializable {

    private static final long serialVersionUID = 2230202088564724687L;

    @JsonProperty(value = FieldConstant.CODIGO, index = 0)
    private Long id;

    @JsonProperty(value = FieldConstant.DESCRICAO, index = 1)
    private String description;

    @JsonProperty(value = FieldConstant.DATA_CADASTRO, index = 2)
    private String dateRegister;
}
