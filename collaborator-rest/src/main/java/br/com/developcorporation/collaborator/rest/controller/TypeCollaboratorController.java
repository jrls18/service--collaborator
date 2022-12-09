package br.com.developcorporation.collaborator.rest.controller;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.message.response.StatusResponse;
import br.com.developcorporation.collaborator.rest.message.response.TypeCollaboratorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping(FieldConstant.TIPO_USUARIO)
@CrossOrigin(origins = "*")
public interface TypeCollaboratorController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/v1/all", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<List<TypeCollaboratorResponse>> getAll();

}
