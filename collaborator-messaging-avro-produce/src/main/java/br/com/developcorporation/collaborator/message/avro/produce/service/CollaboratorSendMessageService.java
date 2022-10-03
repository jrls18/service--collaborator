package br.com.developcorporation.collaborator.message.avro.produce.service;

import org.apache.avro.specific.SpecificRecord;
import br.com.developcorporation.collaborator.message.avro.*;
import br.com.developcorporation.collaborator.message.avro.produce.service.send.SendMessageAvro;


public interface CollaboratorSendMessageService<T extends SpecificRecord> extends SendMessageAvro<Colaborador> {
}
