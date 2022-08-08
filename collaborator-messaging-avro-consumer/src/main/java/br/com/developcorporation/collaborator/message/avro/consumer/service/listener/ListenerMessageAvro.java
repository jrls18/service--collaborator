package br.com.developcorporation.collaborator.message.avro.consumer.service.listener;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.messaging.handler.annotation.Payload;

public interface ListenerMessageAvro<T extends GenericRecord> {
     void onReceive(final @Payload ConsumerRecord< String, T> record);
}
