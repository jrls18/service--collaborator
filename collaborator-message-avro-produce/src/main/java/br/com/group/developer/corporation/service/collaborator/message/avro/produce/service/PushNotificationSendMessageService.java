package br.com.group.developer.corporation.service.collaborator.message.avro.produce.service;

import br.com.group.developer.corporation.service.collaborator.message.avro.produce.service.send.SendMessageAvro;
import org.apache.avro.specific.SpecificRecord;

public interface PushNotificationSendMessageService<T extends SpecificRecord> extends SendMessageAvro<T> {
}
