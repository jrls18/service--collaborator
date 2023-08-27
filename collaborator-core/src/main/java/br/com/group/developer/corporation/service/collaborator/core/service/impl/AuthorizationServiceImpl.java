package br.com.group.developer.corporation.service.collaborator.core.service.impl;

import br.com.group.developer.corporation.service.collaborator.core.validation.AuthorizationValidation;
import br.com.group.developer.corporation.service.collaborator.core.service.AuthorizationService;
import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.constants.MessageDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.model.Authorization;
import br.com.group.developer.corporation.service.collaborator.domain.model.Status;
import br.com.group.developer.corporation.service.collaborator.domain.port.AuthorizationPort;
import br.com.group.developer.corporation.service.collaborator.domain.port.StatusPort;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import br.com.grupo.developer.corporation.libcommons.enums.CoreEnum;
import br.com.grupo.developer.corporation.libcommons.exception.DomainException;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    public static final long ACTIVE = 1L;
    private final AuthorizationPort port;
    private final StatusPort statusPort;
    private final AuthorizationValidation validator;

    @Override
    public Message add(Authorization dto) {
        
        validator.add(dto);

        exists(dto);

        Status status = new Status();
        status.setId(ACTIVE);
        dto.setStatus(status);
        dto.setClientId(UUID.randomUUID().toString());
        dto.setClientSecret(UUID.randomUUID().toString());
        dto.setDateRegister(LocalDateTime.now());

        try {
            port.save(dto);
        }catch (Exception ex){
           throw new DomainException(
                   CoreEnum.INTERNAL_SERVER_ERROR.getCode(),
                   MessageDomainConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE,
                   null);
        }

        return new Message(CoreEnum.CREATED.getCode(),
                MessageDomainConstants.AUTORIZACAO_CADASTRADA_COM_SUCESSO);
    }

    @Override
    public Message update(Authorization dto) {

        if(Objects.isNull(dto))
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.PREENCHA_O_FORMULARIO_DE_CADASTRO_DA_NOVA_AUTORIZACAO,
                    null
            );

        validator.update(dto);

        existsUpdate(dto);

        Authorization authorization = (Authorization) ContextHolder.get().getMap().get(FieldDomainConstants.AUTORIZACAO);

        try {

            dto.setClientSecret(authorization.getClientSecret());
            dto.setClientId(authorization.getClientId());
            dto.setDateRegister(authorization.getDateRegister());

            port.save(dto);
        }catch (Exception ex){
            throw new DomainException(
                    CoreEnum.INTERNAL_SERVER_ERROR.getCode(),
                    MessageDomainConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE,
                    null);
        }

        return new Message(CoreEnum.ACCEPTED.getCode(),
                MessageDomainConstants.AUTORIZACAO_ATUALIZADA_COM_SUCESSO);
    }

    @Override
    public List<Authorization> getAll() {
        return port.getAll();
    }

    @Override
    public void isAuthentication() {

        Optional<Authorization> authorizationOptional = port.getByClientIdAndClientSecret(ContextHolder.get().getClientId(), ContextHolder.get().getClientSecret());

        if(authorizationOptional.isEmpty())
            unauthorized();

        if(!authorizationOptional.get().getStatus().getId().equals(ACTIVE))
            unauthorized();
    }

    private void unauthorized(){
        throw new DomainException(
                CoreEnum.UNAUTHORIZED.getCode(),
                MessageDomainConstants.NAO_AUTORIZADO,
                null);
    }

    private void exists(final Authorization authorization) {
          List<Message.Details> details = new ArrayList<>();

        if(port.existsByName(authorization.getApplicationName()))
            details.add(
                    new Message.Details(
                            FieldDomainConstants.APPLICATION_NAME,
                            MessageDomainConstants.APPLICATION_NAME_JA_EXISTE_CADASTRADA,
                            authorization.getApplicationName()));

        if(port.existsBySiglaApp(authorization.getSiglaApp()))
            details.add(
                    new Message.Details(
                            FieldDomainConstants.SIGLA_APP,
                            MessageDomainConstants.SIGLA_APP_JA_EXISTE_CADASTRADA,
                            authorization.getSiglaApp()));

        if(!details.isEmpty())
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO,
                    details);
    }

    private void existsUpdate(final Authorization authorization) {
        List<Message.Details> details = new ArrayList<>();

        Authorization original = port.getById(authorization.getId());

        if(Objects.isNull(original) || Objects.isNull(original.getId()) ){
            details.add(new Message.Details(
                    FieldDomainConstants.CODIGO,
                    MessageDomainConstants.AUTORIZACAO_NAO_EXISTE_CADASTRADA,
                    authorization.getId().toString()));
        } else {

            ContextHolder.get().setMap(FieldDomainConstants.AUTORIZACAO, original);

            Authorization newApplication = port.getByApplicationName(authorization.getApplicationName());

            Authorization newApp = port.getBySiglaApp(authorization.getSiglaApp());

            Status status = statusPort.getById(authorization.getStatus().getId());

            if(Objects.isNull(status)) {
                details.add(
                        new Message.Details(
                                FieldDomainConstants.CODIGO,
                                MessageDomainConstants.CODIGO_DA_SITUACAO_NAO_EXISTE_CADASTRADO,
                                authorization.getStatus().getId().toString()));
            }

            if(Objects.nonNull(newApplication)
                    && Boolean.FALSE.equals(Objects.equals(original.getId(), newApplication.getId()))){
                details.add(
                        new Message.Details(FieldDomainConstants.APPLICATION_NAME,
                                MessageDomainConstants.APPLICATION_NAME_JA_EXISTE_CADASTRADA,
                                authorization.getApplicationName()));
            }

            if (Objects.nonNull(newApp) &&
                    Boolean.FALSE.equals(Objects.equals(original.getId(), newApp.getId()))) {
                details.add(
                        new Message.Details(FieldDomainConstants.SIGLA_APP,
                                MessageDomainConstants.SIGLA_APP_JA_EXISTE_CADASTRADA,
                                authorization.getSiglaApp()));
            }
        }

        if(!details.isEmpty())
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO,
                    details);
    }
}