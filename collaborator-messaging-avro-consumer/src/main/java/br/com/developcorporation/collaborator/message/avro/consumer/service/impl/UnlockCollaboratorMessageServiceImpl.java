package br.com.developcorporation.collaborator.message.avro.consumer.service.impl;

import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.domain.constants.MessageConstants;

import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.message.avro.consumer.mapper.CollaboratorMessageMapper;
import br.com.developcorporation.collaborator.message.avro.consumer.service.ContextService;
import br.com.developcorporation.collaborator.message.avro.consumer.service.UnlockCollaboratorMessageService;
import br.com.grupo.developer.corporation.lib.logger.logger.Logger;
import br.com.grupo.developer.corporation.libcommons.constants.MessageAssistantConstants;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;
import br.com.grupo.developer.corporation.msg.avro.user.unlock.UnlockMenuUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.FixedDelayStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
@Service
public class UnlockCollaboratorMessageServiceImpl implements UnlockCollaboratorMessageService<UnlockMenuUser> {

    private final CollaboratorService collaboratorService;

    private final ContextService contextService;

    @Value("${spring.application.name}")
    private String applicationName;

    @RetryableTopic(
            attempts = "${kafka.topic.consumer.qtd.retry}",
            backoff = @Backoff(delay = 60000, multiplier = 1.0, maxDelay = 120000),
            autoCreateTopics = "false",
            fixedDelayTopicStrategy = FixedDelayStrategy.SINGLE_TOPIC,
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(topics = "${kafka.topic.consumer.unlock.collaborator}", groupId = "${kafka.topic.consumer.unlock.collaborator.id}")
    @Override
    public void onReceive(ConsumerRecord<String, UnlockMenuUser> record) {

        MessageAsync<Collaborator> messageAsync = CollaboratorMessageMapper.INSTANCE.toDomainAvro(record.value());

        contextService.context(this.applicationName, messageAsync.getCorrelationId(), record.topic());

        messageAsync.setDateTimeStartProcessing(LocalDateTime.now().toString());
        messageAsync.setStatus(MessageConstants.INICIO_CADASTRO_ASYNC);

        log.info(MessageConstants.ASYNC_REQUEST, Logger.info(messageAsync, MessageConstants.INICIALIZADO));

        collaboratorService.unlockCollaboratorAsync(messageAsync.getObj());

        messageAsync.setDateTimeEndProcessing(LocalDateTime.now().toString());
        messageAsync.setStatus(MessageConstants.FIM_CADASTRO_ASYNC);

        log.info(MessageConstants.ASYNC_RESPONSE, Logger.info(messageAsync, MessageAssistantConstants.FINALIZADO));

        contextService.remove();
    }
}
