package br.com.developcorporation.collaborator.rest.controller;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.message.response.FollowUpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


public interface FollowUpController {
    @GetMapping(value = "/v1/all", produces = FieldConstant.APPLICATION_JSON)
    ResponseEntity<List<FollowUpResponse>> getAll();
}
