package br.com.developcorporation.collaborator.message.avro.consumer.service;

import br.com.developcorporation.collaborator.message.avro.consumer.service.listener.ListenerMessageAvro;
import br.com.grupo.developer.corporation.msg.avro.collaborator.CollaboratorAsync;
import org.apache.avro.generic.GenericRecord;

public interface CollaboratorMessageService <T extends GenericRecord> extends ListenerMessageAvro<CollaboratorAsync> {
}
