package br.com.developcorporation.collaborator.core.service.impl;

import br.com.developcorporation.collaborator.core.enums.CoreEnum;
import br.com.developcorporation.collaborator.domain.constants.MessageConstants;
import br.com.developcorporation.collaborator.domain.infrastructure.ContextHolder;
import br.com.developcorporation.collaborator.core.service.AuthorizationService;
import br.com.developcorporation.collaborator.core.validation.AuthorizationValidation;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Authorization;
import br.com.developcorporation.collaborator.domain.model.Status;
import br.com.developcorporation.collaborator.domain.port.AuthorizationPort;
import br.com.developcorporation.collaborator.domain.port.StatusPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    public static final long ATIVO = 1L;
    private final AuthorizationPort port;
    private final StatusPort statusPort;
    private final AuthorizationValidation validator;

    @Override
    public Message add(Authorization dto) {

        validator.clientIdAndClientSecret();

        validator.validCorrelationId();

        validator.add(dto);

        exists(dto);

        Status status = new Status();
        status.setId(1L);
        dto.setStatus(status);
        dto.setClientId(UUID.randomUUID().toString());
        dto.setClientSecret(UUID.randomUUID().toString());
        dto.setDateRegister(LocalDateTime.now());

        try {
            port.add(dto);
        }catch (Exception ex){
           throw new DomainException(
                   CoreEnum.INTERNAL_SERVER_ERROR.getCode(),
                   MessageConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE,
                   null);
        }

        return new Message(CoreEnum.CREATED.getCode(),
                MessageConstants.AUTORIZACAO_CADASTRADA_COM_SUCESSO);
    }

    @Override
    public Message update(Authorization dto) {
        validator.clientIdAndClientSecret();

        validator.validCorrelationId();

        validator.update(dto);

        existsUpdate(dto);

        Authorization authorization = (Authorization) ContextHolder.get().getMap().get("autorizacao");

        try {

            dto.setClientSecret(authorization.getClientSecret());
            dto.setClientId(authorization.getClientId());
            dto.setDateRegister(authorization.getDateRegister());

            port.update(dto);
        }catch (Exception ex){
            throw new DomainException(
                    CoreEnum.INTERNAL_SERVER_ERROR.getCode(),
                    MessageConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE,
                    null);
        }

        return new Message(CoreEnum.ACCEPTED.getCode(),
                MessageConstants.AUTORIZACAO_ATUALIZADA_COM_SUCESSO);
    }

    @Override
    public List<Authorization> getAll() {
        return port.getAll();
    }

    @Override
    public void isAuthentication() {

        validator.clientIdAndClientSecret();

        validator.validCorrelationId();

        Optional<Authorization> authorizationOptional = port.getByClientIdAndClientSecret(ContextHolder.get().getClientId(), ContextHolder.get().getClientSecret());

        if(authorizationOptional.isEmpty())
            unauthorized();

        if(!authorizationOptional.get().getStatus().getId().equals(ATIVO))
            unauthorized();
    }

    private void unauthorized(){
        throw new DomainException(
                CoreEnum.UNAUTHORIZED.getCode(),
                MessageConstants.NAO_AUTORIZADO,
                null);
    }

    private void exists(final Authorization authorization) {
          List<Message.Details> details = new ArrayList<>();

        if(port.existsByName(authorization.getApplicationName()))
            details.add(
                    new Message.Details(
                            "application_name",
                            MessageConstants.APPLICATION_NAME_JA_EXISTE_CADASTRADA,
                            authorization.getApplicationName()));

        if(port.existsBySiglaApp(authorization.getSiglaApp()))
            details.add(
                    new Message.Details(
                            "sigla_app",
                            MessageConstants.SIGLA_APP_JA_EXISTE_CADASTRADA,
                            authorization.getSiglaApp()));

        if(!details.isEmpty())
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO,
                    details);
    }

    private void existsUpdate(final Authorization authorization) {
        List<Message.Details> details = new ArrayList<>();

        Authorization original = port.getById(authorization.getId());

        if(Objects.isNull(original) ){
            details.add(new Message.Details(
                    "codigo",
                    MessageConstants.AUTORIZACAO_NAO_EXISTE_CADASTRADA,
                    authorization.getId().toString()));
        }

        if(details.isEmpty()){

            ContextHolder.get().setMap("autorizacao", original);

            Authorization newApplication = port.getByApplicationName(authorization.getApplicationName());

            Authorization newApp = port.getBySiglaApp(authorization.getSiglaApp());

            Status status = statusPort.getById(authorization.getStatus().getId());

            if(Objects.isNull(status)) {
                details.add(
                        new Message.Details(
                                "codigo",
                                MessageConstants.CODIGO_DA_SITUACAO_NAO_EXISTE_CADASTRADO,
                                authorization.getStatus().getId().toString()));
            }

            if(Objects.nonNull(newApplication)){
                if(original.getId() != newApplication.getId())
                    details.add(
                            new Message.Details("application_name",
                                    MessageConstants.APPLICATION_NAME_JA_EXISTE_CADASTRADA,
                                    authorization.getApplicationName()));
            }

            if (Objects.nonNull(newApp)) {
                if(original.getId() != newApp.getId())
                    details.add(
                            new Message.Details("sigla_app",
                                    MessageConstants.SIGLA_APP_JA_EXISTE_CADASTRADA,
                                    authorization.getSiglaApp()));
            }
        }

        if(!details.isEmpty())
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO,
                    details);
    }
}