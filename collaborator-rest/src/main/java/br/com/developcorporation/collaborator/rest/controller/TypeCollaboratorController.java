package br.com.developcorporation.collaborator.rest.controller;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.message.response.StatusResponse;
import br.com.developcorporation.collaborator.rest.message.response.TypeCollaboratorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface TypeCollaboratorController {

    @GetMapping(value = "/v1/all", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<List<TypeCollaboratorResponse>> getAll();

}
