package br.com.developcorporation.collaborator.message.avro.produce.service.impl;

import br.com.developcorporation.collaborator.message.avro.produce.service.PushNotificationSendMessageService;
import br.com.developcorporation.collaborator.message.avro.produce.service.send.SendMessage;
import br.com.group.developer.corporation.push.message.PushMessage;
import lombok.Getter;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationSendMessageServiceImpl extends SendMessage<PushMessage> implements PushNotificationSendMessageService<PushMessage> {

    @Getter
    @Value(value = "${kafka.produce.topic.name.push.notification}")
    private String topicName;

    public PushNotificationSendMessageServiceImpl(KafkaTemplate<String, SpecificRecord> producerKafkaTemplate) {
        super(producerKafkaTemplate);
    }

    @Override
    public void sendMassage(PushMessage classSpecificRecord) {
        super.sendMassage(classSpecificRecord, this.getTopicName());
    }
}
