package br.com.developcorporation.collaborator.message.avro.produce.adapter;

import br.com.developcorporation.collaborator.domain.message.Notification;
import br.com.developcorporation.collaborator.domain.port.PushNotificationSendMessagePort;
import br.com.developcorporation.collaborator.message.avro.produce.mapper.PushNotificationMapper;
import br.com.developcorporation.collaborator.message.avro.produce.service.PushNotificationSendMessageService;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PushNotificationSendMessageAdapter implements PushNotificationSendMessagePort {

    private final PushNotificationSendMessageService service;

    @Override
    public void send(MessageAsync<Notification> domain) {
        if(Objects.nonNull(domain)){
            service.sendMassage(PushNotificationMapper.INSTANCE.toAvro(domain));
        }
    }
}
