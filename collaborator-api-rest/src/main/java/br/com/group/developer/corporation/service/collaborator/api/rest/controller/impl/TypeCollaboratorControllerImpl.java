package br.com.group.developer.corporation.service.collaborator.api.rest.controller.impl;

import br.com.group.developer.corporation.lib.logger.logger.LoggerService;
import br.com.group.developer.corporation.service.collaborator.api.rest.controller.TypeCollaboratorController;
import br.com.group.developer.corporation.service.collaborator.api.rest.mapper.TypeCollaboratorMapper;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.response.TypeCollaboratorResponse;
import br.com.group.developer.corporation.service.collaborator.core.service.TypeCollaboratorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@AllArgsConstructor
@RestController
@ConditionalOnProperty(value = "ASYNC", havingValue = "false")
public class TypeCollaboratorControllerImpl implements TypeCollaboratorController {

    private final TypeCollaboratorService service;

    private final LoggerService logger;

    @Override
    public ResponseEntity<List<TypeCollaboratorResponse>> getAll() {

        logger.info(null);

        List<TypeCollaboratorResponse> responses = TypeCollaboratorMapper.INSTANCE.toResponseList(service.getByAll());

        logger.info(responses, HttpStatus.OK.name());

        return new ResponseEntity<>(
                responses,
                HttpStatus.OK);
    }
}
