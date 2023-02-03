package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.core.service.AuthorizationService;

import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.AuthorizationController;
import br.com.developcorporation.collaborator.rest.mapper.AuthorizationMapper;
import br.com.developcorporation.collaborator.rest.mapper.MessageMapper;
import br.com.developcorporation.collaborator.rest.message.request.AuthorizationRequest;
import br.com.developcorporation.collaborator.rest.message.response.AuthorizationResponse;
import br.com.developcorporation.collaborator.rest.validation.AuthorizationValidator;
import br.com.grupo.developer.corporation.lib.logger.logger.Logger;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class AuthorizationControllerImpl implements AuthorizationController {

    private final AuthorizationService service;
    private final AuthorizationValidator validator;

    @Override
    public ResponseEntity<MessageResponse> add(AuthorizationRequest request) {

        log.info(MessageConstant.REQUISICAO, Logger.info(request, MessageConstant.INICIALIZADO));

        this.validator.addRequestValidation(request);

        Message message = service.add(AuthorizationMapper.INSTANCE.toDto(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        log.info(MessageConstant.RESPOSTA, Logger.info(
                response,
                HttpStatus.CREATED.value(),
                MessageConstant.FINALIZADO));

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MessageResponse> update(AuthorizationRequest request) {

        log.info(MessageConstant.REQUISICAO, Logger.info(request, MessageConstant.INICIALIZADO));

        this.validator.updateRequestValidation(request);

        Message message = service.update(AuthorizationMapper.INSTANCE.toDto(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        log.info(MessageConstant.RESPOSTA, Logger.info(
                response,
                HttpStatus.ACCEPTED.value(),
                MessageConstant.FINALIZADO));

        return new ResponseEntity<>(
                response,
                HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<AuthorizationResponse>> getAll() {

        log.info(MessageConstant.REQUISICAO, Logger.info(
                null,
                MessageConstant.INICIALIZADO));

        List<AuthorizationResponse> responseList =  AuthorizationMapper.INSTANCE.toResponseList(service.getAll());

        log.info(MessageConstant.RESPOSTA, Logger.info(
                responseList,
                HttpStatus.OK.value(),
                MessageConstant.FINALIZADO));

        return new ResponseEntity<>(
                responseList,
                HttpStatus.OK);
    }

}
