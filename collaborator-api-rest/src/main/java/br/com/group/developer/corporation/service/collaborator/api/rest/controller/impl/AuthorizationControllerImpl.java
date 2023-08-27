package br.com.group.developer.corporation.service.collaborator.api.rest.controller.impl;


import br.com.group.developer.corporation.lib.logger.logger.LoggerService;
import br.com.group.developer.corporation.service.collaborator.api.rest.controller.AuthorizationController;
import br.com.group.developer.corporation.service.collaborator.api.rest.mapper.AuthorizationMapper;
import br.com.group.developer.corporation.service.collaborator.api.rest.mapper.MessageMapper;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.request.AuthorizationRequest;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.response.AuthorizationResponse;
import br.com.group.developer.corporation.service.collaborator.api.rest.validation.AuthorizationValidator;
import br.com.group.developer.corporation.service.collaborator.core.service.AuthorizationService;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@ConditionalOnProperty(value = "ASYNC", havingValue = "false")
public class AuthorizationControllerImpl implements AuthorizationController {

    private final AuthorizationService service;
    private final AuthorizationValidator validator;

    private final LoggerService logger;

    @Override
    public ResponseEntity<MessageResponse> add(AuthorizationRequest request) {

        logger.info(request);

        this.validator.addRequestValidation(request);

        Message message = service.add(AuthorizationMapper.INSTANCE.toDto(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        logger.info(response, HttpStatus.CREATED.name());

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MessageResponse> update(AuthorizationRequest request) {

        logger.info(request);

        this.validator.updateRequestValidation(request);

        Message message = service.update(AuthorizationMapper.INSTANCE.toDto(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        logger.info(response, HttpStatus.ACCEPTED.name());

        return new ResponseEntity<>(
                response,
                HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<AuthorizationResponse>> getAll() {

        logger.info(null);

        List<AuthorizationResponse> responseList =  AuthorizationMapper.INSTANCE.toResponseList(service.getAll());

        logger.info(responseList, HttpStatus.OK.name());

        return new ResponseEntity<>(
                responseList,
                HttpStatus.OK);
    }

}
