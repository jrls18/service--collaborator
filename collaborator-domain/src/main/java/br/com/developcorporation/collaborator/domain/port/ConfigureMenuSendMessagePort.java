package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.message.ConfigureMenu;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;

public interface ConfigureMenuSendMessagePort {
    void send(MessageAsync<ConfigureMenu> domain);
}
