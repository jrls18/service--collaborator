package br.com.group.developer.corporation.service.collaborator.domain.port;

import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;

public interface PushNewCollaboratorAndRecoverPasswordNotificationSendMessagePort {
    void send(Collaborator collaborator);
}
