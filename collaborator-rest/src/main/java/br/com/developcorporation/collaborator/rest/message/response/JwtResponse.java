package br.com.developcorporation.collaborator.rest.message.response;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Date;

@JsonRootName(FieldConstant.AUTORIZACAO)
public class JwtResponse {

    @JsonProperty(value = FieldConstant.ACCESS_TOKEN)
    private String token;

    @JsonProperty(value = FieldConstant.TOKEN_TYPE)
    private String type;

    @JsonProperty(value = FieldConstant.EXPIRES_IN)
    private Date expire;

    public JwtResponse(String token, String type, Date expire){
        super();
        this.token = token;
        this.type = type;
        this.expire = expire;
    }
}