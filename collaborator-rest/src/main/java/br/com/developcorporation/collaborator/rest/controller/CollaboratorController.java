package br.com.developcorporation.collaborator.rest.controller;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.message.request.CollaboratorRequest;
import br.com.developcorporation.collaborator.rest.message.response.CollaboratorResponse;
import br.com.developcorporation.collaborator.rest.security.model.UserPrinciple;

import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import br.com.grupo.developer.corporation.libcommons.message.response.PaginationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping(FieldConstant.ROUTER_COLLABORATOR)
@CrossOrigin(origins = "*")
public interface CollaboratorController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/save")
    ResponseEntity<MessageResponse> add(@RequestBody CollaboratorRequest request);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/v1/save")
    ResponseEntity<MessageResponse> update(@Validated @RequestBody CollaboratorRequest request);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<PaginationResponse<CollaboratorResponse>> paginationResponse(
            @RequestParam(required = false) String searchTerm,
            @RequestParam String codEmpresa,
            @RequestParam(defaultValue = "0") String page,
            @RequestParam(defaultValue = "10") String size);


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/v1/profile", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<CollaboratorResponse> getUserProfile(@AuthenticationPrincipal UserPrinciple userDetails);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/v1/profile/{codigo}", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<CollaboratorResponse> getProfileId(@PathVariable("codigo") final String id);


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/internal/v1/profile/{codigo}", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<CollaboratorResponse> getProfileNotImageId(@PathVariable("codigo") final String id);
}
