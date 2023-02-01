package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.domain.logger.*;
import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.CollaboratorController;
import br.com.developcorporation.collaborator.rest.mapper.CollaboratorMapper;
import br.com.developcorporation.collaborator.rest.mapper.MessageMapper;
import br.com.developcorporation.collaborator.rest.message.request.CollaboratorRequest;
import br.com.developcorporation.collaborator.rest.message.response.CollaboratorResponse;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import br.com.developcorporation.collaborator.rest.message.response.PaginationResponse;
import br.com.developcorporation.collaborator.rest.security.model.UserPrinciple;
import br.com.developcorporation.collaborator.rest.security.service.AuthenticateService;
import br.com.developcorporation.collaborator.rest.validation.CollaboratorValidator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RestController
public class CollaboratorControllerImpl implements CollaboratorController {

    private final CollaboratorValidator validator;

    private final CollaboratorService service;

    private final AuthenticateService authenticateService;

    private final LogDomain logRest;


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
    public ResponseEntity<PaginationResponse<CollaboratorResponse>> paginationResponse(
            String searchTerm, String page, String size) {

        final String jsonRequest = logRest.jsonLogInfoParams("searchTerm=" +searchTerm + "&page="+page+"&size="+size, MessageConstant.INICIALIZADO);

        log.info(MessageConstant.REQUISICAO, jsonRequest);

        this.validator.pathPaginationValidator(searchTerm, page, size);

        PaginationResponse<CollaboratorResponse> response =
               CollaboratorMapper.INSTANCE.toResponse(
                       service.search(
                               searchTerm,
                               Integer.parseInt(page),
                               Integer.parseInt(size)));


        final String jsonResponse = logRest.jsonLogInfo(response, MessageConstant.FINALIZADO);

        log.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CollaboratorResponse> getUserProfile(UserPrinciple userDetails) {

        final String jsonRequest = logRest.jsonLogInfo(userDetails, MessageConstant.INICIALIZADO);

        log.info(MessageConstant.REQUISICAO, jsonRequest);

        CollaboratorResponse collaboratorResponse =  CollaboratorMapper.INSTANCE.toResponse(
                service.getById(userDetails.getId()));

        final String jsonResponse = logRest.jsonLogInfo(collaboratorResponse, MessageConstant.FINALIZADO);

        log.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(
                collaboratorResponse,
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CollaboratorResponse> getProfileId(String id) {
        final String jsonRequest = logRest.jsonLogInfoParams(id, MessageConstant.INICIALIZADO);

        log.info(MessageConstant.REQUISICAO, jsonRequest);

        CollaboratorResponse collaboratorResponse =  CollaboratorMapper.INSTANCE.toResponse(
                service.getByIdNotImage(Long.parseLong(id)));

        final String response = logRest.jsonLogInfo(collaboratorResponse, MessageConstant.FINALIZADO);

        log.info(MessageConstant.RESPOSTA, response);

        return new ResponseEntity<>(
                collaboratorResponse,
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CollaboratorResponse> getProfileNotImageId(String id) {
        final String jsonRequest = logRest.jsonLogInfoParams(id, MessageConstant.INICIALIZADO);

        log.info(MessageConstant.REQUISICAO, jsonRequest);

        CollaboratorResponse collaboratorResponse =  CollaboratorMapper.INSTANCE.toResponse(
                service.getByIdNotImage(Long.parseLong(id)));

        final String response = logRest.jsonLogInfo(collaboratorResponse, MessageConstant.FINALIZADO);

        log.info(MessageConstant.RESPOSTA, response);

        return new ResponseEntity<>(
                collaboratorResponse,
                HttpStatus.OK);
    }

}
