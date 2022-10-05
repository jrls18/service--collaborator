package br.com.developcorporation.collaborator.jpa.service;

import br.com.developcorporation.collaborator.jpa.entity.Role;

import java.util.List;


public interface RoleRepositoryService {

    Role findByIdCollaboratorTypeAccess(final Long id);

    Role findById(final Long id);

    List<Role> findByAll();
}
