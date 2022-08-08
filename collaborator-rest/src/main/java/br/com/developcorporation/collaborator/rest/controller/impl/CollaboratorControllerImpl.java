package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.CollaboratorController;
import br.com.developcorporation.collaborator.rest.logger.LogRest;
import br.com.developcorporation.collaborator.rest.mapper.CollaboratorMapper;
import br.com.developcorporation.collaborator.rest.mapper.JwtMapper;
import br.com.developcorporation.collaborator.rest.mapper.MessageMapper;
import br.com.developcorporation.collaborator.rest.message.request.CollaboratorRequest;
import br.com.developcorporation.collaborator.rest.message.request.LoginRequest;
import br.com.developcorporation.collaborator.rest.message.response.CollaboratorResponse;
import br.com.developcorporation.collaborator.rest.message.response.JwtResponse;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import br.com.developcorporation.collaborator.rest.message.response.PaginationResponse;
import br.com.developcorporation.collaborator.rest.security.model.UserPrinciple;
import br.com.developcorporation.collaborator.rest.security.service.AuthenticateService;
import br.com.developcorporation.collaborator.rest.validation.CollaboratorValidator;
import br.com.developcorporation.lib.commons.monitorable.SpringLogger;
import br.com.developcorporation.lib.commons.util.Convert;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping(FieldConstant.ROUTER_COLLABORATOR)
@CrossOrigin(origins = "*")
public class CollaboratorControllerImpl implements CollaboratorController {

    private final CollaboratorValidator validator;

    private final CollaboratorService service;

    private final AuthenticateService authenticateService;

    private final LogRest logRest;


    @Override
    public ResponseEntity<MessageResponse> add(CollaboratorRequest request) {
        final String jsonRequest =  logRest.jsonLogInfo(request, MessageConstant.INICIALIZADO);

        log.info(MessageConstant.REQUISICAO, jsonRequest);

        this.validator.addRequestValidation(request);

        Message message = service.add(CollaboratorMapper.INSTANCE.toDomain(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        final String jsonResponse = logRest.jsonLogInfo(response, HttpStatus.CREATED.value(), MessageConstant.FINALIZADO);

        log.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MessageResponse> update(CollaboratorRequest request) {
        final String jsonRequest = logRest.jsonLogInfo(request, MessageConstant.INICIALIZADO);

        log.info(MessageConstant.REQUISICAO, jsonRequest);

        this.validator.updateRequestValidation(request);

        Message message = service.update(CollaboratorMapper.INSTANCE.toDomain(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        final String jsonResponse = logRest.jsonLogInfo(response, HttpStatus.ACCEPTED.value(), MessageConstant.FINALIZADO);

        log.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(
                response,
                HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest) {

        this.validator.loginRequestValidator(loginRequest);

        JwtResponse response =  JwtMapper.INSTANCE.toResponse(authenticateService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaginationResponse<CollaboratorResponse>> paginationResponse(
            String searchTerm, String page, String size) {

        this.validator.pathPaginationValidator(searchTerm, page, size);

        PaginationResponse<CollaboratorResponse> response =
               CollaboratorMapper.INSTANCE.toResponse(
                       service.search(
                               searchTerm,
                               Integer.parseInt(page),
                               Integer.parseInt(size)));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CollaboratorResponse> getUserProfile(UserPrinciple userDetails) {
        return new ResponseEntity<>(
                CollaboratorMapper.INSTANCE.toResponse(
                        service.getById(userDetails.getId())),
                HttpStatus.OK);
    }

}
