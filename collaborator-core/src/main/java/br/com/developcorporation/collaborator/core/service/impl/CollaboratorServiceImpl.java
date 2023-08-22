package br.com.developcorporation.collaborator.core.service.impl;

import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.core.validation.AuthorizationValidation;
import br.com.developcorporation.collaborator.core.validation.CollaboratorValidation;
import br.com.developcorporation.collaborator.domain.constants.FieldConstants;
import br.com.developcorporation.collaborator.domain.constants.MessageConstants;
import br.com.developcorporation.collaborator.domain.constants.OtherDomainConstants;
import br.com.developcorporation.collaborator.domain.message.Notification;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.domain.model.Status;
import br.com.developcorporation.collaborator.domain.port.*;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import br.com.grupo.developer.corporation.libcommons.enums.CoreEnum;
import br.com.grupo.developer.corporation.libcommons.exception.DomainException;
import br.com.grupo.developer.corporation.libcommons.exception.UnauthorizedException;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;
import br.com.grupo.developer.corporation.libcommons.message.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@RequiredArgsConstructor
@Service
public class CollaboratorServiceImpl implements CollaboratorService {

    private final DocumentPort documentPort;

    private final PasswordEncoder encoder;

    private final CollaboratorPort port;

    private final CompanyPort companyPort;

    private final TypeCollaboratorPort typeCollaboratorPort;

    private final CollaboratorRolePort collaboratorRolePort;

    private final CollaboratorValidation validator;

    private final AuthorizationValidation validatorAuthorization;

    private final StatusPort statusPort;

    private final DocumentSendMessagePort documentSendMessagePort;

    private final PushNotificationSendMessagePort pushNotificationSendMessagePort;

    @Value(value = "${quantidade.de.itens.na.paginacao}")
    private String qtdItems;

    @Value("${toggle.call.api.documents}")
    private boolean toggleCallApiDocuments;

    private String uuidActiveProfile(){

        Integer[] index = {0,1,2,3,4,5,6,7,8,9};

        String uuid = null;

        for (Integer ignored : index) {
             uuid = UUID.randomUUID().toString();

             Collaborator collaborator =  port.getByIdActive(uuid);

             if(Objects.isNull(collaborator))
                 break;
        }
        return uuid;
    }

    private MessageAsync<Notification> setNotificationMessageAsync(Collaborator dto) {
        MessageAsync<Notification> messageAsync = new MessageAsync<>();
        messageAsync.setCorrelationId(ContextHolder.get().getCorrelationId());
        messageAsync.setOriginSystem(ContextHolder.get().getApplicationName());
        messageAsync.setPostDateTime(LocalDateTime.now().toString());
        messageAsync.setObj(new Notification(
                dto.getId().toString(),
                dto.getContact().getEmail(),dto.getName(),
                dto.getContact().getMainPhone(),
                dto.getIdActive()
                ,new Notification.TypeNotification(OtherDomainConstants.EMAIL),
                OtherDomainConstants.ID_LAYOUT_NEW_COLLABORATOR));
        return messageAsync;
    }

    private void envDoc(Collaborator domain) {
        if(Objects.nonNull(domain.getDocument()) &&
                domain.getDocument().getCommand().equals(OtherDomainConstants.SAVE) &&
                !StringUtils.isEmpty(domain.getDocument().getNameDocument())){
            domain.getDocument().setLogo(false);
            domain.getDocument().setIdCatalago(OtherDomainConstants.IMAGE_PROFILE);
            documentSendMessagePort.send(domain);
        }
    }


    @Transactional
    @Override
    public Message add(Collaborator domain) {

        if(Objects.isNull(domain))
            throw new DomainException(CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.POR_FAVOR_INFORME_OS_DADOS_DO_COLABORADOR, null);

        validExistsStatus(OtherDomainConstants.PENDENTE_VALIDACAO_DE_EMAIL);

        validator.add(domain);

        domain.setCpfCnpj(StringUtils.leftPad(domain.getCpfCnpj(),14,"0"));

        if(Objects.nonNull(domain.getContact()) &&
                Boolean.FALSE.equals(StringUtils.isEmpty(domain.getContact().getEmail())))
            domain.getContact().setEmail(domain.getContact().getEmail().trim());

        validAddExists(domain);

        Collaborator.Status status = new Collaborator.Status();
        status.setId(OtherDomainConstants.PENDENTE_VALIDACAO_DE_EMAIL);
        domain.setStatus(status);

        if(Objects.nonNull(domain.getDocument()) &&
                Objects.nonNull(domain.getDocument().getDocument())){
            domain.getDocument().setCommand(OtherDomainConstants.SAVE);
            if(StringUtils.isEmpty(domain.getDocument().getNameDocument()))
                domain.getDocument().setNameDocument(UUID.randomUUID().toString());
        }

        domain.setPassword(generatedPassword(domain.getPassword()));
        domain.setDateRegister(LocalDateTime.now());
        domain.setIdActive(uuidActiveProfile());

        try{
            Long id =  port.add(domain);
            domain.setId(id);

            collaboratorRolePort.save(id, domain.getTypeCollaborator().getId());

            pushNotificationSendMessagePort.send(setNotificationMessageAsync(domain));

            envDoc(domain);

        }catch (Exception ex) {
            throw new DomainException(
                    CoreEnum.INTERNAL_SERVER_ERROR.getCode(),
                    MessageConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE,
                    null);
        }

        return new Message(CoreEnum.CREATED.getCode(),
                MessageConstants.USUARIO_CADASTRODO_COM_SUCESSO);
    }

    private String generatedPassword(String password) {
        if(StringUtils.isEmpty(password))
            password = "@#Ha1".concat(RandomStringUtils.randomAlphanumeric(8));
        return encoder.encode(password);
    }



    @Override
    public void saveAsync(Collaborator dto) {
        if(Objects.nonNull(dto)){
            if(StringUtils.isEmpty(dto.getOperationType()) ||
                    dto.getOperationType().equalsIgnoreCase("I"))
                add(dto);
            else
                update(dto);
        }
    }

    @Transactional
    @Override
    public Message update(Collaborator domain) {

        if(Objects.isNull(domain))
            throw new DomainException(CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.POR_FAVOR_INFORME_OS_DADOS_DO_COLABORADOR, null);

        validator.update(domain);

        validUpdateExists(domain);

        Collaborator dto = (Collaborator) ContextHolder.get().getMap().get("collaborator");

        domain.setPassword(dto.getPassword());
        domain.setIdCompany(dto.getIdCompany());
        domain.setDateRegister(dto.getDateRegister());
        domain.setStatus(dto.getStatus());

        domain.setCpfCnpj(StringUtils.leftPad(domain.getCpfCnpj(),14,"0"));

        try {

            if(Objects.nonNull(dto.getDocument())) {
                if (!StringUtils.isEmpty(dto.getDocument().getNameDocument())) {
                    domain.getDocument().setNameDocument(dto.getDocument().getNameDocument());
                }else {
                    domain.getDocument().setNameDocument(UUID.randomUUID().toString());
                }
                domain.getDocument().setCommand(OtherDomainConstants.SAVE);
            }

            if(Objects.nonNull(dto.getDocument()) && Objects.nonNull(domain.getDocument())){
                if(!StringUtils.isEmpty(dto.getDocument().getNameDocument()) && Objects.isNull(domain.getDocument().getDocument())){
                    domain.getDocument().setNameDocument(null);
                    domain.getDocument().setCommand(OtherDomainConstants.DELETE_FILE);
                }
            }

            port.update(domain);

            if(StringUtils.isEmpty(domain.getDocument().getNameDocument()) && !StringUtils.isEmpty(dto.getDocument().getNameDocument()))
                domain.getDocument().setNameDocument(dto.getDocument().getNameDocument());

            envDoc(domain);

        }catch (Exception ex){
            throw new DomainException(
                    CoreEnum.INTERNAL_SERVER_ERROR.getCode(),
                    MessageConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE,
                    null);
        }

        return new Message(CoreEnum.ACCEPTED.getCode(),
                MessageConstants.COLABORADOR_ATUALIZADA_COM_SUCESSO);
    }

    @Override
    public Collaborator getById(Long id) {
        validatorAuthorization.validCredentials();

        Collaborator collaborator = port.getById(id);

        if(Objects.isNull(collaborator)){
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.COLLABORADOR_NAO_EXISTE_CADASTRO,
                    null);
        }

        if(toggleCallApiDocuments){
            collaborator.getDocument().setDocument(documentPort.getImage(collaborator.getIdCompany(), collaborator.getDocument().getNameDocument()));
        }

        return  collaborator;
    }

    @Override
    public Collaborator getByIdNotImage(Long id) {
        validatorAuthorization.validCredentials();

        Collaborator collaborator = port.getById(id);

        if(Objects.isNull(collaborator)){
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.COLLABORADOR_NAO_EXISTE_CADASTRO,
                    null);
        }

        return  collaborator;
    }



    @Override
    public Optional<Collaborator> findByUsername(String username) {

        Optional<Collaborator> collaborator = port.findByUserName(username);

        if(collaborator.isPresent()){
            if(collaborator.get().getStatus().getId() == OtherDomainConstants.PENDENTE_VALIDACAO_DE_EMAIL){
                throw new UnauthorizedException(MessageConstants.USUARIO_NAO_AUTORIZADO_AGUARDANDO_CONFIGURACAO_DE_MENU);
            }
        }

        return collaborator;
    }

    @Override
    public Message profileActivation(String uuid) {

        validator.validUuid(uuid);

        validExistsStatus(OtherDomainConstants.ATIVO);

        Collaborator collaborator = port.getByIdActive(uuid);

        if(Objects.isNull(collaborator))
            throw new DomainException(CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.CAHVE_DE_AUTENTICACAO_DE_EMAIL_INVALIDA,
                    null);

        port.updateStatus(collaborator.getId(), OtherDomainConstants.ATIVO);

        return new Message(CoreEnum.ACCEPTED.getCode(),
                MessageConstants.ATIVACAO_DO_PERFIL_REALIZADO_COM_SUCESSO);
    }

    @Override
    public Pagination<Collaborator> search(String searchTerm, String codEmpresa, int page, int size) {

        if(size == 0)
            size = Integer.parseInt(qtdItems);

        if(StringUtils.isEmpty(searchTerm))
            searchTerm = null;

        return port.search(searchTerm, codEmpresa, page, size);
    }


    private void validAddExists(Collaborator dto){
        List<Message.Details> details = new ArrayList<>();

        Collaborator collaborator =  port.getEmail(dto.getContact().getEmail());

        if(Objects.nonNull(collaborator)){
            details.add(
                    new Message.Details(
                            FieldConstants.EMAIL,
                            MessageConstants.EMAIL_INFORMADO_JA_EXISTE_CADASTRADO,
                            dto.getContact().getEmail()));
        }

        details.addAll(validExistsIdCompany(dto.getIdCompany()));

        details.addAll(validTypeCollaborator(dto.getTypeCollaborator()));

        if(!details.isEmpty())
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DO_USUARIO,
                    details);


    }

    private void validExistsStatus(Long idStatus){
        List<Message.Details> details = new ArrayList<>();

        Status status = statusPort.getById(idStatus);

        if(Objects.isNull(status)){
            details.add(
                    new Message.Details(
                            FieldConstants.CODIGO,
                            MessageConstants.CODIGO_DA_SITUACAO_NAO_EXISTE_CADASTRADO,
                            idStatus.toString()));
        }

        if(!details.isEmpty())
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DO_USUARIO,
                    details);
    }

    public List<Message.Details> validTypeCollaborator(final Collaborator.TypeCollaborator typeCollaborator){
        List<Message.Details> detailsList = new ArrayList<>();

        if(Objects.isNull(typeCollaboratorPort.getById(typeCollaborator.getId()))){
            detailsList.add(
                    new Message.Details(
                            FieldConstants.TIPO_COLABORATOR,
                            MessageConstants.TIPO_DE_COLABORADOR_INVALIDO,
                            typeCollaborator.getId().toString()));
        }

        return detailsList;
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

        if(Objects.isNull(collaboratorOriginal)){
            details.add(
                    new Message.Details(
                            FieldConstants.CODIGO,
                            MessageConstants.CODIGO_COLABORADOR_INFORMADO_NAO_EXISTE_CADASTRADO,
                            dto.getId().toString()));
        }else{
            Collaborator collaborator =  port.getEmail(dto.getContact().getEmail());

            if(Objects.nonNull(collaborator)){
                if(!collaboratorOriginal.getId().equals(collaborator.getId())){
                    details.add(
                            new Message.Details(
                                    FieldConstants.EMAIL,
                                    MessageConstants.EMAIL_INFORMADO_JA_EXISTE_CADASTRADO,
                                    collaborator.getContact().getEmail()));
                }
            }
        }

        details.addAll(validTypeCollaborator(dto.getTypeCollaborator()));

        if(!details.isEmpty())
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageConstants.EXISTE_ERROS_NOS_CAMPOS_DO_USUARIO,
                    details);

        ContextHolder.get().setMap("collaborator", collaboratorOriginal);
    }
}
