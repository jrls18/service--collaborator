package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.message.Notification;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;

public interface PushNotificationSendMessagePort {
    void send(MessageAsync<Notification> domain);
}
