package br.com.group.developer.corporation.service.collaborator.core.validation;


import br.com.group.developer.corporation.service.collaborator.core.mapper.MessageMapper;
import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.constants.MessageDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.model.Authorization;
import br.com.grupo.developer.corporation.libcommons.constants.FieldAssistantConstants;
import br.com.grupo.developer.corporation.libcommons.enums.CoreEnum;
import br.com.grupo.developer.corporation.libcommons.exception.DomainException;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.validation.BaseValidator;
import br.com.grupo.developer.corporation.libcommons.validation.Validation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AuthorizationValidation implements BaseValidator<Authorization> {


    @Override
    public void add(Authorization authorization) {

        if(Objects.isNull(authorization))
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.PREENCHA_O_FORMULARIO_DE_CADASTRO_DA_NOVA_AUTORIZACAO,
                    null
            );

        List<Message.Details> detailsList = new ArrayList<>();

        if(authorization.getId() != null)
            detailsList.add(
                    new Message.Details(
                            FieldDomainConstants.CODIGO,
                            MessageDomainConstants.CODIGO_NAO_DEVE_SER_PREENCHIDO_NO_FORMULARIO_DE_CADASTRO,
                            authorization.getId().toString()));

        detailsList.addAll(
                validNewApplicationName(
                        authorization.getApplicationName()));

        detailsList.addAll(
                validNewSiglaApp(
                      authorization.getSiglaApp()));

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNull(
                                authorization.getClientId(),
                                FieldAssistantConstants.CLIENT_ID,
                                MessageDomainConstants.CLIENT_ID_NAO_DEVE_SER_PREENCHIDO_NO_FOMULARIO_DE_CADASTRO
                  )));

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNull(
                                authorization.getClientSecret(),
                                FieldAssistantConstants.CLIENT_SECRET,
                                MessageDomainConstants.CLIENT_SECRET_NAO_DEVE_SER_PREENCHIDO_NO_FOMULARIO_DE_CADASTRO
                        )));

        detailsList.add(
            MessageMapper.INSTANCE.toDetailsDto(
                    Validation.mandatoryNull(
                            authorization.getStatus(),
                            FieldDomainConstants.SITUACAO,
                            MessageDomainConstants.OBJETO_SITUACAO_NAO_DEVER_SER_PREENCHIDO_POIS_O_CADASTRO_JA_FICA_ATIVO_NO_ATO_DO_CADASTRO
                    )
            )
        );

        throwDomainExceptionGeneric(
                CoreEnum.UNPROCESSABLE_ENTITY,
                MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO,
                detailsList);
    }

    private List<Message.Details> validNewApplicationName(final String applicationName){
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
          MessageMapper.INSTANCE.toDetailsDto(
                  Validation.mandatoryAlphanumeric(
                          applicationName,
                          FieldDomainConstants.APPLICATION_NAME,
                          MessageDomainConstants.APPLICATION_NAME_DEVE_SER_UM_TEXTO_ALFANUMERICO
                  )
          )
        );

        detailsList.add(
          MessageMapper.INSTANCE.toDetailsDto(
                  Validation.stringMinAndMax(
                          applicationName,
                          FieldDomainConstants.APPLICATION_NAME,
                          5,
                          50,
                          MessageDomainConstants.APPLICATION_NAME_DEVE_CONTER_NO_MINIMO_5_CARACTERES_E_NO_MAXIMO_50
                  )
          )
        );

        return detailsList;
    }

    private List<Message.Details> validNewSiglaApp(final String siglaApp){
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryAlphanumeric(
                                siglaApp,
                                FieldDomainConstants.SIGLA_APP,
                                MessageDomainConstants.SIGLA_APP_DEVE_SER_UM_TEXTO_ALFANUMERICO
                        )
                )
        );

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.stringMinAndMax(
                                siglaApp,
                                FieldDomainConstants.SIGLA_APP,
                                5,
                                20,
                                MessageDomainConstants.SIGLA_APP_DEVE_CONTER_NO_MINIMO_5_CARACTERES_E_NO_MAXIMO_20
                        )
                )
        );

        return detailsList;
    }

    @Override
    public void update(Authorization authorization) {

        if(Objects.isNull(authorization))
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.PREENCHA_O_FORMULARIO_DE_ALTERACAO_DA_AUTORIZACAO,
                    null
            );

        List<Message.Details> detailsList = new ArrayList<>(validId(authorization.getId()));

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNull(
                                authorization.getClientId(),
                                FieldAssistantConstants.CLIENT_ID,
                                MessageDomainConstants.CLIENT_ID_NAO_DEVE_SER_PREENCHIDO_NO_FOMULARIO_DE_CADASTRO
                        )));

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNull(
                                authorization.getClientSecret(),
                                FieldAssistantConstants.CLIENT_SECRET,
                                MessageDomainConstants.CLIENT_SECRET_NAO_DEVE_SER_PREENCHIDO_NO_FOMULARIO_DE_CADASTRO
                        )));

        detailsList.addAll(
                validNewApplicationName(
                        authorization.getApplicationName()));

        detailsList.addAll(
                validNewSiglaApp(
                        authorization.getSiglaApp()));


        if(Objects.isNull(authorization.getStatus())){
            detailsList.add(new Message.Details(FieldDomainConstants.SITUACAO,
                    MessageDomainConstants.POR_FAVOR_INFORME_A_SITUACAO,
                    null));
        }else{
            if(authorization.getStatus().getId() <= 0){
                detailsList.add(new Message.Details(FieldDomainConstants.CODIGO,
                        MessageDomainConstants.CODIGO_DA_SITUACAO_DEVE_SER_MAIOR_QUE_ZERO,
                        authorization.getStatus().getId().toString()));
            }
        }

        throwDomainExceptionGeneric(
                CoreEnum.UNPROCESSABLE_ENTITY,
                MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO,
                detailsList);
    }

    private  List<Message.Details> validId(Long id){

        List<Message.Details> detailsList = new ArrayList<>();

        if(Objects.isNull(id))
            detailsList.add(
                    new Message.Details(
                            FieldDomainConstants.CODIGO,
                            MessageDomainConstants.CODIGO_AUTORIZACAO_DEVE_SER_INFORMADO,
                            null));
        else if(id.intValue() <= 0)
            detailsList.add(
                    new Message.Details(
                            FieldDomainConstants.CODIGO,
                            MessageDomainConstants.CODIGO_AUTORIZACAO_DEVE_SER_MAIOR_QUE_ZERO,
                            id.toString()));

        return detailsList;
    }
}
