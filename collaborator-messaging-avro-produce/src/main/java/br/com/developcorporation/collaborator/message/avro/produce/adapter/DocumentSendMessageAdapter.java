package br.com.developcorporation.collaborator.message.avro.produce.adapter;

import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.domain.port.DocumentSendMessagePort;
import br.com.developcorporation.collaborator.message.avro.produce.mapper.DocumentSendMessageMapper;
import br.com.developcorporation.collaborator.message.avro.produce.service.DocumentSendMessageService;
import br.com.developcorporation.documents.message.avro.Documents;
import br.com.developcorporation.documents.message.avro.MessageControl;
import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class DocumentSendMessageAdapter implements DocumentSendMessagePort {

    private final DocumentSendMessageService service;

    @Override
    public void send(Collaborator collaborator) {
        if(Objects.nonNull(collaborator)){

           Documents documents = DocumentSendMessageMapper.INSTANCE.toAvro(collaborator);
           MessageControl control = DocumentSendMessageMapper.INSTANCE.toMessageControl(
                    ContextHolder.get().getCorrelationId(),
                    LocalDateTime.now().toString(),
                    ContextHolder.get().getApplicationName());

            service.sendMassage(DocumentSendMessageMapper.INSTANCE.toDocumentsMessage(documents, control));
        }
    }
}
