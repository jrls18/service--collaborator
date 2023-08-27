package br.com.group.developer.corporation.service.collaborator.api.rest.controller.impl;


import br.com.group.developer.corporation.lib.logger.logger.LoggerService;
import br.com.group.developer.corporation.service.collaborator.api.rest.controller.AuthenticationController;
import br.com.group.developer.corporation.service.collaborator.api.rest.controller.CollaboratorController;
import br.com.group.developer.corporation.service.collaborator.api.rest.mapper.JwtMapper;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.request.CollaboratorRequest;
import br.com.group.developer.corporation.service.collaborator.api.rest.security.service.AuthenticateService;
import br.com.group.developer.corporation.service.collaborator.api.rest.validation.CollaboratorValidator;
import br.com.grupo.developer.corporation.libcommons.message.request.LoginRequest;
import br.com.grupo.developer.corporation.libcommons.message.response.JwtResponse;
import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RestController
@ConditionalOnProperty(value = "ASYNC", havingValue = "false")
public class AuthenticationControllerImpl implements AuthenticationController {

    private final CollaboratorValidator validator;

    private final CollaboratorController service;

    private final AuthenticateService authenticateService;

    private final LoggerService logger;


    @Override
    public ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest) {

        logger.info(loginRequest);

        this.validator.loginRequestValidator(loginRequest);

        JwtResponse response =  JwtMapper.INSTANCE.toResponse(authenticateService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword()));

        logger.info(response, HttpStatus.OK.name());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<MessageResponse> add(CollaboratorRequest request) {
        return service.add(request);
    }
}
