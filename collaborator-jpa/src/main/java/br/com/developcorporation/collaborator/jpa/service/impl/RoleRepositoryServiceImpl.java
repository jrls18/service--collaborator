package br.com.developcorporation.collaborator.jpa.service.impl;

import br.com.developcorporation.collaborator.jpa.entity.Role;
import br.com.developcorporation.collaborator.jpa.repository.RoleRepository;
import br.com.developcorporation.collaborator.jpa.service.RoleRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoleRepositoryServiceImpl implements RoleRepositoryService {

    private final RoleRepository repository;


    @Override
    public Role findByIdCollaboratorTypeAccess(Long id) {
        return repository.findByIdCollaboratorTypeAccess(id);
    }

    @Override
    public Role findById(Long id) {
        Optional<Role> roleOptional = this.repository.findById(id);

        if(roleOptional.isEmpty())
            return null;

        return roleOptional.get();
    }

    @Override
    public List<Role> findByAll() {
        List<br.com.developcorporation.collaborator.jpa.entity.Role> roleOptional = this.repository.findAll();

        if(roleOptional.isEmpty())
            return null;

        return roleOptional.stream()
                .sorted(Comparator.comparingLong(Role::getId))
                .collect(Collectors.toList());
    }
}
