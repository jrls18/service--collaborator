package br.com.developcorporation.collaborator.rest.controller;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.message.request.CollaboratorRequest;
import br.com.developcorporation.collaborator.rest.message.request.LoginRequest;
import br.com.developcorporation.collaborator.rest.message.response.CollaboratorResponse;
import br.com.developcorporation.collaborator.rest.message.response.JwtResponse;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import br.com.developcorporation.collaborator.rest.message.response.PaginationResponse;
import br.com.developcorporation.collaborator.rest.security.model.UserPrinciple;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

public interface CollaboratorController {


    @PostMapping("/auth/v1/save")
    ResponseEntity<MessageResponse> add(@RequestBody CollaboratorRequest request);

    @PutMapping("/v1/save")
    ResponseEntity<MessageResponse> update(@Validated @RequestBody CollaboratorRequest request);

    @PostMapping(value =  "/auth/v1/signin", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<JwtResponse> authenticateUser(@Validated @RequestBody LoginRequest loginRequest);

    @GetMapping(value = "", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<PaginationResponse<CollaboratorResponse>> paginationResponse(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") String page,
            @RequestParam(defaultValue = "10") String size);

    @GetMapping(path = "/v1/profile", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<CollaboratorResponse> getUserProfile(@AuthenticationPrincipal UserPrinciple userDetails);


    /*
    @GetMapping(value = "/v1/get/id={id}", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<CompanyResponse> getById(@PathVariable("id") final String id);

    @GetMapping(value = "/v1/get/cnpj_cpf={cnpj_cpf}", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<CompanyResponse> getByCnpj(@PathVariable("cnpj_cpf") final String cnpj);

     */
}
