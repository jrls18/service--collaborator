package br.com.developcorporation.collaborator.message.avro.produce.service;

import br.com.developcorporation.collaborator.message.avro.produce.service.send.SendMessageAvro;
import br.com.developcorporation.menu.configure.user.message.avro.ConfigureMenuUser;
import org.apache.avro.specific.SpecificRecord;


public interface ConfigureMenuUserSendMessageService<T extends SpecificRecord> extends SendMessageAvro<ConfigureMenuUser> {
}
