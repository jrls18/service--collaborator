package br.com.developcorporation.collaborator.message.avro.produce.adapter;

import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.port.CollaboratorSendMessageErrorPort;
import br.com.developcorporation.collaborator.message.avro.produce.mapper.CollaboratorSendMessageErrorMapper;
import br.com.developcorporation.collaborator.message.avro.produce.service.CollaboratorSendMessageErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CollaboratorSendMessageErrorAdapter implements CollaboratorSendMessageErrorPort {

    private final CollaboratorSendMessageErrorService service;

    @Override
    public void send(DomainException dto) {
        if(Objects.nonNull(dto)){
            service.sendMassage(CollaboratorSendMessageErrorMapper.INSTANCE.toAvro(dto));
        }
    }
}
