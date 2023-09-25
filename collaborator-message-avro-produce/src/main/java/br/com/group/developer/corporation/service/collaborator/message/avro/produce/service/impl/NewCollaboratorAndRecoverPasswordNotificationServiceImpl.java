package br.com.group.developer.corporation.service.collaborator.message.avro.produce.service.impl;

import br.com.group.developer.corporation.push.message.PushNewCollaboratorAndRecoverNotification;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.service.NewCollaboratorAndRecoverPasswordNotificationService;
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
public class NewCollaboratorAndRecoverPasswordNotificationServiceImpl extends SendMessage<PushNewCollaboratorAndRecoverNotification> implements NewCollaboratorAndRecoverPasswordNotificationService<PushNewCollaboratorAndRecoverNotification> {

    @Value(value = "${kafka.topic.produce.notification.collaborator.name}")
    private String topicName;

    public NewCollaboratorAndRecoverPasswordNotificationServiceImpl(KafkaTemplate<String, SpecificRecord> producerKafkaTemplate) {
        super(producerKafkaTemplate);
    }

    @Override
    public void sendMassage(PushNewCollaboratorAndRecoverNotification classSpecificRecord) {
        super.sendMassage(classSpecificRecord, this.getTopicName());
    }
}
