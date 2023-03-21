package br.com.developcorporation.collaborator.message.avro.produce.adapter;

import br.com.developcorporation.collaborator.domain.message.ConfigureMenu;
import br.com.developcorporation.collaborator.domain.port.ConfigureMenuUserSendMessagePort;
import br.com.developcorporation.collaborator.message.avro.produce.mapper.ConfigureMenuMessageMapper;
import br.com.developcorporation.collaborator.message.avro.produce.service.ConfigureMenuMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ConfigureMenuUserSendMessageAdapter implements ConfigureMenuUserSendMessagePort {

    private final ConfigureMenuMessageService service;

    @Override
    public void send(ConfigureMenu domain) {
        if(Objects.nonNull(domain)){
            service.sendMassage(ConfigureMenuMessageMapper.INSTANCE.toAvro(domain));
        }
    }
}
