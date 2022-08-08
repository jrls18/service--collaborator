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
@JsonRootName(FieldConstant.FOLLOW_UP)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FollowUpResponse implements Serializable {

    private static final long serialVersionUID = -7354683021163012289L;

    @JsonProperty(value = FieldConstant.CODIGO, index = 0)
    private Long id;

    @JsonProperty(value = FieldConstant.DESCRICAO, index = 1)
    private String description;

    @JsonProperty(value = FieldConstant.DATA_CADASTRO, index = 2)
    private String dateRegister;

}
