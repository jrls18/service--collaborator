package br.com.developcorporation.collaborator.rest.controller;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.message.request.AuthorizationRequest;
import br.com.developcorporation.collaborator.rest.message.response.AuthorizationResponse;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface AuthorizationController {

    @ApiOperation(value = "Cria uma nova chave de acesso. Client Id e Client Secret",
            authorizations = {
                    @Authorization(value="client_id"),
                    @Authorization(value = "client_secret")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = MessageResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 422, message = "UnProcessable Entity", response = MessageResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = MessageResponse.class)})
    @PostMapping("/v1/save")
    ResponseEntity<MessageResponse> add(@RequestBody AuthorizationRequest request);


    @ApiOperation(value = "Altera os dados de uma chave de acesso. Client Id e Client Secret",
            authorizations = {
                    @Authorization(value="client_id"),
                    @Authorization(value = "client_secret")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request", response = MessageResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 422, message = "UnProcessable Entity", response = MessageResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = MessageResponse.class)})
    @PutMapping("/v1/save")
    ResponseEntity<MessageResponse> update(@RequestBody AuthorizationRequest request);

    @ApiOperation(value = "Consulta todas as chaves de acesso.",
            authorizations = {
                    @Authorization(value="client_id"),
                    @Authorization(value = "client_secret")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = AuthorizationResponse[].class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal server error", response = MessageResponse.class)})
    @GetMapping(value = "/v1/all", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<List<AuthorizationResponse>> getAll();
}
