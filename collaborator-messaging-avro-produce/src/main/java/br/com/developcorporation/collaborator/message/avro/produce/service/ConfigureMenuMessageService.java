package br.com.developcorporation.collaborator.message.avro.produce.service;


import br.com.developcorporation.collaborator.message.avro.produce.service.send.SendMessageAvro;
import br.com.group.developer.corporation.configure.menu.ConfigureMenu;
import org.apache.avro.specific.SpecificRecord;


public interface ConfigureMenuMessageService<T extends SpecificRecord> extends SendMessageAvro<ConfigureMenu> {
}
