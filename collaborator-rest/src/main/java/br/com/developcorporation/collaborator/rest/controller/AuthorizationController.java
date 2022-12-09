package br.com.developcorporation.collaborator.rest.controller;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.message.request.AuthorizationRequest;
import br.com.developcorporation.collaborator.rest.message.response.AuthorizationResponse;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(FieldConstant.AUTORIZACAO)
@CrossOrigin(origins = "*")
public interface AuthorizationController {


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/save")
    ResponseEntity<MessageResponse> add(@RequestBody AuthorizationRequest request);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/v1/save")
    ResponseEntity<MessageResponse> update(@RequestBody AuthorizationRequest request);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/v1/all", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<List<AuthorizationResponse>> getAll();
}
