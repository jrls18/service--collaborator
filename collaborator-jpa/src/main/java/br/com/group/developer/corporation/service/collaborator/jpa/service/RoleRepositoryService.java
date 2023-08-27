package br.com.group.developer.corporation.service.collaborator.jpa.service;


import br.com.group.developer.corporation.service.collaborator.jpa.entity.Role;

import java.util.List;


public interface RoleRepositoryService {

    Role findByIdCollaboratorTypeAccess(final Long id);

    Role findById(final Long id);

    List<Role> findByAll();
}
