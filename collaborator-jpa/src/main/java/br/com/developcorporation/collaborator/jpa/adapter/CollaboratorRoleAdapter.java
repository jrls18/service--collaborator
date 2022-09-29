package br.com.developcorporation.collaborator.jpa.adapter;

import br.com.developcorporation.collaborator.domain.port.CollaboratorRolePort;
import br.com.developcorporation.collaborator.jpa.mapper.CollaboratorRoleMapper;
import br.com.developcorporation.collaborator.jpa.repository.CollaboratorRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CollaboratorRoleAdapter implements CollaboratorRolePort {

    private final CollaboratorRoleRepository collaboratorRoleRepository;

    @Override
    public void save(Long idCollaborator, Long idRole) {
        collaboratorRoleRepository.save(CollaboratorRoleMapper.INSTANCE.toEntity(idCollaborator, idRole));
    }
}
