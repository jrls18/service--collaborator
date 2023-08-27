package br.com.group.developer.corporation.service.collaborator.jpa.adapter;

import br.com.group.developer.corporation.service.collaborator.domain.model.TypeCollaborator;
import br.com.group.developer.corporation.service.collaborator.domain.port.TypeCollaboratorPort;
import br.com.group.developer.corporation.service.collaborator.jpa.mapper.TypeRoleMapper;
import br.com.group.developer.corporation.service.collaborator.jpa.service.RoleRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class TypeRoleAdapter implements TypeCollaboratorPort {

    private final RoleRepositoryService service;

    @Override
    public TypeCollaborator getById(Long id) {

        if(Objects.isNull(id) || id <= 0)
            return null;

        return TypeRoleMapper.INSTANCE.toDto(service.findById(id));
    }

    @Override
    public List<TypeCollaborator> getByAll() {
        return TypeRoleMapper.INSTANCE.toEntityList(service.findByAll());
    }

    @Override
    public TypeCollaborator findByIdCollaboratorTypeAccess(Long id) {

        if(Objects.isNull(id) || id <= 0)
            return null;

        return TypeRoleMapper.INSTANCE.toDto(service.findByIdCollaboratorTypeAccess(id));
    }
}
