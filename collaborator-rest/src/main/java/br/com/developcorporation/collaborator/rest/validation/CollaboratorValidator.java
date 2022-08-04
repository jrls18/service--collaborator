package br.com.developcorporation.collaborator.rest.validation;


import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.exception.error.BadRequestEntityException;
import br.com.developcorporation.collaborator.rest.mapper.MessageMapper;
import br.com.developcorporation.collaborator.rest.message.request.CollaboratorRequest;
import br.com.developcorporation.collaborator.rest.message.request.LoginRequest;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import br.com.developcorporation.lib.commons.validation.Validation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CollaboratorValidator implements BaseValidator<CollaboratorRequest>{
    @Override
    public void addRequestValidation(CollaboratorRequest value) {
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNull(value.getId(),
                                FieldConstant.CODIGO,
                                MessageConstant.CODIGO_NAO_DEVE_SER_PREENCHIDO)
                )
        );

        detailsResponses.addAll(validCnpj(value));

        detailsResponses.addAll(validTypeUser(value));

        detailsResponses.addAll(validFoundationDate(value));

        detailsResponses.addAll(validPassword(value));

        detailsResponses.addAll(validContact(value));

        detailsResponses.addAll(validAddress(value));

        detailsResponses.addAll(validIdCompany(value));

        throwBadRequestGeneric(
                detailsResponses,
                MessageConstant.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR);
    }


    public void loginRequestValidator(final LoginRequest request) {

        if(Objects.isNull(request))
            throw new BadRequestEntityException(MessageConstant.INFORME_USUARIO_E_SENHA, null);

        List<MessageResponse.DetailsResponse> detailsResponseList = new ArrayList<>();

        detailsResponseList.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldConstant.USERNAME,request.getUsername(),
                                MessageConstant.INFORME_O_USUARIO_DE_ACESSO))
        );

        detailsResponseList.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.stringMinAndMax(
                                request.getUsername(),
                                FieldConstant.USERNAME,
                                11,
                                150,
                                MessageConstant.USUARIO_DEVE_SER_INFORMADO_UM_NUMERO_DE_TELEFONE_OU_UM_EMAIL
                        ) )
        );

        detailsResponseList.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldConstant.PASSWORD,
                                request.getPassword(),
                                MessageConstant.INFORME_A_SENHA_DE_ACESSO))
        );


        detailsResponseList.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.stringMinAndMax(
                                request.getPassword(),
                                FieldConstant.PASSWORD,
                                5,
                                150,
                                MessageConstant.SENHA_DEVE_CONTER_NO_MINIMO_5_CARACTERES_E_NO_MAXIMO_150
                        ) )
        );

        throwBadRequestGeneric(
                detailsResponseList,
                MessageConstant.EXISTE_ERROS_NOS_CAMPOS_DO_USUARIO);
    }


    @Override
    public void updateRequestValidation(CollaboratorRequest value) {
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNumber8(
                                value.getId(),
                                FieldConstant.CODIGO,
                                MessageConstant.CODIGO_E_OBRIGATORIO
                        )
                )
        );

        detailsResponses.addAll(validCnpj(value));

        detailsResponses.addAll(validTypeUser(value));

        detailsResponses.addAll(validFoundationDate(value));

        detailsResponses.addAll(validPassword(value));

        detailsResponses.addAll(validContact(value));

        detailsResponses.addAll(validAddress(value));

        detailsResponses.addAll(validIdCompany(value));

        throwBadRequestGeneric(
                detailsResponses,
                MessageConstant.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR);
    }

    public void pathVariableId(final String id){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNumber8(id,
                                FieldConstant.CODIGO,
                                MessageConstant.CODIGO_DEVE_CONTER_NO_MINIMO_1_CARACTERE_E_NO_MAXIMO_8)
                )
        );

        throwBadRequestGeneric(
                detailsResponses,
                MessageConstant.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR);
    }

    public void pathVariableCnpj(final String cnpj){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.stringMinAndMax(cnpj,
                                FieldConstant.CNPJ,
                                11,
                                14,
                                MessageConstant.CNPJ_DEVE_CONTER_NO_MINIMO_11_CARACTERES_E_NO_MAXIMO_14_NA_AUSENCIA_DO_CNPJ_PODE_INFORMAR_O_CPF
                        )
                )
        );

        throwBadRequestGeneric(
                detailsResponses,
                MessageConstant.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR);
    }


    private List<MessageResponse.DetailsResponse> validIdCompany(final CollaboratorRequest request){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNumber8(
                                request.getIdCompany(),
                                FieldConstant.ID_COMPANY,
                                MessageConstant.CODIGO_DA_EMPRESA_E_OBRIGATORIO
                        )
                )
        );

        return detailsResponses;
    }

    private List<MessageResponse.DetailsResponse> validTypeUser(final CollaboratorRequest request){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        /*
        if(Objects.isNull(request.getTypeUser())){
            detailsResponses.add(
                    new MessageResponse.DetailsResponse(
                            FieldConstant.TIPO_SISTEMA,
                            MessageConstant.TIPO_SISTEMA_E_OBRIGATORIO,
                            null)
            );
        }else{
            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.mandatoryNumber8(
                                    request.getTypeUser(),
                                    FieldConstant.CODIGO,
                                    MessageConstant.CODIGO_DO_TIPO_DE_SYSTEMA_E_OBRIGATORIO
                            )
                    )
            );
        }

         */

        return detailsResponses;
    }

    private List<MessageResponse.DetailsResponse> validContact(final CollaboratorRequest request){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        if(Objects.isNull(request.getContact())){
            detailsResponses.add(
                    new MessageResponse.DetailsResponse(
                            FieldConstant.CONTATO,
                            MessageConstant.DADOS_DE_CONTATO_E_OBRIGATORIO,
                            null
                    )
            );
        }else {

            detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNumberPhone(
                                request.getContact().getMainPhone(),
                                FieldConstant.TELEFONE_PRINCIPAL,
                                MessageConstant.TELEFONE_PRINCIPAL_E_OBRIGATORIO
                        )
                )
            );

            if(!StringUtils.isEmpty(request.getContact().getTelephone())){
                detailsResponses.add(
                        MessageMapper.INSTANCE.toDetailsResponse(
                                Validation.mandatoryNumberPhone(
                                        request.getContact().getTelephone(),
                                        FieldConstant.TELEFONE,
                                        MessageConstant.TELEFONE_INVALIDO
                                )
                        )
                );
            }

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.mandatoryEmail(
                                    request.getContact().getEmail(),
                                    FieldConstant.EMAIL,
                                    MessageConstant.EMAIL_E_OBRIGATORIO_OU_ESTA_INVALIDO
                            )
                    )
            );
        }




        return detailsResponses;
    }

    private List<MessageResponse.DetailsResponse> validCnpj(final CollaboratorRequest request){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryCNPJorCPF(
                                request.getCpfCnpj(),
                                FieldConstant.CNPJ,
                                MessageConstant.CNPJ_CPF_E_OBRIGATORIO,
                                MessageConstant.CNPJ_E_INVALIDO,
                                MessageConstant.CPF_E_INVALIDO,
                                MessageConstant.CNPJ_CPF_E_OBRIGATORIO
                        )
                )
        );

        return detailsResponses;
    }

    private List<MessageResponse.DetailsResponse> validAddress(final CollaboratorRequest request){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        if(Objects.isNull(request.getAddress())){
            detailsResponses.add(
                    new MessageResponse.DetailsResponse(
                            FieldConstant.ENDERECO,
                            MessageConstant.ENDERECO_E_OBRIGATORIO,
                            null
                    )
            );
        }else{

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.notNullOrEmpty(
                                    FieldConstant.CEP,
                                    request.getAddress().getZipCode(),
                                    MessageConstant.CEP_E_OBRIGATORIO
                            )
                    )
            );

            if(detailsResponses.isEmpty()){
                detailsResponses.add(
                     MessageMapper.INSTANCE.toDetailsResponse(
                             Validation.mandatoryNumber8(
                                     request.getAddress().getZipCode(),
                                     FieldConstant.CEP,
                                     MessageConstant.CEP_INVALIDO
                             )
                     )
                );
            }

            detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldConstant.LOGRADOURO,
                                request.getAddress().getNeighborhood(),
                                MessageConstant.LOGRADOURO_E_OBRIGATORIO
                        )
                )
            );

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.notNullOrEmpty(
                                    FieldConstant.BAIRRO,
                                    request.getAddress().getNeighborhood(),
                                    MessageConstant.BAIRRO_E_OBRIGATORIO
                            )
                    )
            );

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.notNullOrEmpty(
                                    FieldConstant.LOCALIDADE,
                                    request.getAddress().getLocation(),
                                    MessageConstant.LOCALIDADE_E_OBRIGATORIO
                            )
                    )
            );

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.notNullOrEmpty(
                                    FieldConstant.UF,
                                    request.getAddress().getState(),
                                    MessageConstant.UF_E_OBRIGATORIO
                            )
                    )
            );

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.mandatoryNumber8(
                                    request.getAddress().getNumber(),
                                    FieldConstant.NUMERO,
                                    MessageConstant.NUMERO_E_OBRIGATORIO_OU_INVALIDO
                            )
                    )
            );

        }

        return detailsResponses;
    }

    private List<MessageResponse.DetailsResponse> validPassword(final CollaboratorRequest request){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
          MessageMapper.INSTANCE.toDetailsResponse(
                  Validation.notNullOrEmpty(
                          FieldConstant.PASSWORD,
                          request.getPassword(),
                          MessageConstant.PASSWORD_E_OBRIGATORIO
                  )
          )
        );

        return detailsResponses;
    }

    private List<MessageResponse.DetailsResponse> validFoundationDate(final CollaboratorRequest request){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldConstant.DATA_NASCIMENTO,
                                request.getBirthDate(),
                                MessageConstant.DATA_NASCIMENTO_E_OBRIGATORIO
                        )
                )
        );

        if(detailsResponses.isEmpty()){
            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.mandatoryDate(
                                    request.getBirthDate(),
                                    FieldConstant.DATA_NASCIMENTO,
                                    MessageConstant.DATA_NASCIMENTO_DEVE_ESTA_NO_FORMATO_ANO_MES_DIA_OU_ANO_MES_DIA_HORAS_MINUTOS_E_SEGUNDO
                            )
                    )
            );
        }

        return detailsResponses;
    }
}
