package br.com.developcorporation.collaborator.message.avro.produce.adapter;

import br.com.developcorporation.collaborator.domain.message.CollaboratorMessage;
import br.com.developcorporation.collaborator.domain.port.CollaboratorSendMessagePort;
import br.com.developcorporation.collaborator.message.avro.produce.mapper.CollaboratorSendMessageMapper;
import br.com.developcorporation.collaborator.message.avro.produce.service.CollaboratorSendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CollaboratorSendMessageAdapter implements CollaboratorSendMessagePort {

    private final CollaboratorSendMessageService service;

    @Override
    public void send(CollaboratorMessage dto) {
        if(Objects.nonNull(dto)){
            service.sendMassage(CollaboratorSendMessageMapper.INSTANCE.toAvro(dto));
        }
    }
}
