package br.com.group.developer.corporation.service.collaborator.api.rest.message.request;


import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecoverPasswordRequest implements Serializable {
    private static final long serialVersionUID = -765304178137866821L;

    @JsonProperty(value = FieldDomainConstants.USERNAME)
    private String username;
}
