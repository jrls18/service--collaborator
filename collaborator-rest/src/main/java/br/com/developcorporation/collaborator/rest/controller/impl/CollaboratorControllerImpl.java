package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.CollaboratorController;
import br.com.developcorporation.collaborator.rest.logger.LogRest;
import br.com.developcorporation.collaborator.rest.mapper.JwtMapper;
import br.com.developcorporation.collaborator.rest.message.request.CollaboratorRequest;
import br.com.developcorporation.collaborator.rest.message.request.LoginRequest;
import br.com.developcorporation.collaborator.rest.message.response.JwtResponse;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping(FieldConstant.ROUTER_COLLABORATOR)
@CrossOrigin(origins = "*")
public class CollaboratorControllerImpl implements CollaboratorController {

    private final CollaboratorValidator validator;
    //private final CollaboratorService service;

    private final AuthenticateService authenticateService;

    private final LogRest logRest;


    @Override
    public ResponseEntity<MessageResponse> add(CollaboratorRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<MessageResponse> update(CollaboratorRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest) {

        this.validator.loginRequestValidator(loginRequest);

        JwtResponse response =  JwtMapper.INSTANCE.toResponse(authenticateService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    private static final Logger LOG = LoggerFactory.getLogger(CompanyControllerImpl.class);

    private final CompanyValidator validator;
    private final CollaboratorService service;

    private final LogRest logRest;

    @Override
    public ResponseEntity<MessageResponse> add(CompanyRequest request) {

        final String jsonRequest =  logRest.jsonLogInfo(request, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        this.validator.addRequestValidation(request);

        Message message = service.add(CompanyMapper.INSTANCE.toDto(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        final String jsonResponse = logRest.jsonLogInfo(response, HttpStatus.CREATED.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MessageResponse> update(CompanyRequest request) {

        final String jsonRequest = logRest.jsonLogInfo(request, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        this.validator.updateRequestValidation(request);

        Message message = service.update(CompanyMapper.INSTANCE.toDto(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        final String jsonResponse = logRest.jsonLogInfo(response, HttpStatus.ACCEPTED.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(
                response,
                HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<CompanyResponse> getById(String id) {

        final String jsonRequest = logRest.jsonLogInfo(id, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        this.validator.pathVariableId(id);

        Company company = service.getById(Long.parseLong(id));

        CompanyResponse response =  CompanyMapper.INSTANCE.toResponse(company);

        final String jsonResponse = logRest.jsonLogInfo(response, HttpStatus.OK.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CompanyResponse> getByCnpj(String cnpj) {

        final String jsonRequest = logRest.jsonLogInfo(cnpj, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        this.validator.pathVariableCnpj(cnpj);

        Company company = service.getByCnpj(cnpj);

        CompanyResponse response =  CompanyMapper.INSTANCE.toResponse(company);

        final String jsonResponse =  logRest.jsonLogInfo(response, HttpStatus.OK.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA,jsonResponse);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

     */

}
