package br.com.developcorporation.collaborator.message.avro.produce.service.send;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Log
@RequiredArgsConstructor
@Service
public abstract class SendMessage<T extends SpecificRecord> {

    private final KafkaTemplate<String, SpecificRecord> producerKafkaTemplate;

    protected void sendMassage(T avro, String topicName) {

        ProducerRecord<String, SpecificRecord> producerRecord = new ProducerRecord<>(topicName, null, avro);

        ListenableFuture<SendResult<String, SpecificRecord>> future = producerKafkaTemplate.send(producerRecord);

        future.addCallback(new KafkaSendCallback<>() {

            @Override
            public void onFailure(KafkaProducerException kafkaProducerException) {
                log.severe("Message could not be delivered. " + kafkaProducerException.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, SpecificRecord> result) {
                log.info("Your message was delivered with following offset: " + result.getRecordMetadata().offset());
            }
        });

        producerKafkaTemplate.flush();
        producerKafkaTemplate.destroy();
    }
}
