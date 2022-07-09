package br.com.developcorporation.collaborator.message.avro.consumer.service.impl;

import br.com.developcorporation.collaborator.message.avro.consumer.service.ProduceService;
import br.com.developcorporation.collaborator.message.avro.consumer.config.KafkaProperties;
import br.com.developcorporation.company.message.avro.User;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProduceServiceImpl implements ProduceService {

    private final KafkaProperties confProperties;

    public void sendMassage(User user) {

        KafkaProducer<String, User> producer = new KafkaProducer<>(confProperties.getProperties());

        ProducerRecord<String, User> record = new ProducerRecord<>(confProperties.getTopicProduceUserCollaborator(), user);

        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if(exception == null){
                    System.out.println("Sucesso");
                }else{
                    System.out.println("Error");
                }
            }
        });

        producer.flush();
        producer.close();
    }
}
