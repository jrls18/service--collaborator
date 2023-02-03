package br.com.developcorporation.collaborator.core.validation;

import br.com.developcorporation.collaborator.domain.constants.MessageConstants;
import br.com.developcorporation.collaborator.core.mapper.MessageMapper;
import br.com.developcorporation.collaborator.domain.model.Authorization;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import br.com.grupo.developer.corporation.libcommons.enums.CoreEnum;
import br.com.grupo.developer.corporation.libcommons.exception.DomainException;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.validation.BaseValidator;
import br.com.grupo.developer.corporation.libcommons.validation.Validation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuthorizationValidation implements BaseValidator<Authorization> {


    @Override
    public void add(Authorization authorization) {

        if(Objects.isNull(authorization))
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.PREENCHA_O_FORMULARIO_DE_CADASTRO_DA_NOVA_AUTORIZACAO,
                    null
            );

        List<Message.Details> detailsList = new ArrayList<>();

        if(authorization.getId() != null)
            detailsList.add(
                    new Message.Details(
                            "codigo",
                            MessageConstants.CODIGO_NAO_DEVE_SER_PREENCHIDO_NO_FORMULARIO_DE_CADASTRO,
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
                                "client_id",
                                MessageConstants.CLIENT_ID_NAO_DEVE_SER_PREENCHIDO_NO_FOMULARIO_DE_CADASTRO
                  )));

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNull(
                                authorization.getClientSecret(),
                                "client_secret",
                                MessageConstants.CLIENT_SECRET_NAO_DEVE_SER_PREENCHIDO_NO_FOMULARIO_DE_CADASTRO
                        )));

        detailsList.add(
            MessageMapper.INSTANCE.toDetailsDto(
                    Validation.mandatoryNull(
                            authorization.getStatus(),
                            "situação",
                            MessageConstants.OBJETO_SITUACAO_NAO_DEVER_SER_PREENCHIDO_POIS_O_CADASTRO_JA_FICA_ATIVO_NO_ATO_DO_CADASTRO
                    )
            )
        );

        throwDomainExceptionGeneric(
                CoreEnum.UNPROCESSABLE_ENTITY,
                MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO,
                detailsList);
    }

    private List<Message.Details> validNewApplicationName(final String applicationName){
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
          MessageMapper.INSTANCE.toDetailsDto(
                  Validation.mandatoryAlphanumeric(
                          applicationName,
                          "application_name",
                          MessageConstants.APPLICATION_NAME_DEVE_SER_UM_TEXTO_ALFANUMERICO
                  )
          )
        );

        detailsList.add(
          MessageMapper.INSTANCE.toDetailsDto(
                  Validation.stringMinAndMax(
                          applicationName,
                          "application_name",
                          5,
                          50,
                          MessageConstants.APPLICATION_NAME_DEVE_CONTER_NO_MINIMO_5_CARACTERES_E_NO_MAXIMO_50
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
                                "silga_app",
                                MessageConstants.SIGLA_APP_DEVE_SER_UM_TEXTO_ALFANUMERICO
                        )
                )
        );

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.stringMinAndMax(
                                siglaApp,
                                "silga_app",
                                5,
                                20,
                                MessageConstants.SIGLA_APP_DEVE_CONTER_NO_MINIMO_5_CARACTERES_E_NO_MAXIMO_20
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
                    MessageConstants.PREENCHA_O_FORMULARIO_DE_ALTERACAO_DA_AUTORIZACAO,
                    null
            );

        List<Message.Details> detailsList = new ArrayList<>();

       detailsList.addAll(
               validId(authorization.getId()));

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNull(
                                authorization.getClientId(),
                                "client_id",
                                MessageConstants.CLIENT_ID_NAO_DEVE_SER_PREENCHIDO_NO_FOMULARIO_DE_CADASTRO
                        )));

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNull(
                                authorization.getClientSecret(),
                                "client_secret",
                                MessageConstants.CLIENT_SECRET_NAO_DEVE_SER_PREENCHIDO_NO_FOMULARIO_DE_CADASTRO
                        )));

        detailsList.addAll(
                validNewApplicationName(
                        authorization.getApplicationName()));

        detailsList.addAll(
                validNewSiglaApp(
                        authorization.getSiglaApp()));


        if(Objects.isNull(authorization.getStatus())){
            detailsList.add(new Message.Details("siutacao",
                    MessageConstants.POR_FAVOR_INFORME_A_SITUACAO,
                    null));
        }else{
            if(authorization.getStatus().getId() <= 0){
                detailsList.add(new Message.Details("codigo",
                        MessageConstants.CODIGO_DA_SITUACAO_DEVE_SER_MAIOR_QUE_ZERO,
                        authorization.getStatus().getId().toString()));
            }
        }

        throwDomainExceptionGeneric(
                CoreEnum.UNPROCESSABLE_ENTITY,
                MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO,
                detailsList);
    }

    public void clientIdAndClientSecret() {
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.notNullOrEmpty(
                                "client_Id",
                                ContextHolder.get().getClientId(),
                                MessageConstants.INFORME_A_CHAVE_DE_ACESSO_CLIENT_ID)));

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.notNullOrEmpty(
                                "client_secret",
                                ContextHolder.get().getClientSecret(),
                                MessageConstants.INFORME_A_CHAVE_DE_ACESSO_CLIENT_SECRET)));

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.validUUID(
                                ContextHolder.get().getClientId(),
                                "client_Id",
                                MessageConstants.CHAVE_DE_ACESSO_CLIENT_ID_ESTA_NO_FORMATO_INVALIDO)));

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(Validation.validUUID(
                        ContextHolder.get().getClientSecret(),
                        "client_secret",
                        MessageConstants.CHAVE_DE_ACESSO_CLIENT_SECRET_ESTA_NO_FORMATO_INVALIDO)));

        throwDomainExceptionGeneric(
                CoreEnum.UNPROCESSABLE_ENTITY,
                MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO,
                detailsList);

    }

    public void validCorrelationId(){
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.notNullOrEmpty(
                                "correlationId",
                                ContextHolder.get().getCorrelationId(),
                                MessageConstants.INFORME_A_CHAVE_DE_RASTREIO_DE_UM_MICROSERVICE)));


        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.validUUID(
                                ContextHolder.get().getCorrelationId(),
                                "correlationId",
                                MessageConstants.INFORME_A_CHAVE_DE_RASTREIO_DE_UM_MICROSERVICE_NO_FORMATO_DE_UUID)));

        throwDomainExceptionGeneric(
                CoreEnum.UNPROCESSABLE_ENTITY,
                MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO,
                detailsList);
    }



    public void validCredentials(){
        this.clientIdAndClientSecret();
        this.clientIdAndClientSecret();
    }


    private  List<Message.Details> validId(Long id){

        List<Message.Details> detailsList = new ArrayList<>();

        if(Objects.isNull(id))
            detailsList.add(
                    new Message.Details(
                            "codigo",
                            MessageConstants.CODIGO_AUTORIZACAO_DEVE_SER_INFORMADO,
                            null));
        else if(id.intValue() <= 0)
            detailsList.add(
                    new Message.Details(
                            "codigo",
                            MessageConstants.CODIGO_AUTORIZACAO_DEVE_SER_MAIOR_QUE_ZERO,
                            id.toString()));

        return detailsList;
    }
}
