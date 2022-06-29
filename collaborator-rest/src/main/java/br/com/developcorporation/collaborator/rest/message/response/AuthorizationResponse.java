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
@JsonRootName(FieldConstant.AUTORIZACAO)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthorizationResponse implements Serializable {

    private static final long serialVersionUID = -5068719238130048172L;

    @JsonProperty(value = FieldConstant.CODIGO, index = 0)
    private Long id;

    @JsonProperty(value = FieldConstant.CLIENT_ID, index = 1)
    private String clientId;

    @JsonProperty(value = FieldConstant.CLIENT_SECRET, index = 2)
    private String clientSecret;

    @JsonProperty(value = FieldConstant.APPLICATION_NAME, index = 3)
    private String applicationName;

    @JsonProperty(value = FieldConstant.SIGLA_APP, index = 4)
    private String siglaApp;

    @JsonProperty(value = FieldConstant.DATA_CADASTRO, index = 5)
    private String dateRegister;

    @JsonProperty(value = FieldConstant.STATUS, index = 6)
    private Status status;

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status implements Serializable {

        private static final long serialVersionUID = -2352115967754444161L;

        @JsonProperty(value = FieldConstant.CODIGO, index = 0)
        private Long id;

        @JsonProperty(value = FieldConstant.DESCRICAO, index = 1)
        private String description;
    }
}
