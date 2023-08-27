package br.com.group.developer.corporation.service.collaborator.api.rest.validation;


import br.com.group.developer.corporation.service.collaborator.api.rest.mapper.MessageMapper;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.request.AuthorizationRequest;
import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.constants.MessageDomainConstants;
import br.com.grupo.developer.corporation.libcommons.constants.FieldAssistantConstants;
import br.com.grupo.developer.corporation.libcommons.exception.BadRequestEntityException;
import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import br.com.grupo.developer.corporation.libcommons.validation.Validation;
import br.com.grupo.developer.corporation.libcommons.validation.response.BaseValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AuthorizationValidator implements BaseValidator<AuthorizationRequest> {

    public void validClientIdAndClientSecret(final String clientId, final String clientSecret) {
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
          MessageMapper.INSTANCE.toDetailsResponse(
              Validation.notNullOrEmpty(
                      FieldAssistantConstants.CLIENT_ID,
                      clientId,
                      MessageDomainConstants.INFORME_A_CHAVE_DE_ACESSO_CLIENT_ID)
          )
        );

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldAssistantConstants.CLIENT_SECRET,
                                clientSecret,
                                MessageDomainConstants.INFORME_A_CHAVE_DE_ACESSO_CLIENT_SECRET)
                )
        );

        throwBadRequestGeneric(
                detailsResponses,
                MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO);
    }

    public void validCorrelationId(final String correlationId){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
             MessageMapper.INSTANCE.toDetailsResponse(
                     Validation.notNullOrEmpty(
                             FieldAssistantConstants.CURRENTCORRELATION_ID,
                             correlationId,
                             MessageDomainConstants.INFORME_A_CHAVE_DE_RASTREIO_DE_UM_MICROSERVICE
                     )
             )
        );

        throwBadRequestGeneric(
                detailsResponses,
                MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO);
    }

    @Override
    public void addRequestValidation(AuthorizationRequest request) {
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        if(Objects.isNull(request))
            throw new BadRequestEntityException(
                    MessageDomainConstants.PARA_REALIZAR_O_CADASTRO_DA_AUTORIZACAO_DEVE_SE_PREENCHER_O_FORMULARIO_E_ENVIAR_UMA_NOVA_REQUISICAO,
                    null
            );

        detailsResponses.add(
           MessageMapper.INSTANCE.toDetailsResponse(
               Validation.mandatoryNull(
                       request.getId(),
                       FieldDomainConstants.CODIGO,
                       MessageDomainConstants.INFORME_O_CODIGO_DA_AUTORIZACAO)
           ));

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldDomainConstants.APPLICATION_NAME,
                                request.getApplicationName(),
                                MessageDomainConstants.INFORME_O_NOME_DA_APLICACAO)));

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldDomainConstants.SIGLA_APP,
                                request.getSiglaApp(),
                                MessageDomainConstants.INFOMRE_O_NOME_DA_SIGLA_APP)));

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNull(
                                request.getStatus(),
                                FieldDomainConstants.SITUACAO,
                                MessageDomainConstants.SITUACAO_NAO_DEVE_SER_ENVIADO_NO_FORMULARIO)
                ));

        throwBadRequestGeneric(
                detailsResponses,
                MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO);
    }

    @Override
    public void updateRequestValidation(AuthorizationRequest request) {
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        if(Objects.isNull(request))
            throw new BadRequestEntityException(
                    MessageDomainConstants.PARA_ATUALIZAR_O_REGISTRO_DE_UMA_AUTORIZACAO_DEVE_SE_PREENCHER_O_FORMULARIO_E_ENVIAR_UMA_NOVA_REQUISICAO,
                    null
            );

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNumber8(
                                request.getId(),
                                FieldDomainConstants.CODIGO,
                                MessageDomainConstants.CODIGO_DEVE_CONTER_NO_MINIMO_1_CARACTERE_E_NO_MAXIMO_8)));


        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldDomainConstants.APPLICATION_NAME,
                                request.getApplicationName(),
                                MessageDomainConstants.INFORME_O_NOME_DA_APLICACAO)));

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldDomainConstants.SIGLA_APP,
                                request.getSiglaApp(),
                                MessageDomainConstants.INFOMRE_O_NOME_DA_SIGLA_APP)));


        if(Objects.isNull(request.getStatus()))
            detailsResponses.add(new MessageResponse.DetailsResponse(
                    FieldDomainConstants.SITUACAO,
                    MessageDomainConstants.SITUACAO_DEVE_SER_EVIADO,
                    null));
        else{

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.mandatoryNumber8(
                                    request.getStatus().getId(),
                                    FieldDomainConstants.CODIGO,
                                    MessageDomainConstants.CODIGO_SITUACAO_DEVE_CONTER_NO_MINIMO_1_CARACTERER_E_NO_MAXIMO_8)));
        }

        throwBadRequestGeneric(
                detailsResponses,
                MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO);
    }
}
