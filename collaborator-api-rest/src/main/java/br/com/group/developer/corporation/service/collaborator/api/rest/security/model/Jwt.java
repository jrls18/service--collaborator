package br.com.group.developer.corporation.service.collaborator.api.rest.security.model;

import br.com.group.developer.corporation.service.collaborator.api.rest.constants.RouterConstants;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(RouterConstants.ROUTER_AUTORIZACAO)
public class Jwt implements Serializable {

    private static final long serialVersionUID = 5308897202897798301L;

    private String token;

    private String type;

    private Date expire;
}
