package br.com.group.developer.corporation.service.collaborator.message.avro.produce.service.impl;

import br.com.group.developer.corporation.document.avro.DocumentMessage;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.service.DocumentSendMessageService;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.service.send.SendMessage;
import lombok.Getter;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Getter
@Service
public class DocumentSendMessageServiceImpl extends SendMessage<DocumentMessage> implements DocumentSendMessageService<DocumentMessage> {

    @Value(value = "${kafka.topic.produce.document.name}")
    private String topicName;

    public DocumentSendMessageServiceImpl(KafkaTemplate<String, SpecificRecord> producerKafkaTemplate) {
        super(producerKafkaTemplate);
    }

    @Override
    public void sendMassage(DocumentMessage classSpecificRecord) {
        super.sendMassage(classSpecificRecord, this.getTopicName());
    }
}
