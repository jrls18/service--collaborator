package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.controller.CollaboratorController;
import br.com.developcorporation.collaborator.rest.message.request.CollaboratorRequest;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(FieldConstant.ROUTER_COLLABORATOR)
@CrossOrigin(origins = "*")
public class CollaboratorControllerImpl implements CollaboratorController {
    @Override
    public ResponseEntity<MessageResponse> add(CollaboratorRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<MessageResponse> update(CollaboratorRequest request) {
        return null;
    }

    /*
    private static final Logger LOG = LoggerFactory.getLogger(CompanyControllerImpl.class);

    private final CompanyValidator validator;
    private final CollaboratorService service;

    private final LogRest logRest;

    @Override
    public ResponseEntity<MessageResponse> add(CompanyRequest request) {

        final String jsonRequest =  logRest.jsonLogInfo(request, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        this.validator.addRequestValidation(request);

        Message message = service.add(CompanyMapper.INSTANCE.toDto(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        final String jsonResponse = logRest.jsonLogInfo(response, HttpStatus.CREATED.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MessageResponse> update(CompanyRequest request) {

        final String jsonRequest = logRest.jsonLogInfo(request, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        this.validator.updateRequestValidation(request);

        Message message = service.update(CompanyMapper.INSTANCE.toDto(request));

        MessageResponse response = MessageMapper.INSTANCE.dtoToResponse(message);

        final String jsonResponse = logRest.jsonLogInfo(response, HttpStatus.ACCEPTED.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(
                response,
                HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<CompanyResponse> getById(String id) {

        final String jsonRequest = logRest.jsonLogInfo(id, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        this.validator.pathVariableId(id);

        Company company = service.getById(Long.parseLong(id));

        CompanyResponse response =  CompanyMapper.INSTANCE.toResponse(company);

        final String jsonResponse = logRest.jsonLogInfo(response, HttpStatus.OK.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA, jsonResponse);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CompanyResponse> getByCnpj(String cnpj) {

        final String jsonRequest = logRest.jsonLogInfo(cnpj, MessageConstant.INICIALIZADO);

        LOG.info(MessageConstant.REQUISICAO, jsonRequest);

        this.validator.pathVariableCnpj(cnpj);

        Company company = service.getByCnpj(cnpj);

        CompanyResponse response =  CompanyMapper.INSTANCE.toResponse(company);

        final String jsonResponse =  logRest.jsonLogInfo(response, HttpStatus.OK.value(), MessageConstant.FINALIZADO);

        LOG.info(MessageConstant.RESPOSTA,jsonResponse);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

     */

}
