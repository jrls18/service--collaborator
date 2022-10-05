package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.message.CollaboratorMessage;

public interface CollaboratorSendMessagePort {
    void send(CollaboratorMessage dto);
}
