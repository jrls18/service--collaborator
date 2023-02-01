package br.com.developcorporation.collaborator.core.service.impl;

import br.com.developcorporation.collaborator.core.enums.CoreEnum;
import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.core.validation.AuthorizationValidation;
import br.com.developcorporation.collaborator.core.validation.CollaboratorValidation;
import br.com.developcorporation.collaborator.domain.constants.FieldConstants;
import br.com.developcorporation.collaborator.domain.constants.MessageConstants;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.message.CollaboratorMessage;
import br.com.developcorporation.collaborator.domain.message.ConfigureMenuUser;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.domain.model.Pagination;
import br.com.developcorporation.collaborator.domain.model.Status;
import br.com.developcorporation.collaborator.domain.port.*;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
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

    private static final long AGUARDANDO_CONFIGURACAO_DE_MENU = 6L;

    private static final String ID_AGUARDANDO_CONFIGURACAO_DE_MENU = "6 - AGUARDANDO CONFIGURAÇÂO DE MENU";
    public static final long ID_TIPO_STATUS_ATIVO = 1L;
    public static final long IMAGE_PROFILE = 1L;
    public static final String INCLUSAO_ALTERACAO = "I";
    public static final String DELETE_FILE = "D";

    private final DocumentPort documentPort;

    private final PasswordEncoder encoder;
    private final CollaboratorPort port;

    private final CompanyPort companyPort;

    private final TypeCollaboratorPort typeCollaboratorPort;

    private final CollaboratorRolePort collaboratorRolePort;

    private final CollaboratorSendMessagePort collaboratorSendMessagePort;
    private final CollaboratorValidation validator;
    private final AuthorizationValidation validatorAuthorization;

    private final ConfigureMenuUserSendMessagePort configureMenuUserSendMessagePort;

    private final StatusPort statusPort;

    private final DocumentSendMessagePort documentSendMessagePort;

    @Value(value = "${quantidade.de.itens.na.paginacao}")
    private String qtdItems;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${toggle.call.api.documents}")
    private boolean toggleCallApiDocuments;

    private void save(Collaborator dto) {

        validExistsStatus(AGUARDANDO_CONFIGURACAO_DE_MENU);

        validator.add(dto);

        dto.setCpfCnpj(StringUtils.leftPad(dto.getCpfCnpj(),14,"0"));
        validAddExists(dto);

        try {
            dto.setDateRegister(LocalDateTime.now());

            Collaborator.Status status = new Collaborator.Status();
            status.setId(AGUARDANDO_CONFIGURACAO_DE_MENU);
            dto.setStatus(status);

            if(Objects.nonNull(dto.getDocument())){
                if(Objects.nonNull(dto.getDocument().getDocument())){
                    if(StringUtils.isEmpty(dto.getDocument().getNameDocument())){
                        dto.getDocument().setNameDocument(UUID.randomUUID().toString());
                    }
                    dto.getDocument().setCommand(INCLUSAO_ALTERACAO);
                }
            }

            Long id =  port.add(dto);
            dto.setId(id);

            collaboratorRolePort.save(id, dto.getTypeCollaborator().getId());

            if(Objects.nonNull(dto.getDocument()))
                if(dto.getDocument().getCommand().equals(INCLUSAO_ALTERACAO) && !StringUtils.isEmpty(dto.getDocument().getNameDocument()))
                    enviaDocuments(dto);

            configureMenuUserSendMessagePort.send(setConfigureMenuUser(dto));

        }catch (Exception ex){
            throw new DomainException(
                    CoreEnum.INTERNAL_SERVER_ERROR.getCode(),
                    MessageConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE,
                    null);
        }
    }

    private void enviaDocuments(Collaborator dto) {
        if(Objects.nonNull(dto.getDocument())){
            dto.getDocument().setLogo(false);
            dto.getDocument().setIdCatalago(IMAGE_PROFILE);
            documentSendMessagePort.send(dto);
        }
    }

    private ConfigureMenuUser setConfigureMenuUser(Collaborator dto) {
        ConfigureMenuUser configureMenuUser = new ConfigureMenuUser();
        configureMenuUser.setUser(new ConfigureMenuUser.User(dto.getId(),true));
        configureMenuUser.setMessageControl(new
                ConfigureMenuUser.MessageControl(
                        ContextHolder.get().getCorrelationId(),
                        LocalDateTime.now().toString(),
                        ContextHolder.get().getApplicationName(),
                        ID_AGUARDANDO_CONFIGURACAO_DE_MENU));
        return configureMenuUser;
    }


    @Transactional
    @Override
    public Message add(Collaborator dto) {
        validatorAuthorization.validCredentials();
        ContextHolder.get().setApplicationName(this.applicationName);

        dto.setPassword(encoder.encode(dto.getPassword()));

        dto.getDocument().setCommand(INCLUSAO_ALTERACAO);

        save(dto);

        return new Message(CoreEnum.CREATED.getCode(),
                MessageConstants.USUARIO_CADASTRODO_COM_SUCESSO);
    }

    @Override
    public void addAsync(Collaborator dto) {
        if(Objects.nonNull(dto)){
            if(StringUtils.isEmpty(dto.getOperationType()) || dto.getOperationType().equalsIgnoreCase("I"))
                save(dto);
            else
                updateAsync(dto);
        }
    }

    @Override
    public void unlockCollaboratorAsync(Collaborator collaborator) {
        if(Objects.nonNull(collaborator)){
            updateUnlock(collaborator);
        }
    }

    @SneakyThrows
    public void updateUnlock(Collaborator collaborator) {

        validExistsStatus(ID_TIPO_STATUS_ATIVO);

        try{
            Collaborator collaboratorExists = port.getById(collaborator.getId());

            if(Objects.isNull(collaboratorExists))
                throw new DomainException(
                        CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                        MessageConstants.CODIGO_COLABORADOR_INFORMADO_NAO_EXISTE_CADASTRADO,
                        null);

            port.updateStatus(collaborator.getId(), ID_TIPO_STATUS_ATIVO);

            //Envio de notificação para o cliente informando que está liberado seu usuario.
        }catch (Exception ex){
            log.error("Ops houve um erro inesperado no processo de desbloqueio do colaborador. Detalhes: " + ex.getMessage());
            throw new Exception(ex);
        }
    }




    private void updateAsync(final Collaborator domain){
        this.updateBase(domain);
    }

    private void updateBase(Collaborator domain){

        validator.update(domain);

        validUpdateExists(domain);

        ContextHolder.get().setApplicationName(this.applicationName);

        Collaborator dto = (Collaborator) ContextHolder.get().getMap().get("collaborator");

        domain.setPassword(dto.getPassword());
        domain.setIdCompany(dto.getIdCompany());

        domain.setCpfCnpj(StringUtils.leftPad(domain.getCpfCnpj(),14,"0"));

        domain.setDateRegister(dto.getDateRegister());

        domain.setStatus(dto.getStatus());

        try {

            if(Objects.nonNull(dto.getDocument())) {
                if (!StringUtils.isEmpty(dto.getDocument().getNameDocument())) {
                    domain.getDocument().setNameDocument(dto.getDocument().getNameDocument());
                }else {
                    domain.getDocument().setNameDocument(UUID.randomUUID().toString());
                }
                domain.getDocument().setCommand(INCLUSAO_ALTERACAO);
            }

            if(Objects.nonNull(dto.getDocument()) && Objects.nonNull(domain.getDocument())){
                if(!StringUtils.isEmpty(dto.getDocument().getNameDocument()) && Objects.isNull(domain.getDocument().getDocument())){
                    domain.getDocument().setNameDocument(null);
                    domain.getDocument().setCommand(DELETE_FILE);
                }
            }

            port.update(domain);

            if(StringUtils.isEmpty(domain.getDocument().getNameDocument()) && !StringUtils.isEmpty(dto.getDocument().getNameDocument()))
               domain.getDocument().setNameDocument(dto.getDocument().getNameDocument());

            if(!dto.getTypeCollaborator().getId().equals(domain.getTypeCollaborator().getId()))
                configureMenuUserSendMessagePort.send(setConfigureMenuUser(domain));

            enviaDocuments(domain);


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
    public void sendMessage(CollaboratorMessage collaboratorMessage) {
        if(Objects.nonNull(collaboratorMessage)){
            collaboratorSendMessagePort.send(collaboratorMessage);
        }
    }

    @Override
    public Optional<Collaborator> findByUsername(String username) {

        Optional<Collaborator> collaborator = port.findByUserName(username);

        if(collaborator.isPresent()){
            if(collaborator.get().getStatus().getId() == AGUARDANDO_CONFIGURACAO_DE_MENU){
                throw new DomainException(
                        CoreEnum.UNAUTHORIZED.getCode(),
                        MessageConstants.USUARIO_NAO_AUTORIZADO_AGUARDANDO_CONFIGURACAO_DE_MENU,
                        null);
            }
        }

        return collaborator;
    }

    @Override
    public Pagination<Collaborator> search(String searchTerm, int page, int size) {

        if(size == 0)
            size = Integer.parseInt(qtdItems);

        if(StringUtils.isEmpty(searchTerm))
            searchTerm = null;

        return port.search(searchTerm, page, size);
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

        if(Boolean.FALSE.equals(typeCollaboratorPort.getById(typeCollaborator.getId()))){
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
