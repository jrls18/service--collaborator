package br.com.group.developer.corporation.service.collaborator.core.service.impl;

import br.com.group.developer.corporation.service.collaborator.core.service.StatusService;
import br.com.group.developer.corporation.service.collaborator.domain.model.Status;
import br.com.group.developer.corporation.service.collaborator.domain.port.StatusPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class StatusServiceImpl implements StatusService {

    private final StatusPort port;

    @Override
    public Status getById(Long id) {
        if(Objects.isNull(id) || id <= 0)
            return null;

        return port.getById(id);
    }

    @Override
    public List<Status> getByAll() {
        return  port.getByAll();
    }
}
