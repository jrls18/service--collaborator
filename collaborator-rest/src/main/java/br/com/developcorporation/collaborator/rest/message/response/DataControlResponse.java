package br.com.developcorporation.collaborator.rest.message.response;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataControlResponse implements Serializable {

    private static final long serialVersionUID = -6382478048955975458L;

    @JsonProperty(value = FieldConstant.CHAVE_UNICA_UUID, index = 0)
    private String singleKey;

    @JsonProperty(value = FieldConstant.DATA_CADASTRO, index = 1)
    private String dateRegister;

}
