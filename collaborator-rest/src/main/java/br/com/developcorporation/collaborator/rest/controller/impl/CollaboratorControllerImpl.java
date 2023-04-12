package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.CollaboratorController;
import br.com.developcorporation.collaborator.rest.mapper.CollaboratorMapper;
import br.com.developcorporation.collaborator.rest.mapper.MessageMapper;
import br.com.developcorporation.collaborator.rest.message.request.CollaboratorRequest;
import br.com.developcorporation.collaborator.rest.message.response.CollaboratorResponse;
import br.com.developcorporation.collaborator.rest.security.model.UserPrinciple;
import br.com.developcorporation.collaborator.rest.validation.CollaboratorValidator;
import br.com.grupo.developer.corporation.lib.logger.logger.Logger;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import br.com.grupo.developer.corporation.libcommons.message.response.PaginationResponse;
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


    @Override
    public ResponseEntity<MessageResponse> add(CollaboratorRequest request) {

        log.info(MessageConstant.REQUISICAO, Logger.info(request, MessageConstant.INICIALIZADO));

        this.validator.addRequestValidation(request);

        Message message = service.add(CollaboratorMapper.INSTANCE.toDomain(request));

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
    public ResponseEntity<MessageResponse> update(CollaboratorRequest request) {

        log.info(MessageConstant.REQUISICAO, Logger.info(request, MessageConstant.INICIALIZADO));

        this.validator.updateRequestValidation(request);

        Message message = service.update(CollaboratorMapper.INSTANCE.toDomain(request));

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
    public ResponseEntity<PaginationResponse<CollaboratorResponse>> paginationResponse(
            String searchTerm, String codEmpresa, String page, String size) {

        log.info(MessageConstant.REQUISICAO, Logger.info(null,
                MessageConstant.INICIALIZADO));

        this.validator.pathPaginationValidator(searchTerm,codEmpresa, page, size);

        PaginationResponse<CollaboratorResponse> responsePage =
               CollaboratorMapper.INSTANCE.toResponse(
                       service.search(
                               searchTerm,
                               codEmpresa,
                               Integer.parseInt(page),
                               Integer.parseInt(size)));

        log.info(MessageConstant.RESPOSTA, Logger.info(
                responsePage,
                HttpStatus.OK.value(),
                MessageConstant.FINALIZADO));

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CollaboratorResponse> getUserProfile(UserPrinciple userDetails) {

        log.info(MessageConstant.REQUISICAO, Logger.info(userDetails,
                MessageConstant.INICIALIZADO));

        CollaboratorResponse collaboratorResponse =  CollaboratorMapper.INSTANCE.toResponse(
                service.getById(userDetails.getId()));

        log.info(MessageConstant.RESPOSTA, Logger.info(
                collaboratorResponse,
                HttpStatus.OK.value(),
                MessageConstant.FINALIZADO));

        return new ResponseEntity<>(
                collaboratorResponse,
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CollaboratorResponse> getProfileId(String id) {

        log.info(MessageConstant.REQUISICAO, Logger.info(null,
                MessageConstant.INICIALIZADO));

        CollaboratorResponse collaboratorResponse =  CollaboratorMapper.INSTANCE.toResponse(
                service.getByIdNotImage(Long.parseLong(id)));

        log.info(MessageConstant.RESPOSTA, Logger.info(
                collaboratorResponse,
                HttpStatus.OK.value(),
                MessageConstant.FINALIZADO));

        return new ResponseEntity<>(
                collaboratorResponse,
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CollaboratorResponse> getProfileNotImageId(String id) {

        log.info(MessageConstant.REQUISICAO, Logger.info(null,
                MessageConstant.INICIALIZADO));

        CollaboratorResponse collaboratorResponse =  CollaboratorMapper.INSTANCE.toResponse(
                service.getByIdNotImage(Long.parseLong(id)));

        log.info(MessageConstant.RESPOSTA, Logger.info(
                collaboratorResponse,
                HttpStatus.OK.value(),
                MessageConstant.FINALIZADO));

        return new ResponseEntity<>(
                collaboratorResponse,
                HttpStatus.OK);
    }
}
