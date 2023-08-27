package br.com.group.developer.corporation.service.collaborator.api.rest.validation;

import br.com.group.developer.corporation.service.collaborator.api.rest.mapper.MessageMapper;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.request.CollaboratorRequest;
import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.constants.MessageDomainConstants;
import br.com.grupo.developer.corporation.libcommons.exception.BadRequestEntityException;
import br.com.grupo.developer.corporation.libcommons.message.request.LoginRequest;
import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import br.com.grupo.developer.corporation.libcommons.validation.Validation;
import br.com.grupo.developer.corporation.libcommons.validation.response.BaseValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CollaboratorValidator implements BaseValidator<CollaboratorRequest> {
    @Override
    public void addRequestValidation(CollaboratorRequest value) {
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNull(value.getId(),
                                FieldDomainConstants.CODIGO,
                                MessageDomainConstants.CODIGO_NAO_DEVE_SER_PREENCHIDO)
                )
        );

        detailsResponses.addAll(validCnpj(value));

        detailsResponses.addAll(validFoundationDate(value));

        detailsResponses.addAll(validContact(value));

        detailsResponses.addAll(validAddress(value));

        detailsResponses.addAll(validIdCompany(value));

        throwBadRequestGeneric(
                detailsResponses,
                MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR);
    }


    public void loginRequestValidator(final LoginRequest request) {

        if(Objects.isNull(request))
            throw new BadRequestEntityException(MessageDomainConstants.INFORME_USUARIO_E_SENHA, null);

        List<MessageResponse.DetailsResponse> detailsResponseList = new ArrayList<>();

        detailsResponseList.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldDomainConstants.USERNAME,request.getUsername(),
                                MessageDomainConstants.INFORME_O_USUARIO_DE_ACESSO))
        );

        detailsResponseList.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.stringMinAndMax(
                                request.getUsername(),
                                FieldDomainConstants.USERNAME,
                                11,
                                150,
                                MessageDomainConstants.USUARIO_DEVE_SER_INFORMADO_UM_NUMERO_DE_TELEFONE_OU_UM_EMAIL
                        ) )
        );

        detailsResponseList.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldDomainConstants.PASSWORD,
                                request.getPassword(),
                                MessageDomainConstants.INFORME_A_SENHA_DE_ACESSO))
        );


        detailsResponseList.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.stringMinAndMax(
                                request.getPassword(),
                                FieldDomainConstants.PASSWORD,
                                5,
                                150,
                                MessageDomainConstants.SENHA_DEVE_CONTER_NO_MINIMO_5_CARACTERES_E_NO_MAXIMO_150
                        ) )
        );

        throwBadRequestGeneric(
                detailsResponseList,
                MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR);
    }


    @Override
    public void updateRequestValidation(CollaboratorRequest value) {
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNumber8(
                                value.getId(),
                                FieldDomainConstants.CODIGO,
                                MessageDomainConstants.CODIGO_E_OBRIGATORIO
                        )
                )
        );

        detailsResponses.addAll(validCnpj(value));

        detailsResponses.addAll(validFoundationDate(value));

        detailsResponses.addAll(validPasswordIsNull(value));

        detailsResponses.addAll(validContact(value));

        detailsResponses.addAll(validAddress(value));

        detailsResponses.addAll(validIdCompany(value));

        detailsResponses.addAll(validIdTypeCollaborator(value.getTypeCollaborator()));

        throwBadRequestGeneric(
                detailsResponses,
                MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR);
    }



    public void pathVariableId(final String id){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNumber8(id,
                                FieldDomainConstants.CODIGO,
                                MessageDomainConstants.CODIGO_DEVE_CONTER_NO_MINIMO_1_CARACTERE_E_NO_MAXIMO_8)
                )
        );

        throwBadRequestGeneric(
                detailsResponses,
                MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR);
    }

    private List<MessageResponse.DetailsResponse> validIdTypeCollaborator(CollaboratorRequest.TypeCollaborator request) {
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();


        if(Objects.isNull(request)){
            detailsResponses.add(new MessageResponse.DetailsResponse(FieldDomainConstants.TIPO_COLABORATOR,MessageDomainConstants.TIPO_USUARIO_DEVE_SER_INFORMADO,null));
        }else {
            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.mandatoryNumber8(
                                    request.getId(),
                                    FieldDomainConstants.CODIGO,
                                    MessageDomainConstants.CODIGO_TIPO_USUARIO_DEVE_SER_UM_NUMERO_VALIDO
                            )
                    )
            );
        }

        return detailsResponses;
    }

    public void pathVariableCnpj(final String cnpj){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.stringMinAndMax(cnpj,
                                FieldDomainConstants.CNPJ,
                                11,
                                14,
                                MessageDomainConstants.CNPJ_DEVE_CONTER_NO_MINIMO_11_CARACTERES_E_NO_MAXIMO_14_NA_AUSENCIA_DO_CNPJ_PODE_INFORMAR_O_CPF
                        )
                )
        );

        throwBadRequestGeneric(
                detailsResponses,
                MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR);
    }

    public void pathPaginationValidator(final String searchTerm, final String codEmpresa, final String page, final String size){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldDomainConstants.PAGE,
                                page,
                                MessageDomainConstants.O_CAMPO_PAGE_E_OBRIGATORIO)
                )
        );

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldDomainConstants.SIZE,
                                size,
                                MessageDomainConstants.O_CAMPO_SIZE_E_OBRIGATORIO)
                )
        );

        if(Boolean.FALSE.equals(StringUtils.isEmpty(searchTerm))){
            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.stringMinAndMax(
                                    searchTerm,
                                    FieldDomainConstants.SEARCHTERM,
                                    0,
                                    50,
                                    MessageDomainConstants.O_CAMPO_SEARCHTERM_DEVE_CONTER_NO_MAXIMO_50_CARACTERES)
                    )
            );
        }

        detailsResponses.add(
             MessageMapper.INSTANCE.toDetailsResponse(
                     Validation.mandatoryNumber8(codEmpresa,FieldDomainConstants.CODEMPRESA,"O campo codEmpresa é obrigatório e deve conter somente números.")
             )
        );


        throwBadRequestGeneric(
                detailsResponses,
                MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR);
    }


    private List<MessageResponse.DetailsResponse> validIdCompany(final CollaboratorRequest request){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNumber8(
                                request.getIdCompany(),
                                FieldDomainConstants.ID_COMPANY,
                                MessageDomainConstants.CODIGO_DA_EMPRESA_E_OBRIGATORIO
                        )
                )
        );

        return detailsResponses;
    }

    private List<MessageResponse.DetailsResponse> validContact(final CollaboratorRequest request){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        if(Objects.isNull(request.getContact())){
            detailsResponses.add(
                    new MessageResponse.DetailsResponse(
                            FieldDomainConstants.CONTATO,
                            MessageDomainConstants.DADOS_DE_CONTATO_E_OBRIGATORIO,
                            null
                    )
            );
        }else {

            detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNumberPhone(
                                request.getContact().getMainPhone(),
                                FieldDomainConstants.TELEFONE_PRINCIPAL,
                                MessageDomainConstants.TELEFONE_PRINCIPAL_E_OBRIGATORIO
                        )
                )
            );

            if(!StringUtils.isEmpty(request.getContact().getTelephone())){
                detailsResponses.add(
                        MessageMapper.INSTANCE.toDetailsResponse(
                                Validation.mandatoryNumberPhone(
                                        request.getContact().getTelephone(),
                                        FieldDomainConstants.TELEFONE,
                                        MessageDomainConstants.TELEFONE_INVALIDO
                                )
                        )
                );
            }

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.mandatoryEmail(
                                    request.getContact().getEmail(),
                                    FieldDomainConstants.EMAIL,
                                    MessageDomainConstants.EMAIL_E_OBRIGATORIO_OU_ESTA_INVALIDO
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
                                FieldDomainConstants.CNPJ,
                                MessageDomainConstants.CNPJ_CPF_E_OBRIGATORIO,
                                MessageDomainConstants.CNPJ_E_INVALIDO,
                                MessageDomainConstants.CPF_E_INVALIDO,
                                MessageDomainConstants.CNPJ_CPF_E_OBRIGATORIO
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
                            FieldDomainConstants.ENDERECO,
                            MessageDomainConstants.ENDERECO_E_OBRIGATORIO,
                            null
                    )
            );
        }else{

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.notNullOrEmpty(
                                    FieldDomainConstants.CEP,
                                    request.getAddress().getZipCode(),
                                    MessageDomainConstants.CEP_E_OBRIGATORIO
                            )
                    )
            );

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.mandatoryNumber8(
                                    request.getAddress().getZipCode(),
                                    FieldDomainConstants.CEP,
                                    MessageDomainConstants.CEP_INVALIDO
                            )
                    )
            );


            detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.notNullOrEmpty(
                                FieldDomainConstants.LOGRADOURO,
                                request.getAddress().getNeighborhood(),
                                MessageDomainConstants.LOGRADOURO_E_OBRIGATORIO
                        )
                )
            );

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.notNullOrEmpty(
                                    FieldDomainConstants.BAIRRO,
                                    request.getAddress().getNeighborhood(),
                                    MessageDomainConstants.BAIRRO_E_OBRIGATORIO
                            )
                    )
            );

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.notNullOrEmpty(
                                    FieldDomainConstants.LOCALIDADE,
                                    request.getAddress().getLocation(),
                                    MessageDomainConstants.LOCALIDADE_E_OBRIGATORIO
                            )
                    )
            );

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.notNullOrEmpty(
                                    FieldDomainConstants.UF,
                                    request.getAddress().getState(),
                                    MessageDomainConstants.UF_E_OBRIGATORIO
                            )
                    )
            );

            detailsResponses.add(
                    MessageMapper.INSTANCE.toDetailsResponse(
                            Validation.mandatoryNumber8(
                                    request.getAddress().getNumber(),
                                    FieldDomainConstants.NUMERO,
                                    MessageDomainConstants.NUMERO_E_OBRIGATORIO_OU_INVALIDO
                            )
                    )
            );

        }

        return detailsResponses;
    }


    private List<MessageResponse.DetailsResponse> validPasswordIsNull(final CollaboratorRequest request){
        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryNull(
                                request.getPassword(),
                                FieldDomainConstants.PASSWORD,
                                MessageDomainConstants.PASSWORD_DEVE_SER_NULLO
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
                                FieldDomainConstants.DATA_NASCIMENTO,
                                request.getBirthDate(),
                                MessageDomainConstants.DATA_NASCIMENTO_E_OBRIGATORIO
                        )
                )
        );


        detailsResponses.add(
                MessageMapper.INSTANCE.toDetailsResponse(
                        Validation.mandatoryDate(
                                request.getBirthDate(),
                                FieldDomainConstants.DATA_NASCIMENTO,
                                MessageDomainConstants.DATA_NASCIMENTO_DEVE_ESTA_NO_FORMATO_ANO_MES_DIA_OU_ANO_MES_DIA_HORAS_MINUTOS_E_SEGUNDO
                        )
                )
        );


        return detailsResponses;
    }
}
