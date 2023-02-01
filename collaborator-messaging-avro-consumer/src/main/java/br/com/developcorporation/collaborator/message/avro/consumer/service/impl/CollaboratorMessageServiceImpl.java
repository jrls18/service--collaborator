package br.com.developcorporation.collaborator.message.avro.consumer.service.impl;

import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.domain.constants.MessageConstants;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.message.avro.Colaborador;
import br.com.developcorporation.collaborator.domain.message.CollaboratorMessage;
import br.com.developcorporation.collaborator.message.avro.consumer.mapper.CollaboratorMessageMapper;
import br.com.developcorporation.collaborator.message.avro.consumer.service.CollaboratorMessageService;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.retrytopic.FixedDelayStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import br.com.developcorporation.collaborator.domain.logger.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CollaboratorMessageServiceImpl implements CollaboratorMessageService<Colaborador> {

    private static final Logger LOG = LoggerFactory.getLogger(CollaboratorMessageServiceImpl.class);

    private static final String INICIO_PROCESSAMENTO_DE_CADASTRO = "INICIO-PROCESSAMENTO-DO-CADASTRO-COLABORADOR";

    private static final String FIM_PROCESSAMENTO = "FIM-PROCESSAMENTO-DO-CADASTRO-COLABORADOR-AGUARDANDO-CONFIGURACAO-DE-MENU";
    private static final String FIM_PROCESSAMENTO_COM_ERRO_DE_NEGOCIO = "FIM-PROCESSAMENTO-DO-CADASTRO-COLABORADOR-COM-ERRO-DE-NEGOCIO";

    private final LogDomain logDomain;
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
    @KafkaListener(topics = "${kafka.topic.consumer.collaborator.processo-cadastrar-alterar}", groupId = "${kafka.topic.consumer.collaborator.processo-cadastrar-alterar.client.id}")
    @Override
    public void onReceive(ConsumerRecord<String, Colaborador> record) {

        CollaboratorMessage message = setDadosDeControleDeProcessamento(record);

        try {

            message.getMessageControl().setDataInicioProcessamento(LocalDateTime.now().toString());
            message.getMessageControl().setSituacaoDoProcessamento(INICIO_PROCESSAMENTO_DE_CADASTRO);

            final String json = logDomain.jsonLogInfo(message, MessageConstants.INICIALIZADO);

            LOG.info(MessageConstants.ASYNC_REQUEST, json);

            collaboratorService.addAsync(CollaboratorMessageMapper.INSTANCE.toDomain(message.getCollaborator()));

            setDadosController(message, FIM_PROCESSAMENTO);

            LOG.info(MessageConstants.ASYNC_RESPONSE, message);

        }catch (DomainException ex){

            message.setMessage(CollaboratorMessageMapper.INSTANCE.toMessage(ex));

            setDadosController(message, FIM_PROCESSAMENTO_COM_ERRO_DE_NEGOCIO);

            collaboratorService.sendMessage(message);

            final String json = logDomain.jsonLogInfo(ex, MessageConstants.FINALIZADO);

            LOG.info(MessageConstants.ASYNC_RESPONSE, json);
        }
    }

    private static void setDadosController(CollaboratorMessage message, String situacaoProcessamento) {
        message.getMessageControl().setDataFimProcessamento(LocalDateTime.now().toString());
        message.getMessageControl().setSituacaoDoProcessamento(situacaoProcessamento);
    }

    private CollaboratorMessage setDadosDeControleDeProcessamento(final ConsumerRecord<String, Colaborador> record)  {
            CollaboratorMessage message = CollaboratorMessageMapper.INSTANCE.toDomainAvro(record.value());
            ContextHolder.get().setCorrelationId(message.getMessageControl().getUuid());
            ContextHolder.get().setApplicationName(this.applicationName);
            return message;
    }
}
