package br.com.group.developer.corporation.service.collaborator.api.rest.controller;


import br.com.group.developer.corporation.service.collaborator.api.rest.constants.RouterConstants;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.request.CollaboratorRequest;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.response.CollaboratorResponse;
import br.com.group.developer.corporation.service.collaborator.api.rest.security.model.UserPrinciple;
import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import br.com.grupo.developer.corporation.libcommons.message.response.PaginationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping(RouterConstants.ROUTER_COLLABORATOR)
@CrossOrigin(origins = "*")
public interface CollaboratorController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/save")
    ResponseEntity<MessageResponse> add(@RequestBody CollaboratorRequest request);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/v1/save")
    ResponseEntity<MessageResponse> update(@Validated @RequestBody CollaboratorRequest request);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "", produces = RouterConstants.APPLICATION_JSON)
    ResponseEntity<PaginationResponse<CollaboratorResponse>> paginationResponse(
            @RequestParam(required = false) String searchTerm,
            @RequestParam String codEmpresa,
            @RequestParam(defaultValue = "0") String page,
            @RequestParam(defaultValue = "10") String size);


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/v1/profile", produces = RouterConstants.APPLICATION_JSON)
    ResponseEntity<CollaboratorResponse> getUserProfile(@AuthenticationPrincipal UserPrinciple userDetails);


    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "/v1/profile/activation/{id}", produces = RouterConstants.APPLICATION_JSON)
    ResponseEntity<MessageResponse> profileActivation(@PathVariable("id") final String uuid);


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/v1/profile/{codigo}", produces = RouterConstants.APPLICATION_JSON)
    ResponseEntity<CollaboratorResponse> getProfileId(@PathVariable("codigo") final String id);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/internal/v1/profile/{codigo}", produces = RouterConstants.APPLICATION_JSON)
    ResponseEntity<CollaboratorResponse> getProfileNotImageId(@PathVariable("codigo") final String id);
}
