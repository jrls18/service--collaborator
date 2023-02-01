package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.StatusController;
import br.com.developcorporation.collaborator.domain.logger.*;
import br.com.developcorporation.collaborator.rest.mapper.StatusMapper;
import br.com.developcorporation.collaborator.core.service.StatusService;
import br.com.developcorporation.collaborator.rest.message.response.StatusResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class StatusControllerImpl implements StatusController {

    private static final Logger LOG = LoggerFactory.getLogger(StatusControllerImpl.class);

    private final StatusService service;

    private final LogDomain logRest;

    @Override
    public ResponseEntity<List<StatusResponse>> getAll() {

        final String jsonRequest = logRest.jsonLogInfoParams(null, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        List<StatusResponse> statusResponses = StatusMapper.INSTANCE.toResponseList(service.getByAll());

        final String jsonResponse = logRest.jsonLogInfo(statusResponses, HttpStatus.OK.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(
                statusResponses,
                HttpStatus.OK);

    }
}
