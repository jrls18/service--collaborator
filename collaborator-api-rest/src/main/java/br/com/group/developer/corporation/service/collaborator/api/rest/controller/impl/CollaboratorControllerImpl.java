package br.com.group.developer.corporation.service.collaborator.api.rest.controller.impl;


import br.com.group.developer.corporation.lib.logger.logger.LoggerService;
import br.com.group.developer.corporation.service.collaborator.api.rest.controller.CollaboratorController;
import br.com.group.developer.corporation.service.collaborator.api.rest.mapper.CollaboratorMapper;
import br.com.group.developer.corporation.service.collaborator.api.rest.mapper.MessageMapper;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.request.CollaboratorRequest;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.response.CollaboratorResponse;
import br.com.group.developer.corporation.service.collaborator.api.rest.security.model.UserPrinciple;
import br.com.group.developer.corporation.service.collaborator.api.rest.validation.CollaboratorValidator;
import br.com.group.developer.corporation.service.collaborator.core.service.CollaboratorService;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import br.com.grupo.developer.corporation.libcommons.message.response.PaginationResponse;
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
public class CollaboratorControllerImpl implements CollaboratorController {

    private final CollaboratorValidator validator;

    private final CollaboratorService service;

    private final LoggerService logger;

    @Override
    public ResponseEntity<MessageResponse> add(CollaboratorRequest request) {

        logger.info(request);

        this.validator.addRequestValidation(request);

        Message message = service.add(CollaboratorMapper.INSTANCE.toDomain(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        logger.info(response, HttpStatus.CREATED.name());

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MessageResponse> update(CollaboratorRequest request) {

        logger.info(request);

        this.validator.updateRequestValidation(request);

        Message message = service.update(CollaboratorMapper.INSTANCE.toDomain(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        logger.info(response, HttpStatus.ACCEPTED.name());

        return new ResponseEntity<>(
                response,
                HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<PaginationResponse<CollaboratorResponse>> paginationResponse(
            String searchTerm, String codEmpresa, String page, String size) {

        logger.info(null);

        this.validator.pathPaginationValidator(searchTerm,codEmpresa, page, size);

        PaginationResponse<CollaboratorResponse> responsePage =
               CollaboratorMapper.INSTANCE.toResponse(
                       service.search(
                               searchTerm,
                               codEmpresa,
                               Integer.parseInt(page),
                               Integer.parseInt(size)));

        logger.info(responsePage, HttpStatus.OK.name());

        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CollaboratorResponse> getUserProfile(UserPrinciple userDetails) {

        logger.info(userDetails);

        CollaboratorResponse collaboratorResponse =  CollaboratorMapper.INSTANCE.toResponse(
                service.getById(userDetails.getId()));

        logger.info(collaboratorResponse, HttpStatus.OK.name());

        return new ResponseEntity<>(
                collaboratorResponse,
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessageResponse> profileActivation(String uuid) {

        logger.info(null);

        MessageResponse messageResponse = MessageMapper.INSTANCE.toResponse(service.profileActivation(uuid));

        logger.info(messageResponse, HttpStatus.ACCEPTED.name());

        return new ResponseEntity<>(
                messageResponse,
                HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<CollaboratorResponse> getProfileId(String id) {

        logger.info(null);

        CollaboratorResponse collaboratorResponse =  CollaboratorMapper.INSTANCE.toResponse(
                service.getById(Long.parseLong(id)));

        logger.info(collaboratorResponse, HttpStatus.OK.name());

        return new ResponseEntity<>(
                collaboratorResponse,
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CollaboratorResponse> getProfileNotImageId(String id) {

        logger.info(null);

        CollaboratorResponse collaboratorResponse =  CollaboratorMapper.INSTANCE.toResponse(
                service.getByIdNotImage(Long.parseLong(id)));

        logger.info(collaboratorResponse, HttpStatus.OK.name());

        return new ResponseEntity<>(
                collaboratorResponse,
                HttpStatus.OK);
    }
}
