package br.com.developcorporation.collaborator.rest.controller;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.message.request.CollaboratorRequest;
import br.com.developcorporation.collaborator.rest.message.request.LoginRequest;
import br.com.developcorporation.collaborator.rest.message.response.JwtResponse;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(FieldConstant.ROUTER_AUTHENTICATION)
@CrossOrigin(origins = "*")
public interface AuthenticationController {


    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "authenticateUser",
            authorizations = {
                    @Authorization(value = "client_id"),
                    @Authorization(value = "client_secret")})
    @PostMapping(value =  "/v1/signin", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<JwtResponse> authenticateUser(@Validated @RequestBody LoginRequest loginRequest);

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "authenticateUser",
            authorizations = {
                    @Authorization(value = "client_id"),
                    @Authorization(value = "client_secret")
            })
    @PostMapping("/v1/teste/save")
    ResponseEntity<MessageResponse> add(@RequestBody CollaboratorRequest request);

}
