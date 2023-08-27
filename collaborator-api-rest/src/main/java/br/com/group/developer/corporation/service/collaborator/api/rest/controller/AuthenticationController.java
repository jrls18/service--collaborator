package br.com.group.developer.corporation.service.collaborator.api.rest.controller;

import br.com.group.developer.corporation.service.collaborator.api.rest.constants.RouterConstants;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.request.CollaboratorRequest;
import br.com.grupo.developer.corporation.libcommons.constants.FieldAssistantConstants;
import br.com.grupo.developer.corporation.libcommons.message.request.LoginRequest;
import br.com.grupo.developer.corporation.libcommons.message.response.JwtResponse;
import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(RouterConstants.ROUTER_AUTHENTICATION)
@CrossOrigin(origins = "*")
public interface AuthenticationController {


    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "authenticateUser",
            authorizations = {
                    @Authorization(value = FieldAssistantConstants.CLIENT_ID),
                    @Authorization(value = FieldAssistantConstants.CLIENT_SECRET)})
    @PostMapping(value =  "/v1/signin", produces = RouterConstants.APPLICATION_JSON)
    ResponseEntity<JwtResponse> authenticateUser(@Validated @RequestBody LoginRequest loginRequest);

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "authenticateUser",
            authorizations = {
                    @Authorization(value = FieldAssistantConstants.CLIENT_ID),
                    @Authorization(value = FieldAssistantConstants.CLIENT_SECRET)
            })
    @PostMapping("/v1/teste/save")
    ResponseEntity<MessageResponse> add(@RequestBody CollaboratorRequest request);

}
