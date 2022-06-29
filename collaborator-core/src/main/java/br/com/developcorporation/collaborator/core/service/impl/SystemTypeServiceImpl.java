package br.com.developcorporation.collaborator.core.service.impl;

import br.com.developcorporation.collaborator.core.service.SystemTypeService;
import br.com.developcorporation.collaborator.domain.model.SystemType;
import br.com.developcorporation.collaborator.domain.port.SystemTypePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SystemTypeServiceImpl  implements SystemTypeService {

    private final SystemTypePort port;


    @Override
    public SystemType getById(Long id) {
        return port.getById(id);
    }

    @Override
    public List<SystemType> getByAll() {
        return  port.getByAll();
    }
}
