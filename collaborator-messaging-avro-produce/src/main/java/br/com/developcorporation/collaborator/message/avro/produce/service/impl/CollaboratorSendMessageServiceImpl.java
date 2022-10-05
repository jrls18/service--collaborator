package br.com.developcorporation.collaborator.message.avro.produce.service.impl;

import br.com.developcorporation.collaborator.message.avro.produce.service.CollaboratorSendMessageService;
import br.com.developcorporation.collaborator.message.avro.produce.service.send.SendMessage;
import br.com.developcorporation.collaborator.message.result.avro.Colaborador;
import lombok.Getter;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CollaboratorSendMessageServiceImpl extends SendMessage<Colaborador> implements CollaboratorSendMessageService<Colaborador> {

    @Getter
    @Value(value = "${kafka.topic.produce.collaborator.message.result}")
    private String topicName;

    public CollaboratorSendMessageServiceImpl(KafkaTemplate<String, SpecificRecord> producerKafkaTemplate) {
        super(producerKafkaTemplate);
    }

    @Override
    public void sendMassage(Colaborador classSpecificRecord) {
        super.sendMassage(classSpecificRecord, this.getTopicName());
    }
}
