package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.SystemTypeController;
import br.com.developcorporation.collaborator.rest.logger.LogRest;
import br.com.developcorporation.collaborator.rest.mapper.SystemTypeMapper;
import br.com.developcorporation.collaborator.core.service.SystemTypeService;
import br.com.developcorporation.collaborator.rest.message.response.SystemTypeResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(FieldConstant.ROUTER_TYPE_SYSTEM)
@CrossOrigin(origins = "*")

public class SystemTypeControllerImpl implements SystemTypeController {

    private static final Logger LOG = LoggerFactory.getLogger(SystemTypeControllerImpl.class);

    private final SystemTypeService service;

    private final LogRest logRest;

    @Override
    public ResponseEntity<List<SystemTypeResponse>> getAll() {

        final String jsonRequest = logRest.jsonLogInfo(null, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        List<SystemTypeResponse> systemTypeResponses = SystemTypeMapper.INSTANCE.toResponseList(service.getByAll());

        final String jsonResponse = logRest.jsonLogInfo(systemTypeResponses, HttpStatus.OK.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(
                systemTypeResponses,
                HttpStatus.OK);
    }
}
