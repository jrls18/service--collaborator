package br.com.group.developer.corporation.service.collaborator.message.avro.produce.service.impl;

import br.com.group.developer.corporation.push.message.PushMessage;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.service.PushNotificationSendMessageService;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.service.send.SendMessage;
import lombok.Getter;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Primary
@Getter
@Service
public class PushNotificationSendMessageServiceImpl extends SendMessage<PushMessage> implements PushNotificationSendMessageService<PushMessage> {

    @Value(value = "${kafka.topic.produce.notification.name}")
    private String topicName;

    public PushNotificationSendMessageServiceImpl(KafkaTemplate<String, SpecificRecord> producerKafkaTemplate) {
        super(producerKafkaTemplate);
    }

    @Override
    public void sendMassage(PushMessage classSpecificRecord) {
        super.sendMassage(classSpecificRecord, this.getTopicName());
    }
}
