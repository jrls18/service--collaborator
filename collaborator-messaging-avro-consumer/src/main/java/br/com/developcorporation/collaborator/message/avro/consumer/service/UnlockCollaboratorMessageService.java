package br.com.developcorporation.collaborator.message.avro.consumer.service;

import br.com.developcorporation.collaborator.message.avro.consumer.service.listener.ListenerMessageAvro;
import br.com.developcorporation.menu.configure.user.unlock.message.avro.UnlockMenuUser;
import org.apache.avro.generic.GenericRecord;

public interface UnlockCollaboratorMessageService<T extends GenericRecord> extends ListenerMessageAvro<UnlockMenuUser> {
}
