package br.com.developcorporation.collaborator.message.avro.produce.service;

import br.com.developcorporation.collaborator.message.avro.produce.service.send.SendMessageAvro;
import br.com.developcorporation.documents.message.avro.DocumentsMessage;
import br.com.group.developer.corporation.push.message.PushMessage;
import org.apache.avro.specific.SpecificRecord;


public interface PushNotificationSendMessageService<T extends SpecificRecord> extends SendMessageAvro<PushMessage> {
}
