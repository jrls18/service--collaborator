package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.exception.DomainException;

public interface CollaboratorSendMessageErrorPort {
    void send(DomainException dto);
}
