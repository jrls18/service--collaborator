package br.com.developcorporation.collaborator.jpa.adapter;

import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.domain.model.Pagination;
import br.com.developcorporation.collaborator.domain.port.CollaboratorPort;
import br.com.developcorporation.collaborator.jpa.mapper.CollaboratorMapper;
import br.com.developcorporation.collaborator.jpa.service.CollaboratorRepositoryService;
import br.com.developcorporation.collaborator.jpa.service.RoleRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CollaboratorAdapter implements CollaboratorPort {

   private final CollaboratorRepositoryService service;
   private final RoleRepositoryService roleRepositoryService;

    @Override
    public Long add(Collaborator dto) {
        return service.save(CollaboratorMapper.INSTANCE.toEntity(dto));
    }

    @Override
    public void update(Collaborator dto) {
        service.save(CollaboratorMapper.INSTANCE.toEntity(dto));
    }

    @Override
    public Boolean existeEmpresa(String id) {
        return service.existeEmpresa(Long.parseLong(id));
    }

    @Override
    public Collaborator getById(Long id) {

        Collaborator collaborator = CollaboratorMapper.INSTANCE.toDomain(service.consultaPorCodigo(id).orElse(null));

        return setRoleCollaborator(collaborator);
    }

    @Override
    public Collaborator getEmail(String email) {
        return setRoleCollaborator(CollaboratorMapper.INSTANCE.toDomain(service.findByEmail(email).orElse(null)));
    }


    private Collaborator setRoleCollaborator(Collaborator collaborator){
        if(Objects.nonNull(collaborator))
            collaborator.setTypeCollaborator(CollaboratorMapper.INSTANCE.toDomain(roleRepositoryService.findByIdCollaboratorTypeAccess(collaborator.getId())));

        return collaborator;
    }

    @Override
    public Optional<Collaborator> findByUserName(final String username){

        Optional<br.com.developcorporation.collaborator.jpa.entity.Collaborator> collaboratorOptional  = service.findByUserName(username);

        if(collaboratorOptional.isEmpty())
            return Optional.empty();

       return Optional.of(setRoleCollaborator(CollaboratorMapper.INSTANCE.toDomain(collaboratorOptional.get())));
    }

    @Override
    public Pagination<Collaborator> search(String searchTerm, int page, int size) {

        Pagination<Collaborator> pagination = CollaboratorMapper.INSTANCE.toDomain(service.search(searchTerm, page, size));

        List<Collaborator> collaborators = new ArrayList<>(pagination.getItems().size());

        for (Collaborator line : pagination.getItems()) {
            collaborators.add(setRoleCollaborator(line));
        }
        pagination.setItems(collaborators.stream()
                .sorted(Comparator.comparingLong(Collaborator::getId))
                .collect(Collectors.toList()));

        return pagination;
    }

}
