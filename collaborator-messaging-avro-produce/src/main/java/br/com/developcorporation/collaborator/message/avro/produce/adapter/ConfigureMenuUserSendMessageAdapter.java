package br.com.developcorporation.collaborator.message.avro.produce.adapter;

import br.com.developcorporation.collaborator.domain.message.ConfigureMenuUser;
import br.com.developcorporation.collaborator.domain.port.ConfigureMenuUserSendMessagePort;
import br.com.developcorporation.collaborator.message.avro.produce.mapper.ConfigureMenuUserSendMessageMapper;
import br.com.developcorporation.collaborator.message.avro.produce.service.ConfigureMenuUserSendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ConfigureMenuUserSendMessageAdapter implements ConfigureMenuUserSendMessagePort {

    private final ConfigureMenuUserSendMessageService service;

    @Override
    public void send(ConfigureMenuUser domain) {
        if(Objects.nonNull(domain)){
            service.sendMassage(ConfigureMenuUserSendMessageMapper.INSTANCE.toAvro(domain));
        }
    }
}
