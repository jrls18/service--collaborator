package br.com.group.developer.corporation.service.collaborator.message.avro.consumer.service.impl;

import br.com.group.developer.corporation.lib.logger.logger.LoggerAsynchronous;
import br.com.group.developer.corporation.libparametrizador.schedule.ParameterizeService;
import br.com.group.developer.corporation.service.collaborator.domain.constants.OtherDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.constants.ParametrizeConstants;
import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import br.com.group.developer.corporation.service.collaborator.domain.model.Notification;
import br.com.group.developer.corporation.service.collaborator.domain.port.PushNotificationSendMessagePort;
import br.com.group.developer.corporation.service.collaborator.message.avro.consumer.mapper.CollaboratorMessageMapper;
import br.com.group.developer.corporation.service.collaborator.message.avro.consumer.service.CollaboratorMessageDltService;
import br.com.group.developer.corporation.service.collaborator.message.avro.consumer.service.ContextService;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;
import br.com.grupo.developer.corporation.msg.avro.collaborator.CollaboratorAsync;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Service
public class CollaboratorMessageDltServiceImpl implements CollaboratorMessageDltService<CollaboratorAsync> {

    public static final String SUPPORT = "SUPPORT";

    private final PushNotificationSendMessagePort pushNotificationSendMessagePort;

    private final ContextService contextService;

    private final LoggerAsynchronous logger;

    private final ParameterizeService parameterizeService;

    @Value("${spring.application.name}")
    private String applicationName; //NOSONAR

    @KafkaListener(autoStartup = "${ASYNC}" ,topics = "${kafka.topic.consumer.collaborator.name}-dlt", groupId = "${kafka.topic.consumer.collaborator.id}")
    public void onReceive(ConsumerRecord<String, CollaboratorAsync> record) { //NOSONAR

        MessageAsync<Collaborator> message = CollaboratorMessageMapper.INSTANCE.toDomain(record.value());

        message.setDateTimeStartProcessing(LocalDateTime.now().toString());

        contextService.context(this.applicationName,
                message, record.topic(), false);

        logger.info(message.getObj());

        final String idLayoutSendMessageClientAndSupport = parameterizeService.getPropertiesString(ParametrizeConstants.ID_LAYOUT_SEND_MESSAGE_CLIENT_AND_SUPPORT);

        produceFailNotification(message.getObj(),idLayoutSendMessageClientAndSupport);

        message.setDateTimeEndProcessing(LocalDateTime.now().toString());

        contextService.context(this.applicationName,
                message, record.topic(), true);

        logger.warning(message.getObj(), SUPPORT);

        contextService.remove();
    }

    private void produceFailNotification(final Collaborator collaborator,
                                         final String idLayout){
        if(Objects.nonNull(collaborator)){

            Notification notification = new Notification();
            notification.setEmail(collaborator.getContact().getEmail());
            notification.setName(collaborator.getName());
            notification.setCellPhone(collaborator.getContact().getMainPhone());
            notification.setMessage(null);
            notification.setIdActive(null);
            notification.setPassword(null);

            notification.setTypeNotification(new Notification.TypeNotification(OtherDomainConstants.EMAIL));
            notification.setIdLayout(idLayout);

            pushNotificationSendMessagePort.send(notification);
        }
    }
}
