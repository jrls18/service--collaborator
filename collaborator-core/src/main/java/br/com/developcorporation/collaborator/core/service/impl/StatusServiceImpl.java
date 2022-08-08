package br.com.developcorporation.collaborator.core.service.impl;

import br.com.developcorporation.collaborator.core.service.StatusService;
import br.com.developcorporation.collaborator.domain.model.Status;
import br.com.developcorporation.collaborator.domain.port.StatusPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StatusServiceImpl implements StatusService {

    private final StatusPort port;

    @Override
    public Status getById(Long id) {
        return port.getById(id);
    }

    @Override
    public List<Status> getByAll() {
        return  port.getByAll();
    }
}
