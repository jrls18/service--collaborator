package br.com.developcorporation.collaborator.message.avro.consumer.service.impl;

import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.domain.constants.MessageConstants;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.message.avro.consumer.mapper.CollaboratorMessageMapper;
import br.com.developcorporation.collaborator.message.avro.consumer.service.CollaboratorMessageService;
import br.com.developcorporation.collaborator.message.avro.consumer.service.ContextService;
import br.com.grupo.developer.corporation.lib.logger.logger.Logger;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import br.com.grupo.developer.corporation.libcommons.constants.MessageAssistantConstants;
import br.com.grupo.developer.corporation.libcommons.exception.DomainException;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;
import br.com.grupo.developer.corporation.msg.avro.collaborator.CollaboratorAsync;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Log4j2
@RequiredArgsConstructor
@Service
public class CollaboratorMessageServiceImpl implements CollaboratorMessageService<CollaboratorAsync> {
    private final CollaboratorService collaboratorService;

    private final ContextService contextService;


    @Value("${spring.application.name}")
    private String applicationName;

    /*
    @RetryableTopic(
            attempts = "${kafka.topic.consumer.qtd.retry}",
            backoff = @Backoff(delay = 60000, multiplier = 1.0, maxDelay = 120000),
            autoCreateTopics = "false",
            fixedDelayTopicStrategy = FixedDelayStrategy.SINGLE_TOPIC,
            dltStrategy = DltStrategy.NO_DLT
    )
    @KafkaListener(topics = "${kafka.topic.consumer.collaborator.name}", groupId = "${kafka.topic.consumer.collaborator.id}")
         */
    @Override
    public void onReceive(ConsumerRecord<String, CollaboratorAsync> record) {

        MessageAsync<Collaborator> messageAsync = setDadosDeControleDeProcessamento(record);

        try {

            messageAsync.setDateTimeStartProcessing(LocalDateTime.now().toString());
            messageAsync.setStatus(MessageConstants.INICIO_CADASTRO_ASYNC);

            log.info(MessageConstants.ASYNC_REQUEST, Logger.info(messageAsync, MessageAssistantConstants.INICIALIZADO));

            collaboratorService.saveAsync(messageAsync.getObj());

            messageAsync.setDateTimeEndProcessing(LocalDateTime.now().toString());
            messageAsync.setStatus(MessageConstants.FIM_CADASTRO_ASYNC);

            log.info(MessageConstants.ASYNC_RESPONSE, Logger.info(messageAsync, MessageAssistantConstants.FINALIZADO));

        }catch (DomainException ex){

           // message.setMessage(CollaboratorMessageMapper.INSTANCE.toMessage(ex));

            messageAsync.setDateTimeEndProcessing(LocalDateTime.now().toString());
            messageAsync.setStatus(MessageConstants.FIM_CADASTRO_ASYNC_ERRO_DE_NEGOCIO);

           // collaboratorService.sendMessage(message);

           log.info(MessageConstants.ASYNC_RESPONSE, Logger.info(messageAsync, MessageConstants.FINALIZADO));
        }

        ContextHolder.remove();
    }

    @SneakyThrows
    private MessageAsync<Collaborator> setDadosDeControleDeProcessamento(final ConsumerRecord<String, CollaboratorAsync> record) {
        MessageAsync<Collaborator> messageAsync = CollaboratorMessageMapper.INSTANCE.toDomain(record.value());

        contextService.context(this.applicationName, messageAsync.getCorrelationId(), record.topic());

        return messageAsync;
    }
}
