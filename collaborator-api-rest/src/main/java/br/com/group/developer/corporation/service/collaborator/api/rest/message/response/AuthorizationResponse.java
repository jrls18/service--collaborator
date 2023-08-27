package br.com.group.developer.corporation.service.collaborator.api.rest.message.response;

import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import br.com.grupo.developer.corporation.libcommons.constants.FieldAssistantConstants;
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
@JsonRootName(FieldDomainConstants.AUTORIZACAO)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthorizationResponse implements Serializable {

    private static final long serialVersionUID = -5068719238130048172L;

    @JsonProperty(value = FieldDomainConstants.CODIGO, index = 0)
    private Long id;

    @JsonProperty(value = FieldAssistantConstants.CLIENT_ID, index = 1)
    private String clientId;

    @JsonProperty(value = FieldAssistantConstants.CLIENT_SECRET, index = 2)
    private String clientSecret;

    @JsonProperty(value = FieldDomainConstants.APPLICATION_NAME, index = 3)
    private String applicationName;

    @JsonProperty(value = FieldDomainConstants.SIGLA_APP, index = 4)
    private String siglaApp;

    @JsonProperty(value = FieldDomainConstants.DATA_HORA_CADASTRO, index = 5)
    private String dateRegister;

    @JsonProperty(value = FieldDomainConstants.SITUACAO, index = 6)
    private Status status;

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status implements Serializable {

        private static final long serialVersionUID = -2352115967754444161L;

        @JsonProperty(value = FieldDomainConstants.CODIGO, index = 0)
        private Long id;

        @JsonProperty(value = FieldDomainConstants.DESCRICAO, index = 1)
        private String description;
    }
}
