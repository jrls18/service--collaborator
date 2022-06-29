package br.com.developcorporation.collaborator.core.service.impl;

import br.com.developcorporation.collaborator.core.service.FollowUpService;
import br.com.developcorporation.collaborator.domain.model.FollowUp;
import br.com.developcorporation.collaborator.domain.port.FollowUpPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FollowUpServiceImpl implements FollowUpService {

    private final FollowUpPort port;

    @Override
    public FollowUp getById(Long id) {
        return port.getById(id);
    }

    @Override
    public List<FollowUp> getByAll() {
        return port.getByAll();
    }
}
