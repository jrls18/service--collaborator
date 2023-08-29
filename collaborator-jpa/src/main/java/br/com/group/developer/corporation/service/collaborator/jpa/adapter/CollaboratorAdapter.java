package br.com.group.developer.corporation.service.collaborator.jpa.adapter;


import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import br.com.group.developer.corporation.service.collaborator.domain.port.CollaboratorPort;
import br.com.group.developer.corporation.service.collaborator.jpa.mapper.CollaboratorMapper;
import br.com.group.developer.corporation.service.collaborator.jpa.service.CollaboratorRepositoryService;
import br.com.group.developer.corporation.service.collaborator.jpa.service.RoleRepositoryService;
import br.com.grupo.developer.corporation.libcommons.message.Pagination;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class CollaboratorAdapter implements CollaboratorPort {

   private final CollaboratorRepositoryService service;
   private final RoleRepositoryService roleRepositoryService;

    @Override
    public Long save(Collaborator dto) {
        if(Objects.isNull(dto))
            return null;

        return service.save(CollaboratorMapper.INSTANCE.toEntity(dto));
    }


    @Override
    public void updateStatus(Long idCollaborator, Long idStatus) {

        if(Objects.isNull(idCollaborator) || Objects.isNull(idStatus)
                || idCollaborator <= 0 || idStatus <= 0)
            return;

        service.updateStatus(idCollaborator, idStatus);
    }

    @Override
    public void updateDateTimeLastAccess(Long idCollaborator,
                                         LocalDateTime dateTimeLastAccess) {
        if(Objects.nonNull(idCollaborator) && idCollaborator > 0 && Objects.nonNull(dateTimeLastAccess))
            service.updateDateTimeLastAccess(idCollaborator,dateTimeLastAccess);
    }

    @Override
    public Boolean existsCompany(String id) {
        if(StringUtils.isEmpty(id))
            return false;

        return service.existsCompany(Long.parseLong(id));
    }

    @Override
    public Collaborator getById(Long id) {

        if(Objects.isNull(id) || id <=0)
            return null;

        Collaborator collaborator = CollaboratorMapper.INSTANCE.toDomain(service.findById(id).orElse(null));

        return setRoleCollaborator(collaborator);
    }

    @Override
    public Collaborator getByIdActive(String uuid) {
        if(StringUtils.isEmpty(uuid))
            return null;

        return CollaboratorMapper.INSTANCE.toDomain(service.findByIdActive(uuid));
    }

    @Override
    public Collaborator getEmail(String email) {

        if(StringUtils.isEmpty(email))
            return null;

        return setRoleCollaborator(CollaboratorMapper.INSTANCE.toDomain(service.findByEmail(email).orElse(null)));
    }


    private Collaborator setRoleCollaborator(Collaborator collaborator){
        if(Objects.nonNull(collaborator))
            collaborator.setTypeCollaborator(CollaboratorMapper.INSTANCE.toDomain(roleRepositoryService.findByIdCollaboratorTypeAccess(collaborator.getId())));

        return collaborator;
    }

    @Override
    public Optional<Collaborator> findByUserName(final String username){

        if(StringUtils.isEmpty(username))
            return Optional.empty();

        Optional<br.com.group.developer.corporation.service.collaborator.jpa.entity.Collaborator> collaboratorOptional  = service.findByUserName(username);

        return collaboratorOptional.map(collaborator -> setRoleCollaborator(CollaboratorMapper.INSTANCE.toDomain(collaborator)));

    }

    @Override
    public Pagination<Collaborator> search(String searchTerm,
                                           String codEmpresa,
                                           int page,
                                           int size) {

        Pagination<Collaborator> pagination = CollaboratorMapper.INSTANCE.toDomain(service.search(searchTerm, codEmpresa, page, size));

        List<Collaborator> collaborators = new ArrayList<>(pagination.getItems().size());

        for (Collaborator line : pagination.getItems()) {
            collaborators.add(setRoleCollaborator(line));
        }
        pagination.setItems(collaborators.stream()
                .sorted(Comparator.comparingLong(Collaborator::getId))
                .toList());

        return pagination;
    }
}
