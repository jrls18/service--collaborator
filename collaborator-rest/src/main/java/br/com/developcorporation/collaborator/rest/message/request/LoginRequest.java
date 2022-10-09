package br.com.developcorporation.collaborator.rest.message.request;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@JsonRootName(FieldConstant.LOGIN)
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = -2592838023087796778L;

    @JsonProperty(value = FieldConstant.USERNAME, index = 0, required = true)
    private String username;

    @JsonProperty(value = FieldConstant.PASSWORD, index = 1, required = true)
    private String password;
}
