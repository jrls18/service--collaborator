package br.com.developcorporation.collaborator.message.avro.produce.service;

import br.com.developcorporation.collaborator.message.avro.produce.service.send.SendMessageAvro;
import br.com.developcorporation.documents.message.avro.DocumentsMessage;
import org.apache.avro.specific.SpecificRecord;


public interface DocumentSendMessageService<T extends SpecificRecord> extends SendMessageAvro<DocumentsMessage> {
}
