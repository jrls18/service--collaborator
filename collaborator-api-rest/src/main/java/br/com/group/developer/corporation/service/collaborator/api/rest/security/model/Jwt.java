package br.com.group.developer.corporation.service.collaborator.api.rest.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Jwt implements Serializable {

    private static final long serialVersionUID = 5308897202897798301L;

    private String token;

    private String type;

    private Date expire;
}
