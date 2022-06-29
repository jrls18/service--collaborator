package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.FollowUpController;
import br.com.developcorporation.collaborator.rest.logger.LogRest;
import br.com.developcorporation.collaborator.rest.mapper.FollowUpMapper;
import br.com.developcorporation.collaborator.core.service.FollowUpService;
import br.com.developcorporation.collaborator.rest.message.response.FollowUpResponse;
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
@RequestMapping(FieldConstant.ROUTER_FOLLOW_UP)
@CrossOrigin(origins = "*")
public class FollowUpControllerImpl implements FollowUpController {

    private static final Logger LOG = LoggerFactory.getLogger(FollowUpControllerImpl.class);

    private final FollowUpService service;

    private final LogRest logRest;

    @Override
    public ResponseEntity<List<FollowUpResponse>> getAll() {

        final String jsonRequest = logRest.jsonLogInfo(null, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        List<FollowUpResponse> followUpResponses = FollowUpMapper.INSTANCE.toResponseList(service.getByAll());

        final String jsonResponse = logRest.jsonLogInfo(followUpResponses, HttpStatus.OK.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(
                followUpResponses,
                HttpStatus.OK);
    }
}
