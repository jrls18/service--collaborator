package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.message.ConfigureMenu;

public interface ConfigureMenuUserSendMessagePort {
    void send(ConfigureMenu domain);
}
