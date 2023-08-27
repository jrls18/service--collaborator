package br.com.group.developer.corporation.service.collaborator.domain.port;

import br.com.group.developer.corporation.service.collaborator.domain.model.Notification;

public interface PushNotificationSendMessagePort {
    void send(Notification notification);
}
