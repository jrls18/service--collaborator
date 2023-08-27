package br.com.group.developer.corporation.service.collaborator.api.rest.message.request;

import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(FieldDomainConstants.AUTORIZACAO)
public class AuthorizationRequest implements Serializable {

    private static final long serialVersionUID = 1024861767839304546L;

    @JsonProperty(value = FieldDomainConstants.CODIGO, index = 1, required = true)
    private String id;

    @JsonProperty(value = FieldDomainConstants.SIGLA_APP, index = 2, required = true)
    private String siglaApp;

    @JsonProperty(value = FieldDomainConstants.APPLICATION_NAME, index = 3, required = true)
    private String applicationName;

    @JsonProperty(value = FieldDomainConstants.SITUACAO, index = 4)
    private Status status;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status implements Serializable{

        private static final long serialVersionUID = 7998974176736946455L;

        @JsonProperty(value = FieldDomainConstants.CODIGO, index = 0)
        private String id;
    }
}
