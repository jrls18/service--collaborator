package br.com.developcorporation.collaborator.rest.controller;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.message.request.AuthorizationRequest;
import br.com.developcorporation.collaborator.rest.message.response.AuthorizationResponse;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface AuthorizationController {

    @PostMapping("/v1/save")
    ResponseEntity<MessageResponse> add(@RequestBody AuthorizationRequest request);

    @PutMapping("/v1/save")
    ResponseEntity<MessageResponse> update(@RequestBody AuthorizationRequest request);

    @GetMapping(value = "/v1/all", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<List<AuthorizationResponse>> getAll();
}
