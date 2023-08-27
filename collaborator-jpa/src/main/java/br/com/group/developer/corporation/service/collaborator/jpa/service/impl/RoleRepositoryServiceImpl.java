package br.com.group.developer.corporation.service.collaborator.jpa.service.impl;


import br.com.group.developer.corporation.service.collaborator.jpa.entity.Role;
import br.com.group.developer.corporation.service.collaborator.jpa.repository.RoleRepository;
import br.com.group.developer.corporation.service.collaborator.jpa.service.RoleRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class RoleRepositoryServiceImpl implements RoleRepositoryService {

    private final RoleRepository repository;


    @Override
    public Role findByIdCollaboratorTypeAccess(Long id) {

        if(Objects.isNull(id) || id <= 0)
            return null;

        return repository.findByIdCollaboratorTypeAccess(id);
    }

    @Override
    public Role findById(Long id) {

        if(Objects.isNull(id) || id <= 0)
            return null;

        Optional<Role> roleOptional = this.repository.findById(id);

        return roleOptional.orElse(null);

    }

    @Override
    public List<Role> findByAll() {
        List<Role> roleOptional = this.repository.findAll();

        if(roleOptional.isEmpty())
            return Collections.emptyList();

        return roleOptional.stream()
                .sorted(Comparator.comparingLong(Role::getId))
                .toList();
    }
}
