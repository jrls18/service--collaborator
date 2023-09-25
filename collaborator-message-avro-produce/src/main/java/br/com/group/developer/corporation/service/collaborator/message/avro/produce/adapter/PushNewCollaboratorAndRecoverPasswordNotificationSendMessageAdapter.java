package br.com.group.developer.corporation.service.collaborator.message.avro.produce.adapter;

import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import br.com.group.developer.corporation.service.collaborator.domain.port.PushNewCollaboratorAndRecoverPasswordNotificationSendMessagePort;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.mapper.PushNewCollaboratorAndRecoverPasswordNotificationMapper;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.service.NewCollaboratorAndRecoverPasswordNotificationService;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.utils.MessageAsync;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PushNewCollaboratorAndRecoverPasswordNotificationSendMessageAdapter extends MessageAsync<Collaborator> implements PushNewCollaboratorAndRecoverPasswordNotificationSendMessagePort {

    private final NewCollaboratorAndRecoverPasswordNotificationService service;

    @Override
    public void send(Collaborator collaborator) {

        if(Objects.isNull(collaborator))
            return;

        service.sendMassage(PushNewCollaboratorAndRecoverPasswordNotificationMapper.INSTANCE.toAvro(setMessage(collaborator)));
    }
}
