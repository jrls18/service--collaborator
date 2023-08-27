package br.com.group.developer.corporation.service.collaborator.core.service.impl;


import br.com.group.developer.corporation.service.collaborator.core.service.TypeCollaboratorService;
import br.com.group.developer.corporation.service.collaborator.domain.model.TypeCollaborator;
import br.com.group.developer.corporation.service.collaborator.domain.port.TypeCollaboratorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TypeCollaboratorServiceImpl implements TypeCollaboratorService {

    private final TypeCollaboratorPort port;

    @Override
    public TypeCollaborator getById(Long id) {
        if(Objects.isNull(id) || id <= 0)
            return null;

        return port.getById(id);
    }

    @Override
    public List<TypeCollaborator> getByAll() {
        return port.getByAll();
    }
}
