package br.com.developcorporation.collaborator.message.avro.produce.service.impl;

import br.com.developcorporation.collaborator.message.avro.produce.service.DocumentSendMessageService;
import br.com.developcorporation.collaborator.message.avro.produce.service.send.SendMessage;
import br.com.developcorporation.documents.message.avro.DocumentsMessage;
import lombok.Getter;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DocumentSendMessageServiceImpl extends SendMessage<DocumentsMessage> implements DocumentSendMessageService<DocumentsMessage> {

    @Getter
    @Value(value = "${kafka.topic.produce.document.name}")
    private String topicName;

    public DocumentSendMessageServiceImpl(KafkaTemplate<String, SpecificRecord> producerKafkaTemplate) {
        super(producerKafkaTemplate);
    }

    @Override
    public void sendMassage(DocumentsMessage classSpecificRecord) {
        super.sendMassage(classSpecificRecord, this.getTopicName());
    }
}
