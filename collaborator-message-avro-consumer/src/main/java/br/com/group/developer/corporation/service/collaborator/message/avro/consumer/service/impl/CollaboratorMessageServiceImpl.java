package br.com.group.developer.corporation.service.collaborator.message.avro.consumer.service.impl;

import br.com.group.developer.corporation.lib.logger.logger.LoggerAsynchronous;
import br.com.group.developer.corporation.libparametrizador.schedule.ParameterizeService;
import br.com.group.developer.corporation.service.collaborator.core.service.CollaboratorService;
import br.com.group.developer.corporation.service.collaborator.domain.constants.OtherDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.constants.ParametrizeConstants;
import br.com.group.developer.corporation.service.collaborator.domain.exception.BusinessErrorException;
import br.com.group.developer.corporation.service.collaborator.domain.exception.CollaboratorErrorValidatorException;
import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import br.com.group.developer.corporation.service.collaborator.domain.model.Notification;
import br.com.group.developer.corporation.service.collaborator.domain.port.PushNotificationSendMessagePort;
import br.com.group.developer.corporation.service.collaborator.message.avro.consumer.mapper.CollaboratorMessageMapper;
import br.com.group.developer.corporation.service.collaborator.message.avro.consumer.service.CollaboratorMessageService;
import br.com.group.developer.corporation.service.collaborator.message.avro.consumer.service.ContextService;
import br.com.grupo.developer.corporation.libcommons.enums.CoreEnum;
import br.com.grupo.developer.corporation.libcommons.exception.DomainException;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;
import br.com.grupo.developer.corporation.msg.avro.collaborator.CollaboratorAsync;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Service
public class CollaboratorMessageServiceImpl implements CollaboratorMessageService<CollaboratorAsync> {

    private static final String ACCEPT = "ACCEPT";

    private final CollaboratorService collaboratorService;

    private final PushNotificationSendMessagePort pushNotificationSendMessagePort;

    private final ContextService contextService;

    private final LoggerAsynchronous logger;

    private final ParameterizeService parameterizeService;

    @Value("${spring.application.name}")
    private String applicationName; //NOSONAR

    @RetryableTopic(
            attempts = "${kafka.topic.consumer.qtdRetry}",
            backoff = @Backoff(delay = 60000, multiplier = 1.0, maxDelay = 120000),
            autoCreateTopics = "false"
    )
    @KafkaListener(autoStartup = "${ASYNC}", topics = "${kafka.topic.consumer.collaborator.name}", groupId = "${kafka.topic.consumer.collaborator.id}")
    @Override
    public void onReceive(ConsumerRecord<String, CollaboratorAsync> record) { //NOSONAR

        MessageAsync<Collaborator> message = CollaboratorMessageMapper.INSTANCE.toDomain(record.value());

        message.setDateTimeStartProcessing(LocalDateTime.now().toString());

        contextService.context(this.applicationName,
                message, record.topic(), false);

        logger.info(message.getObj());

        try {
            collaboratorService.saveAsync(message.getObj());

            message.setDateTimeEndProcessing(LocalDateTime.now().toString());

            contextService.context(this.applicationName,
                    message, record.topic(), true);

            logger.info(message.getObj(), ACCEPT);

        } catch (CollaboratorErrorValidatorException | BusinessErrorException ex) {

           final String idLayoutEmailFailBusiness = parameterizeService.getPropertiesString(ParametrizeConstants.ID_LAYOUT_EMAIL_FAIL_BUSINESS);

            assert ex instanceof DomainException;
            produceFailNotification(message.getObj(),  (DomainException) ex, idLayoutEmailFailBusiness);

            contextService.context(this.applicationName,
                    message, record.topic(), true);

            logger.warning(message.getObj(), CoreEnum.UNPROCESSABLE_ENTITY.name());
        }
        contextService.remove();
    }


    private void produceFailNotification(final Collaborator collaborator, final DomainException ex,
                                         final String idLayout){
        if(Objects.nonNull(collaborator)){

            Notification notification = new Notification();
            notification.setEmail(collaborator.getContact().getEmail());
            notification.setIdActive(null);
            notification.setPassword(null);
            notification.setName(collaborator.getName());
            notification.setCellPhone(collaborator.getContact().getMainPhone());

            if(Objects.nonNull(ex)){
                Message message = new Message();
                message.setCode(ex.getCode());
                message.setMessage(ex.getMessage());
                message.setDetailsList(ex.getDetails());

                notification.setMessage(message);
            }

            notification.setTypeNotification(new Notification.TypeNotification(OtherDomainConstants.EMAIL));
            notification.setIdLayout(idLayout);

            pushNotificationSendMessagePort.send(notification);
        }
    }
}
