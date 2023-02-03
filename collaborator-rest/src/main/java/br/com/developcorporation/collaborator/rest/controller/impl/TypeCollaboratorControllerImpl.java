package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.core.service.TypeCollaboratorService;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.TypeCollaboratorController;
import br.com.developcorporation.collaborator.rest.mapper.TypeCollaboratorMapper;
import br.com.developcorporation.collaborator.rest.message.response.TypeCollaboratorResponse;
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
public class TypeCollaboratorControllerImpl implements TypeCollaboratorController {

    private final TypeCollaboratorService service;

    @Override
    public ResponseEntity<List<TypeCollaboratorResponse>> getAll() {

        log.info(MessageConstant.REQUISICAO, Logger.info(null, MessageConstant.INICIALIZADO));

        List<TypeCollaboratorResponse> typeCollaboratorResponseList = TypeCollaboratorMapper.INSTANCE.toResponseList(service.getByAll());

        log.info(MessageConstant.RESPOSTA, Logger.info(
                typeCollaboratorResponseList,
                HttpStatus.OK.value(),
                MessageConstant.FINALIZADO));

        return new ResponseEntity<>(
                typeCollaboratorResponseList,
                HttpStatus.OK);
    }
}
