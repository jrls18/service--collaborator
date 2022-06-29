package br.com.developcorporation.collaborator.kafka.adapter;

import br.com.developcorporation.collaborator.kafka.mapper.SendMessageMapper;
import br.com.developcorporation.collaborator.kafka.service.ProduceService;
import br.com.developcorporation.collaborator.domain.model.Company;
import br.com.developcorporation.collaborator.domain.port.SendMessagePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@AllArgsConstructor
@Service
public class SendMessageAdapter implements SendMessagePort {

    private final ProduceService produceService;

    @Override
    public void send(Company dto) {
        if(Objects.nonNull(dto)){
            produceService.sendMassage(SendMessageMapper.INSTANCE.toAvroUser(dto));
        }
    }
}
