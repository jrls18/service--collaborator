package br.com.group.developer.corporation.service.collaborator.message.avro.produce.adapter;


import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import br.com.group.developer.corporation.service.collaborator.domain.port.DocumentSendMessagePort;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.mapper.DocumentSendMessageMapper;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.service.DocumentSendMessageService;
import br.com.group.developer.corporation.service.collaborator.message.avro.produce.utils.MessageAsync;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;


@RequiredArgsConstructor
@Service
public class DocumentSendMessageAdapter extends MessageAsync<Collaborator> implements DocumentSendMessagePort {

    private final DocumentSendMessageService service;

    @Override
    public void send(Collaborator collaborator) {

        if(Objects.isNull(collaborator))
            return;

        service.sendMassage(DocumentSendMessageMapper.INSTANCE.toAvro(setMessage(collaborator)));
    }
}
