package br.com.developcorporation.collaborator.message.avro.consumer.service.impl;

import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.domain.constants.MessageConstants;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.message.CollaboratorMessage;

import br.com.developcorporation.collaborator.message.avro.consumer.mapper.CollaboratorMessageMapper;
import br.com.developcorporation.collaborator.message.avro.consumer.service.UnlockCollaboratorMessageService;
import br.com.developcorporation.menu.configure.user.unlock.message.avro.UnlockMenuUser;
import br.com.grupo.developer.corporation.lib.logger.logger.Logger;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
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


    private static final String INICIO_PROCESSAMENTO = "INICIO-PROCESSAMENTO";

    private static final String FIM_PROCESSAMENTO = "FIM-PROCESSAMENTO";
    private static final String FIM_PROCESSAMENTO_COM_ERRO_DE_NEGOCIO = "FIM-PROCESSAMENTO-COM-ERRO-DE-NEGOCIO";

    private final CollaboratorService collaboratorService;

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

        CollaboratorMessage message = setDadosDeControleDeProcessamento(record);

        try {

            message.getMessageControl().setDataInicioProcessamento(LocalDateTime.now().toString());
            message.getMessageControl().setSituacaoDoProcessamento(INICIO_PROCESSAMENTO);

            log.info(MessageConstants.ASYNC_REQUEST, Logger.info(message, MessageConstants.INICIALIZADO));


            collaboratorService.unlockCollaboratorAsync(CollaboratorMessageMapper.INSTANCE.toDomain(message.getCollaborator()));

            setDadosController(message, FIM_PROCESSAMENTO);

            log.info(MessageConstants.ASYNC_RESPONSE, Logger.info(message, MessageConstants.FINALIZADO));

        }catch (DomainException ex){

            message.setMessage(CollaboratorMessageMapper.INSTANCE.toMessage(ex));

            setDadosController(message, FIM_PROCESSAMENTO_COM_ERRO_DE_NEGOCIO);

            collaboratorService.sendMessage(message);

            log.info(MessageConstants.ASYNC_RESPONSE, Logger.info(message, MessageConstants.FINALIZADO));
        }
    }

    private static void setDadosController(CollaboratorMessage message, String situacaoProcessamento) {
        message.getMessageControl().setDataFimProcessamento(LocalDateTime.now().toString());
        message.getMessageControl().setSituacaoDoProcessamento(situacaoProcessamento);
    }

    private CollaboratorMessage setDadosDeControleDeProcessamento(final ConsumerRecord<String, UnlockMenuUser> record)  {
            CollaboratorMessage message = CollaboratorMessageMapper.INSTANCE.toDomainAvro(record.value());
            ContextHolder.get().setCorrelationId(message.getMessageControl().getUuid());
            ContextHolder.get().setApplicationName(this.applicationName);
        return message;
    }
}
