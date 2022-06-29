package br.com.developcorporation.collaborator.rest.controller.impl;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.controller.CompanyController;
import br.com.developcorporation.collaborator.rest.logger.LogRest;
import br.com.developcorporation.collaborator.rest.mapper.CompanyMapper;
import br.com.developcorporation.collaborator.rest.mapper.MessageMapper;
import br.com.developcorporation.collaborator.rest.validation.CompanyValidator;
import br.com.developcorporation.collaborator.core.service.CompanyService;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Company;
import br.com.developcorporation.collaborator.rest.message.request.CompanyRequest;
import br.com.developcorporation.collaborator.rest.message.response.CompanyResponse;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(FieldConstant.ROUTER_COMPANY)
@CrossOrigin(origins = "*")
public class CompanyControllerImpl  implements CompanyController {

    private static final Logger LOG = LoggerFactory.getLogger(CompanyControllerImpl.class);

    private final CompanyValidator validator;
    private final CompanyService service;

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

}
