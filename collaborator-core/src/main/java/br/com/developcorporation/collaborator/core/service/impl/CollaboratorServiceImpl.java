package br.com.developcorporation.collaborator.core.service.impl;


import br.com.developcorporation.collaborator.core.enums.CoreEnum;
import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.core.validation.AuthorizationValidation;
import br.com.developcorporation.collaborator.core.validation.CollaboratorValidation;
import br.com.developcorporation.collaborator.domain.constants.FieldConstants;
import br.com.developcorporation.collaborator.domain.constants.MessageConstants;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.domain.port.CollaboratorPort;
import br.com.developcorporation.collaborator.domain.port.CollaboratorSendMessageErrorPort;
import br.com.developcorporation.collaborator.domain.port.CompanyPort;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CollaboratorServiceImpl implements CollaboratorService {

    public static final long ACTIVE = 1L;

    private final PasswordEncoder encoder;
    private final CollaboratorPort port;

    private final CompanyPort companyPort;

    //private final SendMessagePort messagePort;

    private final CollaboratorSendMessageErrorPort collaboratorSendMessageErrorPort;
    private final CollaboratorValidation validator;
    private final AuthorizationValidation validatorAuthorization;


    private void save(Collaborator dto) {

        validator.add(dto);

        dto.setCpfCnpj(StringUtils.leftPad(dto.getCpfCnpj(),14,"0"));
        validAddExists(dto);

        try {
            dto.setDateRegister(LocalDateTime.now());

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
    }

    @Transactional
    @Override
    public Message add(Collaborator dto) {
        validatorAuthorization.validCredentials();

        dto.setPassword(encoder.encode(dto.getPassword()));

        save(dto);

        return new Message(CoreEnum.CREATED.getCode(),
                MessageConstants.USUARIO_CADASTRODO_COM_SUCESSO);
    }

    @Transactional
    @Override
    public void addAsync(Collaborator dto) {
        if(Objects.nonNull(dto)){
            if(StringUtils.isEmpty(dto.getOperationType()) || dto.getOperationType().equalsIgnoreCase("I"))
                save(dto);
            else
                updateAsync(dto);
        }
    }


    private void updateAsync(final Collaborator domain){
       try{
           this.updateBase(domain);
       }catch (DomainException exception){
           collaboratorSendMessageErrorPort.send(exception);
       }
    }

    private void updateBase(Collaborator domain){
        validator.update(domain);

        domain.setCpfCnpj(StringUtils.leftPad(domain.getCpfCnpj(),14,"0"));
        validUpdateExists(domain);

        try {
            port.update(domain);
        }catch (Exception ex){
            throw new DomainException(
                    CoreEnum.INTERNAL_SERVER_ERROR.getCode(),
                    MessageConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE,
                    null);
        }
    }

    @Transactional
    @Override
    public Message update(Collaborator dto) {

        validatorAuthorization.validCredentials();

        validator.update(dto);

        this.updateBase(dto);

        return new Message(CoreEnum.ACCEPTED.getCode(),
                MessageConstants.COLABORADOR_ATUALIZADA_COM_SUCESSO);
    }



    @Override
    public Collaborator getById(Long id) {
        validatorAuthorization.validCredentials();
        return port.getById(id);
    }

    @Override
    public void sendMessageError(DomainException domainException) {
        if(Objects.nonNull(domainException)){
            collaboratorSendMessageErrorPort.send(domainException);
        }
    }

    @Override
    public Optional<Collaborator> findByUsername(String username) {
        return port.findByUserName(username);
    }








    private void validAddExists(Collaborator dto){
        List<Message.Details> details = new ArrayList<>();

        Collaborator collaborator =  port.getEmail(dto.getContact().getEmail());

        if(Objects.nonNull(collaborator)){
            details.add(
                    new Message.Details(
                            FieldConstants.EMAIL,
                            MessageConstants.EMAIL_DEVE_SER_ENVIADO_NO_FORMATO_VALIDO,
                            dto.getContact().getEmail()));
        }

        details.addAll(validExistsIdCompany(dto.getIdCompany()));

        if(!details.isEmpty())
            throw new DomainException(
                CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DO_USUARIO,
                details);


    }

    private List<Message.Details> validExistsIdCompany(final String idCompany){
        List<Message.Details> details = new ArrayList<>();

       if(Boolean.FALSE.equals(port.existeEmpresa(idCompany))){

         if(Objects.isNull(companyPort.consultaPorId(Long.parseLong(idCompany)))) {
             details.add(
                     new Message.Details(
                             FieldConstants.ID_COMPANY,
                             MessageConstants.CODIGO_EMPRESA_INFORMADO_NAO_EXISTE_CADASTRADO,
                             idCompany));
         }
       }
       return details;
    }

    private void validUpdateExists(Collaborator dto) {

        List<Message.Details> details = new ArrayList<>();

        Collaborator collaboratorOriginal = port.getById(dto.getId());

        Collaborator collaborator =  port.getEmail(dto.getContact().getEmail());

        if(!collaboratorOriginal.getId().equals(collaborator.getId())){
            details.add(
                    new Message.Details(
                            FieldConstants.EMAIL,
                            MessageConstants.EMAIL_INFORMADO_JA_EXISTE_CADASTRADO,
                            collaborator.getContact().getEmail()));
        }

        if(!details.isEmpty())
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DO_USUARIO,
                    details);
    }

}
