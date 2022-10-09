package br.com.developcorporation.collaborator.jpa.adapter;

import br.com.developcorporation.collaborator.domain.model.TypeCollaborator;
import br.com.developcorporation.collaborator.domain.port.TypeCollaboratorPort;
import br.com.developcorporation.collaborator.jpa.mapper.TypeRoleMapper;
import br.com.developcorporation.collaborator.jpa.service.RoleRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TypeRoleAdapter implements TypeCollaboratorPort {

    private final RoleRepositoryService  service;

    @Override
    public TypeCollaborator getById(Long id) {
        return TypeRoleMapper.INSTANCE.toDto(service.findById(id));
    }

    @Override
    public List<TypeCollaborator> getByAll() {
        return TypeRoleMapper.INSTANCE.toEntityList(service.findByAll());
    }

    @Override
    public TypeCollaborator findByIdCollaboratorTypeAccess(Long id) {
        return TypeRoleMapper.INSTANCE.toDto(service.findByIdCollaboratorTypeAccess(id));
    }
}
