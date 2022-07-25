package br.com.developcorporation.collaborator.core.service.impl;

import br.com.developcorporation.collaborator.core.constants.MessageConstants;
import br.com.developcorporation.collaborator.core.enums.CoreEnum;
import br.com.developcorporation.collaborator.core.infrastructure.ContextHolder;
import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.core.validation.AuthorizationValidation;
import br.com.developcorporation.collaborator.core.validation.CompanyValidation;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.domain.model.Status;
import br.com.developcorporation.collaborator.domain.port.CompanyPort;
import br.com.developcorporation.collaborator.domain.port.SendMessagePort;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CollaboratorServiceImpl implements CollaboratorService {

    public static final long ACTIVE = 1L;

    private final PasswordEncoder encoder;
    private final CompanyPort port;
    private final SendMessagePort messagePort;
    private final CompanyValidation validator;
    private final AuthorizationValidation validatorAuthorization;


    @Transactional
    @Override
    public Message add(Collaborator dto) {

        validatorAuthorization.validCredentials();


        validator.add(dto);

        dto.setCpfCnpj(StringUtils.leftPad(dto.getCpfCnpj(),14,"0"));
        validAddExists(dto);

        try {
            dto.setDataRegister(LocalDateTime.now());

            dto.setPassword(encoder.encode(dto.getPassword()));

            Collaborator.Status status = new Collaborator.Status();
            status.setId(ACTIVE);
            dto.setStatus(status);

            Long id =  port.add(dto);
            dto.setId(id);

            //messagePort.send(dto);
        }catch (Exception ex){
            throw new DomainException(
                    CoreEnum.INTERNAL_SERVER_ERROR.getCode(),
                    MessageConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE,
                    null);
        }



        return new Message(CoreEnum.CREATED.getCode(),
                MessageConstants.USUARIO_CADASTRADA_COM_SUCESSO_NO_MAXIMO_24_HORAS_SERA_LIBERADO_SEU_ACESSO_NO_SISTEMA_CADASTRADA_COM_SUCESSO_NO_MAXIMO_24_HORAS_SERA_LIBERADO_SEU_ACESSO_NO_SISTEMA);
    }

    @Transactional
    @Override
    public Message update(Collaborator dto) {

        /*
        validatorAuthorization.validCredentials();


        validator.update(dto);

        dto.setCpfCnpj(StringUtils.leftPad(dto.getCpfCnpj(),14,"0"));
        validUpdateExists(dto);

        try {
            Collaborator companyOriginal = (Collaborator) ContextHolder.get().getMap().get("company");

            dto.setAcceptTerms(companyOriginal.isAcceptTerms());
            dto.setSystemType(companyOriginal.getSystemType());

            port.update(dto);
        }catch (Exception ex){
            throw new DomainException(
                    CoreEnum.INTERNAL_SERVER_ERROR.getCode(),
                    MessageConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE,
                    null);
        }

         */



        return new Message(CoreEnum.ACCEPTED.getCode(),
                MessageConstants.EMPRESA_ATUALIZADA_COM_SUCESSO);
    }



    @Override
    public Collaborator getById(Long id) {

        validatorAuthorization.validCredentials();

       // return port.getById(id);
        return null;
    }

    @Override
    public Collaborator getByCnpj(String cnpj) {

        validatorAuthorization.validCredentials();

       // validator.validCnpj(cnpj);

       // return port.getByCnpj(cnpj);
        return null;
    }


    private void validAddExists(Collaborator dto){
        List<Message.Details> details = new ArrayList<>();

        /*
        Company companyCnpj =  port.getByCnpj(dto.getCnpj());

        Company companyCorporateName = port.getByCorporateName(dto.getCorporateName());

        if(Objects.nonNull(companyCnpj)){
            details.add(
                    new Message.Details(
                            FieldConstants.CNPJ_CPF,
                            MessageConstants.CNPJ_CPF_JA_EXISTE_CADASTRADO,
                            companyCnpj.getCnpj()));
        }

        if(Objects.nonNull(companyCorporateName)){
            details.add(
                    new Message.Details(
                            FieldConstants.RAZAO_SOCIAL,
                            MessageConstants.RAZAO_SOCIAL_JA_EXISTE_CADASTRADA,
                            companyCorporateName.getCorporateName()));
        }

        if(!details.isEmpty())
            throw new DomainException(
                CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DA_EMPRESA,
                details);

         */
    }

    private void validUpdateExists(Collaborator dto) {
        /*
        List<Message.Details> details = new ArrayList<>();

        Company companyOriginal = port.getById(dto.getId());

        ContextHolder.get().setMap("company", companyOriginal);

        Company companyCnpj =  port.getByCnpj(dto.getCnpj());

        Company companyCorporateName = port.getByCorporateName(dto.getCorporateName());

        if(!companyOriginal.getId().equals(companyCnpj.getId())){
            details.add(
                    new Message.Details(
                            FieldConstants.CNPJ_CPF,
                            MessageConstants.CNPJ_CPF_JA_EXISTE_CADASTRADO,
                            companyCnpj.getCnpj()));
        }

        if(!companyOriginal.getId().equals(companyCorporateName.getId())){
            details.add(
                    new Message.Details(
                            FieldConstants.RAZAO_SOCIAL,
                            MessageConstants.RAZAO_SOCIAL_JA_EXISTE_CADASTRADA,
                            companyCorporateName.getCorporateName()));
        }

        if(!details.isEmpty())
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DA_EMPRESA,
                    details);

         */
    }

}
