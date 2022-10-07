package br.com.developcorporation.collaborator.core.service.impl;

import br.com.developcorporation.collaborator.core.enums.CoreEnum;
import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.core.validation.AuthorizationValidation;
import br.com.developcorporation.collaborator.core.validation.CollaboratorValidation;
import br.com.developcorporation.collaborator.domain.constants.FieldConstants;
import br.com.developcorporation.collaborator.domain.constants.MessageConstants;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.infrastructure.ContextHolder;
import br.com.developcorporation.collaborator.domain.message.CollaboratorMessage;
import br.com.developcorporation.collaborator.domain.message.ConfigureMenuUser;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.domain.model.Pagination;
import br.com.developcorporation.collaborator.domain.port.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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

    private static final long AGUARDANDO_CONFIGURACAO_DE_MENU = 6L;

    private static final String ID_AGUARDANDO_CONFIGURACAO_DE_MENU = "6 - AGUARDANDO CONFIGURAÇÂO DE MENU";

    private final PasswordEncoder encoder;
    private final CollaboratorPort port;

    private final CompanyPort companyPort;

    private final TypeCollaboratorPort typeCollaboratorPort;

    private final CollaboratorRolePort collaboratorRolePort;

    private final CollaboratorSendMessagePort collaboratorSendMessagePort;
    private final CollaboratorValidation validator;
    private final AuthorizationValidation validatorAuthorization;

    private final ConfigureMenuUserSendMessagePort configureMenuUserSendMessagePort;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value(value = "${quantidade.de.itens.na.paginacao}")
    private String qtdItems;



    private void save(Collaborator dto) {

        validator.add(dto);

        dto.setCpfCnpj(StringUtils.leftPad(dto.getCpfCnpj(),14,"0"));
        validAddExists(dto);

        try {
            dto.setDateRegister(LocalDateTime.now());

            Collaborator.Status status = new Collaborator.Status();
            status.setId(AGUARDANDO_CONFIGURACAO_DE_MENU);
            dto.setStatus(status);

            Long id =  port.add(dto);
            dto.setId(id);

            collaboratorRolePort.save(id, dto.getTypeCollaborator().getId());

            //Envia documento do cliente
            //messagePort.send(dto);

            configureMenuUserSendMessagePort.send(setConfigureMenuUser(dto));

        }catch (Exception ex){
            throw new DomainException(
                    CoreEnum.INTERNAL_SERVER_ERROR.getCode(),
                    MessageConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE,
                    null);
        }
    }

    private ConfigureMenuUser setConfigureMenuUser(Collaborator dto) {
        ConfigureMenuUser configureMenuUser = new ConfigureMenuUser();
        configureMenuUser.setUser(new ConfigureMenuUser.User(dto.getId(),true));
        configureMenuUser.setMessageControl(new
                ConfigureMenuUser.MessageControl(
                        ContextHolder.get().getCorrelationId(),
                        LocalDateTime.now().toString(),
                        applicationName,
                        ID_AGUARDANDO_CONFIGURACAO_DE_MENU));
        return configureMenuUser;
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
        this.updateBase(domain);
    }

    private void updateBase(Collaborator domain){
        validator.update(domain);

        Collaborator dto = port.getById(domain.getId());
        domain.setPassword(dto.getPassword());

        domain.setCpfCnpj(StringUtils.leftPad(domain.getCpfCnpj(),14,"0"));

        domain.setDateRegister(dto.getDateRegister());

        domain.setStatus(dto.getStatus());

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

        return  port.getById(id);
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
    }

}
