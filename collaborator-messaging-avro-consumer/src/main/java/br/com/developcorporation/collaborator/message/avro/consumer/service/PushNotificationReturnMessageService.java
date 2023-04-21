package br.com.developcorporation.collaborator.message.avro.consumer.service;

import br.com.developcorporation.collaborator.message.avro.consumer.service.listener.ListenerMessageAvro;
import br.com.group.developer.corporation.push.message.response.PushMessageReturn;
import org.apache.avro.generic.GenericRecord;

public interface PushNotificationReturnMessageService<T extends GenericRecord> extends ListenerMessageAvro<PushMessageReturn> {
}
