package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.message.ConfigureMenuUser;

public interface ConfigureMenuUserSendMessagePort {
    void send(ConfigureMenuUser domain);
}
