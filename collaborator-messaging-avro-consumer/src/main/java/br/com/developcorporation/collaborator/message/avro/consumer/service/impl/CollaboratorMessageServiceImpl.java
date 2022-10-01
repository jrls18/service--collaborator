package br.com.developcorporation.collaborator.message.avro.consumer.service.impl;

import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.domain.constants.MessageConstants;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.message.avro.Colaborador;
import br.com.developcorporation.collaborator.message.avro.consumer.mapper.CollaboratorMessageMapper;
import br.com.developcorporation.collaborator.message.avro.consumer.service.CollaboratorMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.FixedDelayStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import br.com.developcorporation.collaborator.domain.logger.*;

@Log
@RequiredArgsConstructor
@Service
public class CollaboratorMessageServiceImpl implements CollaboratorMessageService<Colaborador> {

    private static final Logger LOG = LoggerFactory.getLogger(CollaboratorMessageServiceImpl.class);

    private final LogDomain logDomain;
    private final CollaboratorService collaboratorService;

    @RetryableTopic(
            attempts = "${kafka.topic.consumer.qtd.retry}",
            backoff = @Backoff(delay = 80000, multiplier = 1.0, maxDelay = 120000),
            autoCreateTopics = "false",
            fixedDelayTopicStrategy = FixedDelayStrategy.SINGLE_TOPIC,
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(topics = "${kafka.topic.consumer.collaborator}", groupId = "${kafka.topic.consumer.client.id}")
    @Override
    public void onReceive(ConsumerRecord<String, Colaborador> record) {
        try {

            final String jsonRequest =  logDomain.jsonLogInfo(record.value(), MessageConstants.INICIALIZADO);

            LOG.info(MessageConstants.ASYNC_REQUEST, jsonRequest);

            collaboratorService.addAsync(CollaboratorMessageMapper.INSTANCE.toDomain(record.value()));

            LOG.info(MessageConstants.ASYNC_RESPONSE, jsonRequest);

        }catch (DomainException ex){
            collaboratorService.sendMessageError(ex);

            final String jsonRequest =  logDomain.jsonLogInfo(ex, MessageConstants.FINALIZADO);

            LOG.info(MessageConstants.ASYNC_RESPONSE, jsonRequest);
        }
    }
}
