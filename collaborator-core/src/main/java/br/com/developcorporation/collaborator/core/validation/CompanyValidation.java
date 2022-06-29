package br.com.developcorporation.collaborator.core.validation;

import br.com.developcorporation.collaborator.core.constants.FieldConstants;
import br.com.developcorporation.collaborator.core.constants.MessageConstants;
import br.com.developcorporation.collaborator.core.enums.CoreEnum;
import br.com.developcorporation.collaborator.core.mapper.MessageMapper;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Company;
import br.com.developcorporation.collaborator.domain.model.FollowUp;
import br.com.developcorporation.collaborator.domain.model.SystemType;
import br.com.developcorporation.lib.commons.validation.Validation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CompanyValidation implements BaseValidator<Company>{


    @Override
    public void add(Company company) {

        if(Objects.isNull(company)){
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.PREENCHA_O_FORMULARIO_DE_CADASTRO_DA_NOVA_EMPRESA,
                    null
            );
        }

        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNull(
                                company.getId(),
                                FieldConstants.CODIGO,
                                MessageConstants.CODIGO_NAO_DEVE_SER_PREENCHIDO_NO_FORMULARIO_DE_CADASTRO)
                )
        );

        detailsList.addAll(validFundationDate(company));

        detailsList.addAll(validName(company));

        detailsList.addAll(validCnpjCpf(company.getCnpj()));

        detailsList.addAll(validStateRegistration(company.getStateRegistration()));

        detailsList.addAll(validPhone(company));

        detailsList.addAll(validPassword(company));

        detailsList.addAll(validTypeSystem(company.getSystemType()));

        detailsList.addAll(validFollowUp(company.getFollowUp()));

        detailsList.addAll(validEmail(company));

        detailsList.addAll(validAddress(company));

        detailsList.addAll(validTerms(company));

        throwDomainExceptionGeneric(
                CoreEnum.UNPROCESSABLE_ENTITY,
                MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DA_EMPRESA,
                detailsList);
    }

    @Override
    public void update(Company company) {
        if(Objects.isNull(company))
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.PREENCHA_O_FORMULARIO_DE_ALTERACAO_DA_EMPRESA,
                    null
            );

        List<Message.Details> detailsList = new ArrayList<>();

        if(company.getId() <= 0){
            detailsList.add(
                    new Message.Details(
                            FieldConstants.CODIGO,
                            MessageConstants.CODIGO_E_OBRIGATORIO,
                            null
                    )
            );
        }

        detailsList.addAll(validFundationDate(company));

        detailsList.addAll(validName(company));

        detailsList.addAll(validCnpjCpf(company.getCnpj()));

        detailsList.addAll(validStateRegistration(company.getStateRegistration()));

        detailsList.addAll(validPhone(company));

        detailsList.addAll(validFollowUp(company.getFollowUp()));

        detailsList.addAll(validAddress(company));

        throwDomainExceptionGeneric(
                CoreEnum.UNPROCESSABLE_ENTITY,
                MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DA_EMPRESA,
                detailsList);
    }

    public List<Message.Details> validCnpjCpf(final String cnpjCpf){
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryCNPJorCPF(cnpjCpf,
                               FieldConstants.CNPJ_CPF,
                               MessageConstants.CNPJ_CPF_E_OBRIGATORIO,
                            MessageConstants.CNPJ_INFORMARDO_E_INVALIDO,
                            MessageConstants.CPF_INFORMARDO_E_INVALIDO,
                            MessageConstants.CNPJ_CPF_INFORMADO_E_INVALIDO
                    )
            )
        );

        return detailsList;
    }

    public void validCnpj(final String cnpj){

        throwDomainExceptionGeneric(
                CoreEnum.UNPROCESSABLE_ENTITY,
                MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DA_EMPRESA,
                validCnpjCpf(cnpj));
    }

    private List<Message.Details> validStateRegistration(final String stateRegistration){
        List<Message.Details> detailsList = new ArrayList<>();

        if(!StringUtils.isEmpty(stateRegistration)){

            if(!StringUtils.isNumeric(stateRegistration)){

              detailsList.add(
                      new Message.Details(
                              FieldConstants.INSCRICAO_ESTADUAL,
                              MessageConstants.INSCRICAO_ESTADUAL_DEVE_CONTER_SOMENTE_NUMERO,
                              stateRegistration)
              );
            }else{
                if(detailsList.isEmpty()){
                    detailsList.add(
                            MessageMapper.INSTANCE.toDetailsDto(
                                    Validation.stringMinAndMax(
                                            stateRegistration,
                                            FieldConstants.INSCRICAO_ESTADUAL,
                                            0,
                                            30,
                                            MessageConstants.INSCRICAO_ESTADUAL_DEVE_CONTER_NO_MAXIMO_30_CARACTERES)
                          )
                  );
              }
          }
      }
        return detailsList;
    }

    private List<Message.Details> validTypeSystem(SystemType systemType){
        List<Message.Details> detailsList = new ArrayList<>();

        if(Objects.isNull(systemType)){
            detailsList.add(
                    new Message.Details(
                            FieldConstants.TIPO_SISTEMA,
                            MessageConstants.CODIGO_TIPO_DE_ACESSO_E_OBRIGATORIO,
                            null)
            );
        }else{
            if(systemType.getId() <= 0){
                detailsList.add(
                        new Message.Details(
                                FieldConstants.TIPO_SISTEMA,
                                MessageConstants.CODIGO_TIPO_DE_ACESSO_DE_DEVE_SER_INFORMADO_MAIOR_QUE_ZERO,
                                systemType.getId().toString())
                );
            }
        }
        return detailsList;
    }

    private List<Message.Details> validFollowUp(FollowUp up){
        List<Message.Details> detailsList = new ArrayList<>();

        if(Objects.isNull(up)){
            detailsList.add(
                    new Message.Details(
                            FieldConstants.CODIGO,
                            MessageConstants.CODIGO_SEGUIMENTO_E_OBRIGATORIO,
                            null));
        }else {
            if(up.getId() <= 0){
                detailsList.add(
                        new Message.Details(
                                FieldConstants.CODIGO,
                                MessageConstants.CODIGO_SEGUIMENTO_DE_DEVE_SER_INFORMADO_MAIOR_QUE_ZERO,
                                up.getId().toString())
                );
            }
        }

        return detailsList;
    }

    private List<Message.Details> validAddress(final Company company){
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNumber8(
                                company.getZipCode(),
                                FieldConstants.CEP,
                                MessageConstants.CEP_DEVE_SER_INFORMADO_NO_FORMATO_DE_8_CARACTERES
                        )
                )
        );

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.validationNullOrEmptyMaxCaracter(
                                company.getPublicPlace(),
                                FieldConstants.LOGRADOURO,
                                MessageConstants.LOGRADOURO_DEVE_SER_INFORMADO,
                                MessageConstants.LOGRADOURO_DEVE_SER_INFORMADO_COM_NO_MAXIMO_90_CARACTERES,
                                1,
                                90
                        )
                )
        );

        if(!StringUtils.isEmpty(company.getComplement())){
            detailsList.add(
                    MessageMapper.INSTANCE.toDetailsDto(
                            Validation.stringMinAndMax(
                                    company.getComplement(),
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
                                company.getNeighborhood(),
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
                                company.getLocation(),
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
                                company.getState(),
                                FieldConstants.UF,
                                MessageConstants.UF_DEVE_SER_INFORMADO_COM_BASE_NOS_ESTADOS_BRASILEIROS
                        )
                )
        );

      if(company.getNumber() <= 0){
          detailsList.add(
                  new Message.Details(
                           FieldConstants.NUMERO,
                           MessageConstants.NUMERO_DEVE_SER_INFORMADO,
                           String.valueOf(company.getNumber())
                  )
          );
      }
        return detailsList;
    }

    private List<Message.Details> validName(final Company company){
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.validationNullOrEmptyMaxCaracter(
                                company.getCorporateName(),
                                FieldConstants.RAZAO_SOCIAL,
                                MessageConstants.RAZAO_SOCIAL_E_OBRIGATORIO,
                                MessageConstants.RAZAO_SOCIAL_DEVE_SEM_PREENCHIDA_COM_NO_MINIMO_10_CARACTERES_E_NO_MAXIMO_80,
                                10,
                                80
                        )
                )
        );

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.validationNullOrEmptyMaxCaracter(
                                company.getFantasyName(),
                                FieldConstants.NOME_FANTASIA,
                                MessageConstants.NOME_FANTASIA_E_OBRIGATORIO,
                                MessageConstants.NOME_FANTASIA_DEVE_SEM_PREENCHIDA_COM_NO_MINIMO_10_CARACTERES_E_NO_MAXIMO_80,
                                10,
                                80
                        )
                )
        );

        return detailsList;
    }

    private List<Message.Details> validPhone(final Company company){
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryNumberPhone(
                                company.getMainPhone(),
                                FieldConstants.TELEFONE_PRINCIPAL,
                                MessageConstants.TELEFONE_PRINCIPAL_DEVE_SER_NUMERO_VALIDO
                        )
                )
        );

        if(!StringUtils.isEmpty(company.getTelephone())){
            detailsList.add(
                    MessageMapper.INSTANCE.toDetailsDto(
                            Validation.mandatoryNumberPhone(
                                    company.getTelephone(),
                                    FieldConstants.TELEFONE,
                                    MessageConstants.TELEFONE_DEVE_SER_NUMERO_VALIDO
                            )
                    )
            );
        }

        return detailsList;
    }

    private List<Message.Details> validPassword(final Company company){
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryPassword(
                                company.getPassword(),
                                FieldConstants.PASSWORD,
                                MessageConstants.PASSWORD_DEVE_CONTER_NO_MINIMO_8_CARACETERES_E_DEVE_CONTER_UMA_LETRA_MAIUSCULA_UMA_LETRA_MINUSCULA_UM_SIBOLO_E_UM_CARACTER_ESPECIAL
                        )
                )
        );

        return detailsList;
    }

    private List<Message.Details> validEmail(final Company company){
        List<Message.Details> detailsList = new ArrayList<>();

        detailsList.add(
                MessageMapper.INSTANCE.toDetailsDto(
                        Validation.mandatoryEmail(
                                company.getEmail(),
                                FieldConstants.EMAIL,
                                MessageConstants.EMAIL_DEVE_SER_ENVIADO_NO_FORMATO_VALIDO
                        )
                )
        );

        return detailsList;
    }

    private List<Message.Details> validFundationDate(final Company company){
        List<Message.Details> detailsList = new ArrayList<>();

        if(Objects.isNull(company.getFoundationDate())){
            detailsList.add(
                    new Message.Details(
                            FieldConstants.DATA_FUNDACAO,
                            MessageConstants.DATA_DE_FUNDACAO_E_OBRIGATORIO,
                            null
                    )
            );
        }

        return detailsList;
    }

    private List<Message.Details> validTerms(final Company company){
        List<Message.Details> detailsList = new ArrayList<>();

        if(Boolean.FALSE.equals(company.isAcceptTerms())){
            detailsList.add(
                    new Message.Details(
                            FieldConstants.ACEITA_TERMOS_DE_USO,
                            MessageConstants.PARA_REALIZAR_O_CADASTRO_LEIA_O_CONTRATO_E_ACEITE_AS_CONDICOES,
                            null
                    )
            );
        }
        return detailsList;
    }
}
