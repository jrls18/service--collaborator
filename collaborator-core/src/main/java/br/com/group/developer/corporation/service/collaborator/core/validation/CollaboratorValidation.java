package br.com.group.developer.corporation.service.collaborator.core.validation;


import br.com.group.developer.corporation.service.collaborator.core.mapper.MessageMapper;
import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.constants.MessageDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.exception.CollaboratorErrorValidatorException;
import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import br.com.grupo.developer.corporation.libcommons.enums.CoreEnum;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CollaboratorValidation implements BaseCoreValidator<Collaborator> {


    @Override
    public void add(Collaborator value) {

        if(Objects.isNull(value)){
            throw new CollaboratorErrorValidatorException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.PREENCHA_O_FORMULARIO_DE_CADASTRO_DA_NOVO_COLABORADOR,
                    null
            );
        }

        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNull(
                                value.getId(),
                                FieldDomainConstants.CODIGO,
                                MessageDomainConstants.CODIGO_NAO_DEVE_SER_PREENCHIDO_NO_FORMULARIO_DE_CADASTRO)
                )
        );

        detailsList.addAll(validBirthDate(value));

        detailsList.addAll(validPhone(value.getContact()));

        detailsList.addAll(validPassword(value));

        detailsList.addAll(validAddress(value.getAddress()));

        throwCollaboratorErrorValidatorException(detailsList);
    }

    @Override
    public void update(Collaborator value) {
        if(Objects.isNull(value)){
            throw new CollaboratorErrorValidatorException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.PREENCHA_O_FORMULARIO_DE_ALTERACAO_DO_COLABORADOR,
                    null
            );
        }

        List<Message.Details> detailsList = new ArrayList<>();

        if(value.getId() == null)
            value.setId(0L);

        if(value.getId() <= 0){
            detailsList.add(
                    new Message.Details(
                            FieldDomainConstants.CODIGO,
                            MessageDomainConstants.CODIGO_E_OBRIGATORIO,
                            value.getId().toString())
            );
        }

        detailsList.addAll(validBirthDate(value));

        detailsList.addAll(validPhone(value.getContact()));

        detailsList.addAll(validPasswordIsNull(value));

        detailsList.addAll(validAddress(value.getAddress()));

        detailsList.addAll(validIdCompanyIsNULL(value));

        throwCollaboratorErrorValidatorException(detailsList);
    }

    public void validUuid(final String uuid){
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                Validation.validUUID(
                        uuid,
                         FieldDomainConstants.CHAVE_ATIVACAO_PERFIL,
                        MessageDomainConstants.CHAVE_ATIVACAO_PERFIL_E_OBRIGATORIO_E_DEVE_SER_VALIDO));

        throwCollaboratorErrorValidatorException(detailsList);
    }


    private List<Message.Details> validBirthDate(final Collaborator collaborator){
        List<Message.Details> detailsList = new ArrayList<>();

        if(Objects.isNull(collaborator.getBirthDate())){
            detailsList.add(
                    new Message.Details(
                            FieldDomainConstants.DATA_NASCIMENTO,
                            MessageDomainConstants.DATA_DE_NASCIMENTO_E_OBRIGATORIO,
                            null
                    )
            );
        }

        return detailsList;
    }

    private List<Message.Details> validPhone(final Collaborator.Contact contact){

        if(Objects.isNull(contact)){
            throw new CollaboratorErrorValidatorException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.PREENCHA_OS_DADOS_DE_CONTATO,
                    null
            );
        }

        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNumberPhone(
                                contact.getMainPhone(),
                                FieldDomainConstants.TELEFONE_PRINCIPAL,
                                MessageDomainConstants.TELEFONE_PRINCIPAL_DEVE_SER_NUMERO_VALIDO
                        )
                )
        );

        if(!StringUtils.isEmpty(contact.getTelephone())){
            detailsList.add(
                    MessageMapper.INSTANCE.toDetailsDto(
                            Validation.mandatoryNumberPhone(
                                    contact.getTelephone(),
                                    FieldDomainConstants.TELEFONE,
                                    MessageDomainConstants.TELEFONE_DEVE_SER_NUMERO_VALIDO
                            )
                    )
            );
        }

        detailsList.add(validEmail(contact.getEmail()));

        return detailsList;
    }

    private List<Message.Details> validPasswordIsNull(final Collaborator collaborator){
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
          MessageMapper.INSTANCE.toDetailsDto(
                  Validation.mandatoryNull(
                          collaborator.getPassword(),
                          FieldDomainConstants.PASSWORD,
                          MessageDomainConstants.PASSWORD_DEVE_SER_NULLO
                  )
          )
        );

        return  detailsList;
    }

    private List<Message.Details> validIdCompanyIsNULL(final Collaborator collaborator){
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNull(
                                collaborator.getIdCompany(),
                                FieldDomainConstants.ID_COMPANY,
                                MessageDomainConstants.ID_COMPANY_NAO_DEVE_SER_PREENCHIDO
                        )
                )
        );

        return  detailsList;
    }

    private List<Message.Details> validPassword(final Collaborator collaborator){

        List<Message.Details> detailsList = new ArrayList<>();

        if(Boolean.FALSE.equals(StringUtils.isEmpty(collaborator.getPassword()))){
            detailsList.add(
                    MessageMapper.INSTANCE.toDetailsDto(
                            Validation.validationNullOrEmptyMaxCaracter(
                                    collaborator.getPassword(),
                                    FieldDomainConstants.PASSWORD,
                                    MessageDomainConstants.PASSWORD_NAO_PODE_SER_NULO,
                                    MessageDomainConstants.PASSWORD_DEVE_CONTER_NO_MINIMO_8_CARACTERES_E_MAXIMO_15_CARACTERES,
                                    6,
                                    350)

                    )
            );
        }
        return detailsList;
    }

    private Message.Details validEmail(final String email){

         return  MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryEmail(
                                email,
                                FieldDomainConstants.EMAIL,
                                MessageDomainConstants.EMAIL_DEVE_SER_ENVIADO_NO_FORMATO_VALIDO
                        )
                );
    }

    private List<Message.Details> validAddress(final Collaborator.Address address){
        
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNumber8(
                                address.getZipCode(),
                                FieldDomainConstants.CEP,
                                MessageDomainConstants.CEP_DEVE_SER_INFORMADO_NO_FORMATO_DE_8_CARACTERES
                        )
                )
        );

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.validationNullOrEmptyMaxCaracter(
                                address.getPublicPlace(),
                                FieldDomainConstants.LOGRADOURO,
                                MessageDomainConstants.LOGRADOURO_DEVE_SER_INFORMADO,
                                MessageDomainConstants.LOGRADOURO_DEVE_SER_INFORMADO_COM_NO_MAXIMO_90_CARACTERES,
                                1,
                                90
                        )
                )
        );

        if(!StringUtils.isEmpty(address.getComplement())){
            detailsList.add(
                    MessageMapper.INSTANCE.toDetailsDto(
                            Validation.stringMinAndMax(
                                    address.getComplement(),
                                    FieldDomainConstants.COMPLEMENTO,
                                    0,
                                    90,
                                    MessageDomainConstants.COMPLEMENTO_DEVE_CONTER_NO_MAXIMO_90_CARACTERES
                            )
                    )
            );
        }

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.validationNullOrEmptyMaxCaracter(
                                address.getNeighborhood(),
                                FieldDomainConstants.BAIRRO,
                                MessageDomainConstants.BAIRRO_DEVE_SER_INFORMADO,
                                MessageDomainConstants.BAIRRO_DEVE_SER_INFORMADO_COM_NO_MAXIMO_90_CARACTERES,
                                1,
                                90
                        )
                )
        );

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.validationNullOrEmptyMaxCaracter(
                                address.getLocation(),
                                FieldDomainConstants.LOCALIDADE,
                                MessageDomainConstants.LOCALIDADE_CIDADE_DEVE_SER_INFORMADO,
                                MessageDomainConstants.LOCALIDADE_CIDADE_DEVE_SER_INFORMADO_COM_NO_MAXIMO_90_CARACTERES,
                                1,
                                90
                        )
                )
        );

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryUf(
                                address.getState(),
                                FieldDomainConstants.UF,
                                MessageDomainConstants.UF_DEVE_SER_INFORMADO_COM_BASE_NOS_ESTADOS_BRASILEIROS
                        )
                )
        );

        if(address.getNumber() <= 0){
            detailsList.add(
                    new Message.Details(
                            FieldDomainConstants.NUMERO,
                            MessageDomainConstants.NUMERO_DEVE_SER_INFORMADO,
                            String.valueOf(address.getNumber())
                    )
            );
        }
        return detailsList;
    }

}
