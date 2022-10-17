package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.model.Collaborator;

public interface DocumentSendMessagePort {
    void send(Collaborator collaborator);
}
