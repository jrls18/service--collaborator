package br.com.developcorporation.collaborator.message.avro.produce.service.impl;

import br.com.developcorporation.collaborator.message.avro.CollaboratorMessageError;
import br.com.developcorporation.collaborator.message.avro.produce.service.CollaboratorSendMessageErrorService;
import br.com.developcorporation.collaborator.message.avro.produce.service.send.SendMessage;
import lombok.Getter;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CollaboratorSendMessageErrorServiceImpl extends SendMessage<CollaboratorMessageError> implements CollaboratorSendMessageErrorService<CollaboratorMessageError> {

    @Getter
    @Value(value = "${kafka.topic.produce.collaborator.message.error}")
    private String topicName;

    public CollaboratorSendMessageErrorServiceImpl(KafkaTemplate<String, SpecificRecord> producerKafkaTemplate) {
        super(producerKafkaTemplate);
    }

    @Override
    public void sendMassage(CollaboratorMessageError classSpecificRecord) {
        super.sendMassage(classSpecificRecord, this.getTopicName());
    }
}
