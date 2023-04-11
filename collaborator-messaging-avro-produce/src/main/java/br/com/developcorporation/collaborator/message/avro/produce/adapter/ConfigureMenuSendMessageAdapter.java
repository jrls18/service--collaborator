package br.com.developcorporation.collaborator.message.avro.produce.adapter;

import br.com.developcorporation.collaborator.domain.message.ConfigureMenu;
import br.com.developcorporation.collaborator.domain.port.ConfigureMenuSendMessagePort;
import br.com.developcorporation.collaborator.message.avro.produce.mapper.ConfigureMenuMessageMapper;
import br.com.developcorporation.collaborator.message.avro.produce.service.ConfigureMenuMessageService;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ConfigureMenuSendMessageAdapter implements ConfigureMenuSendMessagePort {

    private final ConfigureMenuMessageService service;

    @Override
    public void send(MessageAsync<ConfigureMenu> domain) {
        if(Objects.nonNull(domain)){
            service.sendMassage(ConfigureMenuMessageMapper.INSTANCE.toAvro(domain));
        }
    }
}
