package br.com.developcorporation.collaborator.rest.message.request;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(FieldConstant.AUTORIZACAO)
public class AuthorizationRequest implements Serializable {

    private static final long serialVersionUID = 1024861767839304546L;

    @JsonProperty(value = FieldConstant.CODIGO, index = 1, required = true)
    private String id;

    @JsonProperty(value = FieldConstant.SIGLA_APP, index = 2, required = true)
    private String siglaApp;

    @JsonProperty(value = FieldConstant.APPLICATION_NAME, index = 3, required = true)
    private String applicationName;

    @JsonProperty(value = FieldConstant.STATUS, index = 4)
    private Status status;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status implements Serializable{

        private static final long serialVersionUID = 7998974176736946455L;

        @JsonProperty(value = FieldConstant.CODIGO, index = 0)
        private String id;
    }
}
