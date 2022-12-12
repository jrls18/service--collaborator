package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.core.service.AuthorizationService;
import br.com.developcorporation.collaborator.domain.logger.*;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.AuthorizationController;
import br.com.developcorporation.collaborator.rest.mapper.AuthorizationMapper;
import br.com.developcorporation.collaborator.rest.mapper.MessageMapper;
import br.com.developcorporation.collaborator.rest.message.request.AuthorizationRequest;
import br.com.developcorporation.collaborator.rest.message.response.AuthorizationResponse;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import br.com.developcorporation.collaborator.rest.validation.AuthorizationValidator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AuthorizationControllerImpl implements AuthorizationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationControllerImpl.class);

    private final AuthorizationService service;
    private final AuthorizationValidator validator;

    private final LogDomain logRest;

    @Override
    public ResponseEntity<MessageResponse> add(AuthorizationRequest request) {

        final String jsonRequest = logRest.jsonLogInfo(request, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        this.validator.addRequestValidation(request);

        Message message = service.add(AuthorizationMapper.INSTANCE.toDto(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        final String jsonResponse =  logRest.jsonLogInfo(response, HttpStatus.CREATED.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA,jsonResponse);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MessageResponse> update(AuthorizationRequest request) {

        final String jsonRequest = logRest.jsonLogInfo(request, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        this.validator.updateRequestValidation(request);

        Message message = service.update(AuthorizationMapper.INSTANCE.toDto(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        final String jsonResponse =  logRest.jsonLogInfo(response, HttpStatus.ACCEPTED.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA,jsonResponse);

        return new ResponseEntity<>(
                response,
                HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<AuthorizationResponse>> getAll() {

        final String jsonRequest = logRest.jsonLogInfoParams(null, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        List<AuthorizationResponse> responseList =  AuthorizationMapper.INSTANCE.toResponseList(service.getAll());

        final String jsonResponse = logRest.jsonLogInfo(responseList, HttpStatus.OK.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA,jsonResponse );

        return new ResponseEntity<>(
                responseList,
                HttpStatus.OK);
    }

}
