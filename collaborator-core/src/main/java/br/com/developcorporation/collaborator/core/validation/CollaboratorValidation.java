package br.com.developcorporation.collaborator.core.validation;

import br.com.developcorporation.collaborator.core.enums.TypeCollaboratorEnum;
import br.com.developcorporation.collaborator.domain.constants.FieldConstants;
import br.com.developcorporation.collaborator.core.mapper.MessageMapper;
import br.com.developcorporation.collaborator.domain.constants.MessageConstants;
import br.com.developcorporation.collaborator.core.enums.CoreEnum;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.lib.commons.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CollaboratorValidation implements BaseValidator<Collaborator>{


    @Override
    public void add(Collaborator value) {

        if(Objects.isNull(value)){
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.PREENCHA_O_FORMULARIO_DE_CADASTRO_DA_NOVO_USUARIO,
                    null
            );
        }

        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNull(
                                value.getId(),
                                FieldConstants.CODIGO,
                                MessageConstants.CODIGO_NAO_DEVE_SER_PREENCHIDO_NO_FORMULARIO_DE_CADASTRO)
                )
        );

        detailsList.addAll(validBirthDate(value));

        detailsList.addAll(validPhone(value.getContact()));

        detailsList.addAll(validPassword(value));

        detailsList.addAll(validAddress(value.getAddress()));

        detailsList.addAll(validTypeCollaborator(value));

        throwDomainExceptionGeneric(
                CoreEnum.UNPROCESSABLE_ENTITY,
                MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DO_USUARIO,
                detailsList);
    }

    @Override
    public void update(Collaborator value) {
        if(Objects.isNull(value)){
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.PREENCHA_O_FORMULARIO_DE_ALTERACAO_DO_USUARIO,
                    null
            );
        }

        List<Message.Details> detailsList = new ArrayList<>();

        if(value.getId() == null)
            value.setId(0L);

        if(value.getId() <= 0){
            detailsList.add(
                    new Message.Details(
                            FieldConstants.CODIGO,
                            MessageConstants.CODIGO_E_OBRIGATORIO,
                            value.getId().toString())
            );
        }

        detailsList.addAll(validBirthDate(value));

        detailsList.addAll(validPhone(value.getContact()));

        detailsList.addAll(validPassword(value));

        detailsList.addAll(validAddress(value.getAddress()));

        detailsList.addAll(validTypeCollaborator(value));

        throwDomainExceptionGeneric(
                CoreEnum.UNPROCESSABLE_ENTITY,
                MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DO_USUARIO,
                detailsList);
    }

    public List<Message.Details> validTypeCollaborator(final Collaborator collaborator){
        List<Message.Details> detailsList = new ArrayList<>();

        if(Boolean.FALSE.equals(TypeCollaboratorEnum.existsCode(collaborator.getTypeUser()))){
            detailsList.add(
                    new Message.Details(
                            FieldConstants.TIPO_COLABORATOR,
                            MessageConstants.TIPO_DE_COLABORADOR_INVALIDO,
                            collaborator.getTypeUser()));
        }

        return detailsList;
    }

    private List<Message.Details> validBirthDate(final Collaborator collaborator){
        List<Message.Details> detailsList = new ArrayList<>();

        if(Objects.isNull(collaborator.getBirthDate())){
            detailsList.add(
                    new Message.Details(
                            FieldConstants.DATA_NASCIMENTO,
                            MessageConstants.DATA_DE_NASCIMENTO_E_OBRIGATORIO,
                            null
                    )
            );
        }

        return detailsList;
    }

    private List<Message.Details> validPhone(final Collaborator.Contact contact){

        if(Objects.isNull(contact)){
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.PREENCHA_OS_DADOS_DE_CONTATO,
                    null
            );
        }

        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNumberPhone(
                                contact.getMainPhone(),
                                FieldConstants.TELEFONE_PRINCIPAL,
                                MessageConstants.TELEFONE_PRINCIPAL_DEVE_SER_NUMERO_VALIDO
                        )
                )
        );

        if(!StringUtils.isEmpty(contact.getTelephone())){
            detailsList.add(
                    MessageMapper.INSTANCE.toDetailsDto(
                            Validation.mandatoryNumberPhone(
                                    contact.getTelephone(),
                                    FieldConstants.TELEFONE,
                                    MessageConstants.TELEFONE_DEVE_SER_NUMERO_VALIDO
                            )
                    )
            );
        }

        detailsList.add(validEmail(contact.getEmail()));

        return detailsList;
    }

    private List<Message.Details> validPassword(final Collaborator collaborator){

        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.validationNullOrEmptyMaxCaracter(
                                collaborator.getPassword(),
                                FieldConstants.PASSWORD,
                                MessageConstants.PASSWORD_NAO_PODE_SER_NULO,
                                MessageConstants.PASSWORD_DEVE_CONTER_NO_MINIMO_8_CARACTERES_E_MAXIMO_15_CARACTERES,
                                6,
                                350)

                )
        );

        return detailsList;
    }

    private Message.Details validEmail(final String email){

         return  MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryEmail(
                                email,
                                FieldConstants.EMAIL,
                                MessageConstants.EMAIL_DEVE_SER_ENVIADO_NO_FORMATO_VALIDO
                        )
                );
    }

    private List<Message.Details> validAddress(final Collaborator.Address address){
        
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNumber8(
                                address.getZipCode(),
                                FieldConstants.CEP,
                                MessageConstants.CEP_DEVE_SER_INFORMADO_NO_FORMATO_DE_8_CARACTERES
                        )
                )
        );

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.validationNullOrEmptyMaxCaracter(
                                address.getAddress(),
                                FieldConstants.LOGRADOURO,
                                MessageConstants.LOGRADOURO_DEVE_SER_INFORMADO,
                                MessageConstants.LOGRADOURO_DEVE_SER_INFORMADO_COM_NO_MAXIMO_90_CARACTERES,
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
                                    FieldConstants.COMPLEMENTO,
                                    0,
                                    90,
                                    MessageConstants.COMPLEMENTO_DEVE_CONTER_NO_MAXIMO_90_CARACTERES
                            )
                    )
            );
        }

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.validationNullOrEmptyMaxCaracter(
                                address.getNeighborhood(),
                                FieldConstants.BAIRRO,
                                MessageConstants.BAIRRO_DEVE_SER_INFORMADO,
                                MessageConstants.BAIRRO_DEVE_SER_INFORMADO_COM_NO_MAXIMO_90_CARACTERES,
                                1,
                                90
                        )
                )
        );

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.validationNullOrEmptyMaxCaracter(
                                address.getLocation(),
                                FieldConstants.LOCALIDADE,
                                MessageConstants.LOCALIDADE_CIDADE_DEVE_SER_INFORMADO,
                                MessageConstants.LOCALIDADE_CIDADE_DEVE_SER_INFORMADO_COM_NO_MAXIMO_90_CARACTERES,
                                1,
                                90
                        )
                )
        );

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryUf(
                                address.getState(),
                                FieldConstants.UF,
                                MessageConstants.UF_DEVE_SER_INFORMADO_COM_BASE_NOS_ESTADOS_BRASILEIROS
                        )
                )
        );

        if(address.getNumber() <= 0){
            detailsList.add(
                    new Message.Details(
                            FieldConstants.NUMERO,
                            MessageConstants.NUMERO_DEVE_SER_INFORMADO,
                            String.valueOf(address.getNumber())
                    )
            );
        }
        return detailsList;
    }

}
