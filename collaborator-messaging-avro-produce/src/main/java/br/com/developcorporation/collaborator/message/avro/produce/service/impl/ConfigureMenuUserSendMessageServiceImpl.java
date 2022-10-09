package br.com.developcorporation.collaborator.message.avro.produce.service.impl;

import br.com.developcorporation.collaborator.message.avro.produce.service.ConfigureMenuUserSendMessageService;
import br.com.developcorporation.collaborator.message.avro.produce.service.send.SendMessage;
import br.com.developcorporation.menu.configure.user.message.avro.ConfigureMenuUser;
import lombok.Getter;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConfigureMenuUserSendMessageServiceImpl extends SendMessage<ConfigureMenuUser> implements ConfigureMenuUserSendMessageService<ConfigureMenuUser> {

    @Getter
    @Value(value = "${kafka.topic.consumer.configure.menu.processo-cadastrar-alterar}")
    private String topicName;

    public ConfigureMenuUserSendMessageServiceImpl(KafkaTemplate<String, SpecificRecord> producerKafkaTemplate) {
        super(producerKafkaTemplate);
    }

    @Override
    public void sendMassage(ConfigureMenuUser classSpecificRecord) {
        super.sendMassage(classSpecificRecord, this.getTopicName());
    }
}
