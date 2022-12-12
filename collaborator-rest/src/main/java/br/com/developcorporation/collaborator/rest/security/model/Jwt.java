package br.com.developcorporation.collaborator.rest.security.model;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(FieldConstant.ROUTER_AUTORIZACAO)
public class Jwt implements Serializable {

    private static final long serialVersionUID = 5308897202897798301L;

    private String token;

    private String type;

    private Date expire;


}
