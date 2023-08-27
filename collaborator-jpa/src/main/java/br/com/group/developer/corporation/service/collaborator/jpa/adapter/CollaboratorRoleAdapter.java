package br.com.group.developer.corporation.service.collaborator.jpa.adapter;


import br.com.group.developer.corporation.service.collaborator.domain.port.CollaboratorRolePort;
import br.com.group.developer.corporation.service.collaborator.jpa.mapper.CollaboratorRoleMapper;
import br.com.group.developer.corporation.service.collaborator.jpa.repository.CollaboratorRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CollaboratorRoleAdapter implements CollaboratorRolePort {

    private final CollaboratorRoleRepository collaboratorRoleRepository;

    @Override
    public void save(Long idCollaborator, Long idRole) {

        if(Objects.isNull(idCollaborator) || Objects.isNull(idRole)
                || idCollaborator <= 0 || idRole <= 0)
            return;

        collaboratorRoleRepository.save(CollaboratorRoleMapper.INSTANCE.toEntity(idCollaborator, idRole));
    }
}
