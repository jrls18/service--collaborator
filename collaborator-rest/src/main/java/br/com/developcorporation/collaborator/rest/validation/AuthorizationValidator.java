package br.com.developcorporation.collaborator.rest.validation;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.exception.error.BadRequestEntityException;
import br.com.developcorporation.collaborator.rest.mapper.MessageMapper;
import br.com.developcorporation.collaborator.rest.message.request.AuthorizationRequest;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import br.com.grupo.developer.corporation.libcommons.validation.Validation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AuthorizationValidator implements BaseValidator<AuthorizationRequest>{

    public void validClientIdAndClientSecret(final String clientId, final String clientSecret) {
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
          MessageMapper.INSTANCE.toDetailsResponse(
              Validation.notNullOrEmpty(
                      FieldConstant.CLIENT_ID,
                      clientId,
                      MessageConstant.INFORME_A_CHAVE_DE_ACESSO_CLIENT_ID)
          )
        );

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldConstant.CLIENT_SECRET,
                                clientSecret,
                                MessageConstant.INFORME_A_CHAVE_DE_ACESSO_CLIENT_SECRET)
                )
        );

        throwBadRequestGeneric(
                detailsResponses,
                MessageConstant.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO);
    }

    public void validCorrelationId(final String correlationId){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
             MessageMapper.INSTANCE.toDetailsResponse(
                     Validation.notNullOrEmpty(
                             FieldConstant.CURRENTCORRELATION_ID,
                             correlationId,
                             MessageConstant.INFORME_A_CHAVE_DE_RASTREIO_DE_UM_MICROSERVICE
                     )
             )
        );

        throwBadRequestGeneric(
                detailsResponses,
                MessageConstant.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO);
    }

    @Override
    public void addRequestValidation(AuthorizationRequest request) {
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        if(Objects.isNull(request))
            throw new BadRequestEntityException(
                    MessageConstant.PARA_REALIZAR_O_CADASTRO_DA_AUTORIZACAO_DEVE_SE_PREENCHER_O_FORMULARIO_E_ENVIAR_UMA_NOVA_REQUISICAO,
                    null
            );

        detailsResponses.add(
           MessageMapper.INSTANCE.toDetailsResponse(
               Validation.mandatoryNull(
                       request.getId(),
                       FieldConstant.CODIGO,
                       MessageConstant.INFORME_O_CODIGO_DA_AUTORIZACAO)
           ));

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldConstant.APPLICATION_NAME,
                                request.getApplicationName(),
                                MessageConstant.INFORME_O_NOME_DA_APLICACAO)));

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldConstant.SIGLA_APP,
                                request.getSiglaApp(),
                                MessageConstant.INFOMRE_O_NOME_DA_SIGLA_APP)));

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNull(
                                request.getStatus(),
                                FieldConstant.ROUTER_STATUS,
                                MessageConstant.SITUACAO_NAO_DEVE_SER_ENVIADO_NO_FORMULARIO)
                ));

        throwBadRequestGeneric(
                detailsResponses,
                MessageConstant.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO);
    }

    @Override
    public void updateRequestValidation(AuthorizationRequest request) {
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        if(Objects.isNull(request))
            throw new BadRequestEntityException(
                    MessageConstant.PARA_ATUALIZAR_O_REGISTRO_DE_UMA_AUTORIZACAO_DEVE_SE_PREENCHER_O_FORMULARIO_E_ENVIAR_UMA_NOVA_REQUISICAO,
                    null
            );

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNumber8(
                                request.getId(),
                                FieldConstant.CODIGO,
                                MessageConstant.CODIGO_DEVE_CONTER_NO_MINIMO_1_CARACTERE_E_NO_MAXIMO_8)));


        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldConstant.APPLICATION_NAME,
                                request.getApplicationName(),
                                MessageConstant.INFORME_O_NOME_DA_APLICACAO)));

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldConstant.SIGLA_APP,
                                request.getSiglaApp(),
                                MessageConstant.INFOMRE_O_NOME_DA_SIGLA_APP)));


        if(Objects.isNull(request.getStatus()))
            detailsResponses.add(new MessageResponse.DetailsResponse(
                    FieldConstant.ROUTER_STATUS,
                    MessageConstant.SITUACAO_DEVE_SER_EVIADO,
                    null));
        else{

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.mandatoryNumber8(
                                    request.getStatus().getId(),
                                    FieldConstant.CODIGO,
                                    MessageConstant.CODIGO_SITUACAO_DEVE_CONTER_NO_MINIMO_1_CARACTERER_E_NO_MAXIMO_8)));
        }

        throwBadRequestGeneric(
                detailsResponses,
                MessageConstant.EXISTE_ERROS_NOS_CAMPOS_DE_AUTORIZACAO);
    }
}
