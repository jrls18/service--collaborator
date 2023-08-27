package br.com.group.developer.corporation.service.collaborator.message.avro.consumer.service;

import br.com.group.developer.corporation.service.collaborator.message.avro.consumer.listener.ListenerMessageAvro;
import br.com.grupo.developer.corporation.msg.avro.collaborator.CollaboratorAsync;
import org.apache.avro.generic.GenericRecord;

public interface CollaboratorMessageDltService<T extends GenericRecord> extends ListenerMessageAvro<CollaboratorAsync> {

}
