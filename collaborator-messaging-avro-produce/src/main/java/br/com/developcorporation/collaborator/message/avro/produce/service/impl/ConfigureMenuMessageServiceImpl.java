package br.com.developcorporation.collaborator.message.avro.produce.service.impl;

import br.com.developcorporation.collaborator.message.avro.produce.service.ConfigureMenuMessageService;
import br.com.developcorporation.collaborator.message.avro.produce.service.send.SendMessage;
import br.com.group.developer.corporation.configure.menu.ConfigureMenu;
import lombok.Getter;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConfigureMenuMessageServiceImpl extends SendMessage<ConfigureMenu> implements ConfigureMenuMessageService<ConfigureMenu> {

    @Getter
    @Value(value = "${kafka.produce.topic.name.configure.menu}")
    private String topicName;

    public ConfigureMenuMessageServiceImpl(KafkaTemplate<String, SpecificRecord> producerKafkaTemplate) {
        super(producerKafkaTemplate);
    }

    @Override
    public void sendMassage(ConfigureMenu classSpecificRecord) {
        super.sendMassage(classSpecificRecord, this.getTopicName());
    }
}
