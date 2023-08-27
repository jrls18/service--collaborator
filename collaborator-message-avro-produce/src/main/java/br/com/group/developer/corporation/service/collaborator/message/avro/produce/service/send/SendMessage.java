package br.com.group.developer.corporation.service.collaborator.message.avro.produce.service.send;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.avro.specific.SpecificRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Log
@RequiredArgsConstructor
@Service
public abstract class SendMessage<T extends SpecificRecord> {

    private final KafkaTemplate<String, SpecificRecord> producerKafkaTemplate;

    protected void sendMassage(T avro, String topicName) {

        if(StringUtils.isEmpty(topicName) || Objects.isNull(avro))
            return;

        ProducerRecord<String, SpecificRecord> producerRecord = new ProducerRecord<>(topicName, null, avro);

        CompletableFuture<SendResult<String, SpecificRecord>> future = producerKafkaTemplate.send(producerRecord);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Your message was delivered with following offset: " + result.getRecordMetadata().offset());
            } else {
                log.severe("Message could not be delivered. Due to : " + ex.getMessage());
            }
        });

        producerKafkaTemplate.flush();
        producerKafkaTemplate.destroy();
    }
}
