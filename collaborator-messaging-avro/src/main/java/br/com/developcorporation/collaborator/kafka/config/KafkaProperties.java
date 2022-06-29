package br.com.developcorporation.collaborator.kafka.config;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@NoArgsConstructor
@Component
public class KafkaProperties {

    @Value("${kafka.bootstrapServers}")
    private String bootstrapSservers;

    @Value("${kafka.acks}")
    private String acks;

    @Value("${kafka.retries}")
    private String retries;

    @Value("${kafka.schemaRegistryUrl}")
    private String schemaRegistryUrl;

    @Value("${kafka.autoCommit}")
    private String kafkaAutoCommit;

    @Value("${kafka.offsetReset}")
    private String kafkaOffsetReset;

    @Value("${kafka.specificAvroReader}")
    private String kafkaSpecificAvroReader;

    @Getter
    @Value("${kafka.topic.produce.user.collaborator}")
    private String topicProduceUserCollaborator;

    public Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", this.bootstrapSservers);
        properties.setProperty("acks", this.acks);
        properties.setProperty("retries", this.retries);
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", this.schemaRegistryUrl);
        properties.setProperty("enable.idempotence", "true");

        //properties.setProperty("enable.auto.commit", this.kafkaAutoCommit);
        //properties.setProperty("auto.offset.reset", this.kafkaOffsetReset);
        //properties.setProperty("specific.avro.reader", this.kafkaSpecificAvroReader);
        return properties;
    }
}
