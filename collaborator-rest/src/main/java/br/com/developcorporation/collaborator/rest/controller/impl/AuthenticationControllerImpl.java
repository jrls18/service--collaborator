package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.AuthenticationController;
import br.com.developcorporation.collaborator.rest.controller.CollaboratorController;
import br.com.developcorporation.collaborator.rest.mapper.JwtMapper;
import br.com.developcorporation.collaborator.rest.message.request.CollaboratorRequest;
import br.com.developcorporation.collaborator.rest.message.request.LoginRequest;
import br.com.developcorporation.collaborator.rest.message.response.JwtResponse;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import br.com.developcorporation.collaborator.rest.security.service.AuthenticateService;
import br.com.developcorporation.collaborator.rest.validation.CollaboratorValidator;
import br.com.grupo.developer.corporation.lib.logger.logger.Logger;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RestController
public class AuthenticationControllerImpl implements AuthenticationController {

    private final CollaboratorValidator validator;

    private final CollaboratorController service;

    private final AuthenticateService authenticateService;


    @Override
    public ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest) {

        log.info(MessageConstant.REQUISICAO, Logger.info(loginRequest, MessageConstant.INICIALIZADO));

        this.validator.loginRequestValidator(loginRequest);

        JwtResponse response =  JwtMapper.INSTANCE.toResponse(authenticateService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword()));

        log.info(MessageConstant.RESPOSTA, Logger.info(response, HttpStatus.OK.value(), MessageConstant.FINALIZADO));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessageResponse> add(CollaboratorRequest request) {
        return service.add(request);
    }
}
