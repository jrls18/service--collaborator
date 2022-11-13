package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.core.service.TypeCollaboratorService;
import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.TypeCollaboratorController;
import br.com.developcorporation.collaborator.domain.logger.*;
import br.com.developcorporation.collaborator.rest.mapper.TypeCollaboratorMapper;
import br.com.developcorporation.collaborator.rest.message.response.TypeCollaboratorResponse;
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
@RequestMapping(FieldConstant.TIPO_USUARIO)
@CrossOrigin(origins = "*")
public class TypeCollaboratorControllerImpl implements TypeCollaboratorController {

    private static final Logger LOG = LoggerFactory.getLogger(TypeCollaboratorControllerImpl.class);

    private final TypeCollaboratorService service;

    private final LogDomain logRest;

    @Override
    public ResponseEntity<List<TypeCollaboratorResponse>> getAll() {

        final String jsonRequest = logRest.jsonLogInfoParams(null, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        List<TypeCollaboratorResponse> statusResponses = TypeCollaboratorMapper.INSTANCE.toResponseList(service.getByAll());

        final String jsonResponse = logRest.jsonLogInfo(statusResponses, HttpStatus.OK.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(
                statusResponses,
                HttpStatus.OK);

    }
}
