package br.com.developcorporation.collaborator.message.avro.consumer.service;

import br.com.developcorporation.collaborator.message.avro.consumer.service.listener.ListenerMessageAvro;
import br.com.group.developer.corporation.configure.menu.return$.ConfigureMenuReturn;
import org.apache.avro.generic.GenericRecord;

public interface ConfigureMenuReturnMessageService<T extends GenericRecord> extends ListenerMessageAvro<ConfigureMenuReturn> {
}
