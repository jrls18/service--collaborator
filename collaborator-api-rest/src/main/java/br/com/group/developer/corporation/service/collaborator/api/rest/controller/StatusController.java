package br.com.group.developer.corporation.service.collaborator.api.rest.controller;

import br.com.group.developer.corporation.service.collaborator.api.rest.constants.RouterConstants;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.response.StatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping(RouterConstants.ROUTER_STATUS)
@CrossOrigin(origins = "*")
public interface StatusController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/v1/all", produces = RouterConstants.APPLICATION_JSON)
    ResponseEntity<List<StatusResponse>> getAll();

}
