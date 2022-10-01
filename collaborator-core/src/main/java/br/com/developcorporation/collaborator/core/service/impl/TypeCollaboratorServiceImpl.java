package br.com.developcorporation.collaborator.core.service.impl;

import br.com.developcorporation.collaborator.core.service.TypeCollaboratorService;
import br.com.developcorporation.collaborator.domain.model.TypeCollaborator;
import br.com.developcorporation.collaborator.domain.port.TypeCollaboratorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TypeCollaboratorServiceImpl implements TypeCollaboratorService {

    private final TypeCollaboratorPort port;

    @Override
    public TypeCollaborator getById(Long id) {
        return port.getById(id);
    }

    @Override
    public List<TypeCollaborator> getByAll() {
        return port.getByAll();
    }
}
