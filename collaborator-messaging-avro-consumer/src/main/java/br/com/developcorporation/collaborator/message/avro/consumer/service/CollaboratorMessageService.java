package br.com.developcorporation.collaborator.message.avro.consumer.service;

import br.com.developcorporation.collaborator.message.avro.consumer.service.listener.ListenerMessageAvro;
import br.com.developcorporation.company.message.avro.produce.Colaborador;
import org.apache.avro.generic.GenericRecord;

public interface CollaboratorMessageService <T extends GenericRecord> extends ListenerMessageAvro<Colaborador> {
}
