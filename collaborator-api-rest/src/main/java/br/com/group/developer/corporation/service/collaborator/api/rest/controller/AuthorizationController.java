package br.com.group.developer.corporation.service.collaborator.api.rest.controller;

import br.com.group.developer.corporation.service.collaborator.api.rest.constants.RouterConstants;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.request.AuthorizationRequest;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.response.AuthorizationResponse;
import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(RouterConstants.ROUTER_AUTORIZACAO)
@CrossOrigin(origins = "*")
public interface AuthorizationController {


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/save")
    ResponseEntity<MessageResponse> add(@RequestBody AuthorizationRequest request);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/v1/save")
    ResponseEntity<MessageResponse> update(@RequestBody AuthorizationRequest request);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/v1/all", produces = RouterConstants.APPLICATION_JSON)
    ResponseEntity<List<AuthorizationResponse>> getAll();
}
