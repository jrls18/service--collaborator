package br.com.group.developer.corporation.service.collaborator.api.rest.controller.impl;


import br.com.group.developer.corporation.lib.logger.logger.LoggerService;
import br.com.group.developer.corporation.service.collaborator.api.rest.controller.StatusController;
import br.com.group.developer.corporation.service.collaborator.api.rest.mapper.StatusMapper;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.response.StatusResponse;
import br.com.group.developer.corporation.service.collaborator.core.service.StatusService;
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
public class StatusControllerImpl implements StatusController {

    private final StatusService service;

    private final LoggerService logger;


    @Override
    public ResponseEntity<List<StatusResponse>> getAll() {

        logger.info(null);

        List<StatusResponse> response = StatusMapper.INSTANCE.toResponseList(service.getByAll());

        logger.info(response, HttpStatus.OK.name());

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);

    }
}
