package br.com.group.developer.corporation.service.collaborator.message.avro.produce.adapter;

import br.com.group.developer.corporation.service.collaborator.domain.model.Notification;
import br.com.group.developer.corporation.service.collaborator.domain.port.PushNotificationSendMessagePort;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.mapper.PushNotificationMapper;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.service.PushNotificationSendMessageService;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.utils.MessageAsync;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PushNotificationSendMessageAdapter extends MessageAsync<Notification> implements PushNotificationSendMessagePort  {

    private final PushNotificationSendMessageService service;

    @Override
    public void send(Notification notification) {

        if(Objects.isNull(notification))
            return;

        service.sendMassage(PushNotificationMapper.INSTANCE.toAvro(setMessage(notification)));
    }
}
