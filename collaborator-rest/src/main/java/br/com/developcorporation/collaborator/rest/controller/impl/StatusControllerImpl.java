package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.StatusController;
import br.com.developcorporation.collaborator.rest.mapper.StatusMapper;
import br.com.developcorporation.collaborator.core.service.StatusService;
import br.com.developcorporation.collaborator.rest.message.response.StatusResponse;
import br.com.grupo.developer.corporation.lib.logger.logger.Logger;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@AllArgsConstructor
@RestController
public class StatusControllerImpl implements StatusController {

    private final StatusService service;

    @Override
    public ResponseEntity<List<StatusResponse>> getAll() {

        log.info(MessageConstant.REQUISICAO, Logger.info(null, MessageConstant.INICIALIZADO));

        List<StatusResponse> statusResponses = StatusMapper.INSTANCE.toResponseList(service.getByAll());

        log.info(MessageConstant.RESPOSTA, Logger.info(
                statusResponses,
                HttpStatus.OK.value(),
                MessageConstant.FINALIZADO));

        return new ResponseEntity<>(
                statusResponses,
                HttpStatus.OK);

    }
}
