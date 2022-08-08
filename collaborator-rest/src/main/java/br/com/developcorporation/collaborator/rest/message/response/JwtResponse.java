package br.com.developcorporation.collaborator.rest.message.response;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonRootName(FieldConstant.AUTORIZACAO)
public class JwtResponse {

    @JsonProperty(value = FieldConstant.ACCESS_TOKEN)
    private String token;

    @JsonProperty(value = FieldConstant.TOKEN_TYPE)
    private String type;

    @JsonProperty(value = FieldConstant.EXPIRES_IN)
    private Date expire;
}