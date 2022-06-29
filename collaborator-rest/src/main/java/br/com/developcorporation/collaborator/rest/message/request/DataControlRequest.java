package br.com.developcorporation.collaborator.rest.message.request;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DataControlRequest implements Serializable {

    private static final long serialVersionUID = 8778838358311691676L;

    @JsonProperty(value = FieldConstant.CHAVE_UNICA_UUID, index = 0)
    private String singleKey;

    @JsonProperty(value = FieldConstant.DATA_CADASTRO, index = 1)
    private String dateRegister;
}
