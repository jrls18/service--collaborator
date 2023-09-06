package br.com.group.developer.corporation.service.collaborator.core.service.impl;

import br.com.group.developer.corporation.libparametrizador.schedule.ParameterizeService;
import br.com.group.developer.corporation.service.collaborator.core.service.CollaboratorService;
import br.com.group.developer.corporation.service.collaborator.core.validation.CollaboratorValidation;
import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.constants.MessageDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.constants.OtherDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.constants.ParametrizeConstants;
import br.com.group.developer.corporation.service.collaborator.domain.exception.CollaboratorErrorValidatorException;
import br.com.group.developer.corporation.service.collaborator.domain.exception.InternalServerErrorException;
import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import br.com.group.developer.corporation.service.collaborator.domain.model.Notification;
import br.com.group.developer.corporation.service.collaborator.domain.model.Status;
import br.com.group.developer.corporation.service.collaborator.domain.port.*;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import br.com.grupo.developer.corporation.libcommons.enums.CoreEnum;
import br.com.grupo.developer.corporation.libcommons.exception.DomainException;
import br.com.grupo.developer.corporation.libcommons.exception.UnauthorizedException;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.message.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private final StatusPort statusPort;

    private final DocumentSendMessagePort documentSendMessagePort;

    private final PushNotificationSendMessagePort pushNotificationSendMessagePort;
    
    private final ParameterizeService parameterizeService;

    private String uuidActiveProfile(){

        String uuid = null;
        int count = 0;

        while (count <= 10){
            uuid = UUID.randomUUID().toString();

            Collaborator collaborator =  port.getByIdActive(uuid);

            if(Objects.isNull(collaborator))
                break;

            count++;
        }

        return uuid;
    }
    
    private void produceDocument(final Collaborator domain) {
        boolean disablesCriticalKafkaContingency = Boolean.parseBoolean(parameterizeService.getPropertiesString(ParametrizeConstants.DISABLES_CRITICAL_KAFKA_CONTINGENCY));
        if(Boolean.FALSE.equals(disablesCriticalKafkaContingency)){
            boolean disablesKafkaContingency = Boolean.parseBoolean(parameterizeService.getPropertiesString(ParametrizeConstants.DISABLES_KAFKA_CONTINGENCY));
            if(Boolean.FALSE.equals(disablesKafkaContingency) &&
                    Objects.nonNull(domain.getDocument()) &&
                    Boolean.FALSE.equals(StringUtils.isEmpty(domain.getDocument().getCommand())) &&
                    domain.getDocument().getCommand().equals(OtherDomainConstants.SAVE) &&
                    Boolean.FALSE.equals(StringUtils.isEmpty(domain.getDocument().getNameDocument())) &&
                    domain.getDocument().getDocument().length > 0 ){
                domain.getDocument().setLogo(false);
                domain.getDocument().setIdCatalago(OtherDomainConstants.IMAGE_PROFILE);
                documentSendMessagePort.send(domain);
            }
        }
    }

    private void produceNotification(final Collaborator collaborator){
        boolean disablesCriticalKafkaContingency = Boolean.parseBoolean(parameterizeService.getPropertiesString(ParametrizeConstants.DISABLES_CRITICAL_KAFKA_CONTINGENCY));
        if(Boolean.FALSE.equals(disablesCriticalKafkaContingency)){
            boolean disablesKafkaContingency = Boolean.parseBoolean(parameterizeService.getPropertiesString(ParametrizeConstants.DISABLES_KAFKA_CONTINGENCY));
            if(Boolean.FALSE.equals(disablesKafkaContingency) && Objects.nonNull(collaborator)){

                Notification notification = new Notification();
                notification.setEmail(collaborator.getContact().getEmail());
                notification.setIdActive(collaborator.getIdActive());
                notification.setName(collaborator.getName());
                notification.setCellPhone(collaborator.getContact().getMainPhone());
                notification.setPassword(ContextHolder.get().getMap().get(FieldDomainConstants.PASSWORD).toString());
                notification.setMessage(null);
                notification.setTypeNotification(new Notification.TypeNotification(OtherDomainConstants.EMAIL));

                notification.setIdLayout(parameterizeService.getPropertiesString(ParametrizeConstants.ID_LAYOUT_EMAIL));
                pushNotificationSendMessagePort.send(notification);
            }
        }
    }

    @Override
    public Message add(Collaborator domain) {

        if(Objects.isNull(domain))
            throw new CollaboratorErrorValidatorException(CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.POR_FAVOR_INFORME_OS_DADOS_DO_COLABORADOR, null);

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
                Objects.nonNull(domain.getDocument().getDocument()) && domain.getDocument().getDocument().length > 0){
            domain.getDocument().setCommand(OtherDomainConstants.SAVE);
            if(StringUtils.isEmpty(domain.getDocument().getNameDocument()))
                domain.getDocument().setNameDocument(UUID.randomUUID().toString());
        }

        domain.setPassword(generatedPassword(domain.getPassword()));
        domain.setDateRegister(LocalDateTime.now());
        domain.setIdActive(uuidActiveProfile());

        try{
            Long id =  port.save(domain);
            domain.setId(id);

            collaboratorRolePort.save(id, domain.getTypeCollaborator().getId());

            produceNotification(domain);

            produceDocument(domain);

        }catch (Exception ex) {
            throw new InternalServerErrorException(
                    MessageDomainConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE);
        }

        return new Message(CoreEnum.CREATED.getCode(),
                MessageDomainConstants.USUARIO_CADASTRODO_COM_SUCESSO);
    }

    private String generatedPassword(String password) {
        if(StringUtils.isEmpty(password))
            password = "@#Ha1".concat(RandomStringUtils.randomAlphanumeric(8));

        ContextHolder.get().setMap(FieldDomainConstants.PASSWORD, password);
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

    @Override
    public Message update(Collaborator domain) {

        if(Objects.isNull(domain))
            throw new CollaboratorErrorValidatorException(CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.POR_FAVOR_INFORME_OS_DADOS_DO_COLABORADOR, null);

        validator.update(domain);

        validUpdateExists(domain);

        Collaborator dto = (Collaborator) ContextHolder.get().getMap().get(FieldDomainConstants.COLLABORATOR);

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

            if(Objects.nonNull(dto.getDocument()) && Objects.nonNull(domain.getDocument())
            && Boolean.FALSE.equals(StringUtils.isEmpty(dto.getDocument().getNameDocument()))
                    && Objects.isNull(domain.getDocument().getDocument())){
                domain.getDocument().setNameDocument(null);
                domain.getDocument().setCommand(OtherDomainConstants.DELETE_FILE);
            }

            port.save(domain);

            if(StringUtils.isEmpty(domain.getDocument().getNameDocument()) && !StringUtils.isEmpty(dto.getDocument().getNameDocument()))
                domain.getDocument().setNameDocument(dto.getDocument().getNameDocument());

            produceDocument(domain);

        }catch (Exception ex){
            throw new InternalServerErrorException(MessageDomainConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE);
        }

        return new Message(CoreEnum.ACCEPTED.getCode(),
                MessageDomainConstants.COLABORADOR_ATUALIZADA_COM_SUCESSO);
    }

    @Override
    public Collaborator getById(Long id) {

        Collaborator collaborator = port.getById(id);

        if(Objects.isNull(collaborator)){
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.COLLABORADOR_NAO_EXISTE_CADASTRO,
                    null);
        }

        boolean disablesCallApiDocumentContingency = Boolean.parseBoolean(parameterizeService.getPropertiesString(ParametrizeConstants.DISABLES_CALL_API_DOCUMENT_CONTINGENCY));

        if(Boolean.FALSE.equals(disablesCallApiDocumentContingency)){
            collaborator.getDocument().setDocument(documentPort.getImage(collaborator.getIdCompany(), collaborator.getDocument().getNameDocument()));
        }

        return  collaborator;
    }

    @Override
    public Collaborator getByIdNotImage(Long id) {

        Collaborator collaborator = port.getById(id);

        if(Objects.isNull(collaborator)){
            throw new DomainException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.COLLABORADOR_NAO_EXISTE_CADASTRO,
                    null);
        }

        return  collaborator;
    }

    @Override
    public Optional<Collaborator> findByUsername(String username) {

        Optional<Collaborator> collaborator = port.findByUserName(username);

        if(collaborator.isPresent()){
            boolean disablesCriticalKafkaContingency = Boolean.parseBoolean(parameterizeService.getPropertiesString(ParametrizeConstants.DISABLES_CRITICAL_KAFKA_CONTINGENCY));
            if(Boolean.FALSE.equals(disablesCriticalKafkaContingency)){
                boolean disablesKafkaContingency = Boolean.parseBoolean(parameterizeService.getPropertiesString(ParametrizeConstants.DISABLES_KAFKA_CONTINGENCY));
                if(Boolean.FALSE.equals(disablesKafkaContingency) && collaborator.get().getStatus().getId() == OtherDomainConstants.PENDENTE_VALIDACAO_DE_EMAIL){
                    throw new UnauthorizedException(MessageDomainConstants.USUARIO_NAO_AUTORIZADO_AGUARDANDO_VALIDACAO_DE_EMAIL_VERIFIQUEI_CAIXA_DE_ENTRADA_E_SPAN);
                }
            }
            port.updateDateTimeLastAccess(collaborator.get().getId(), LocalDateTime.now());
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
                    MessageDomainConstants.CAHVE_DE_AUTENTICACAO_DE_EMAIL_INVALIDA,
                    null);

        port.updateStatus(collaborator.getId(), OtherDomainConstants.ATIVO);

        return new Message(CoreEnum.ACCEPTED.getCode(),
                MessageDomainConstants.ATIVACAO_DO_PERFIL_REALIZADO_COM_SUCESSO);
    }

    @Override
    public Message recoverPassword(String username) {

        if(StringUtils.isEmpty(username))
            throw new DomainException(CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.USERNAME_E_OBRIGATORIO,
                    null);

        Optional<Collaborator> collaborator = port.findByUserName(username);

        if(collaborator.isEmpty())
            throw new DomainException(CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.USERNAME_INFORMADO_NAO_EXISTE_CADASTRADO,
                    null);

        port.recoverPassword(generatedPassword(null), collaborator.get().getId());

        produceNotification(collaborator.get());

        return new Message(CoreEnum.ACCEPTED.getCode(),
                LocalDateTime.now().toString(),
                MessageDomainConstants.USERNAME_ALTERADO_COM_SUCESSO,
                null);
    }

    @Override
    public Pagination<Collaborator> search(String searchTerm, String codEmpresa, int page, int size) {

        if(size == 0){
            String sizePage = parameterizeService.getPropertiesString(ParametrizeConstants.SIZE_PAGE);
            size = Integer.parseInt(sizePage);
        }

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
                            FieldDomainConstants.EMAIL,
                            MessageDomainConstants.EMAIL_INFORMADO_JA_EXISTE_CADASTRADO,
                            dto.getContact().getEmail()));
        }

        details.addAll(validExistsIdCompany(dto.getIdCompany()));

        details.addAll(validTypeCollaborator(dto.getTypeCollaborator()));


        if(!details.isEmpty())
            throw new CollaboratorErrorValidatorException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR,
                    details);


    }

    private void validExistsStatus(Long idStatus){
        List<Message.Details> details = new ArrayList<>();

        Status status = statusPort.getById(idStatus);

        if(Objects.isNull(status)){
            details.add(
                    new Message.Details(
                            FieldDomainConstants.CODIGO,
                            MessageDomainConstants.CODIGO_DA_SITUACAO_NAO_EXISTE_CADASTRADO,
                            idStatus.toString()));
        }

        if(!details.isEmpty())
            throw new CollaboratorErrorValidatorException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR,
                    details);
    }

    public List<Message.Details> validTypeCollaborator(final Collaborator.TypeCollaborator typeCollaborator){
        List<Message.Details> detailsList = new ArrayList<>();

        if(Objects.isNull(typeCollaboratorPort.getById(typeCollaborator.getId()))){
            detailsList.add(
                    new Message.Details(
                            FieldDomainConstants.TIPO_COLABORATOR,
                            MessageDomainConstants.TIPO_DE_COLABORADOR_INVALIDO,
                            typeCollaborator.getId().toString()));
        }

        return detailsList;
    }

    private List<Message.Details> validExistsIdCompany(final String idCompany){
        List<Message.Details> details = new ArrayList<>();

        if(Boolean.FALSE.equals(port.existsCompany(idCompany))){

           boolean disablesCallApiCompanyContingency = Boolean.parseBoolean(parameterizeService.getPropertiesString(ParametrizeConstants.DISABLES_CALL_API_COMPANY_CONTINGENCY));

           if(Boolean.FALSE.equals(disablesCallApiCompanyContingency) && Objects.isNull(companyPort.findByCompanyId(Long.parseLong(idCompany)))){
               details.add(
                           new Message.Details(
                                   FieldDomainConstants.ID_COMPANY,
                                   MessageDomainConstants.CODIGO_EMPRESA_INFORMADO_NAO_EXISTE_CADASTRADO,
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
                            FieldDomainConstants.CODIGO,
                            MessageDomainConstants.CODIGO_COLABORADOR_INFORMADO_NAO_EXISTE_CADASTRADO,
                            dto.getId().toString()));
        }else{
            Collaborator collaborator =  port.getEmail(dto.getContact().getEmail());

            if(Objects.nonNull(collaborator) && Boolean.FALSE.equals(collaboratorOriginal.getId().equals(collaborator.getId()))){
                details.add(
                        new Message.Details(
                                FieldDomainConstants.EMAIL,
                                MessageDomainConstants.EMAIL_INFORMADO_JA_EXISTE_CADASTRADO,
                                collaborator.getContact().getEmail()));
            }
        }

        details.addAll(validTypeCollaborator(dto.getTypeCollaborator()));

        if(!details.isEmpty())
            throw new CollaboratorErrorValidatorException(
                    CoreEnum.UNPROCESSABLE_ENTITY.getCode(),
                    MessageDomainConstants.EXISTE_ERROS_NOS_CAMPOS_DO_COLABORADOR,
                    details);

        ContextHolder.get().setMap(FieldDomainConstants.COLLABORATOR, collaboratorOriginal);
    }
}
