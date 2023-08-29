package br.com.group.developer.corporation.service.collaborator.jpa.service.impl;


import br.com.group.developer.corporation.service.collaborator.jpa.entity.Collaborator;
import br.com.group.developer.corporation.service.collaborator.jpa.repository.CollaboratorRepository;
import br.com.group.developer.corporation.service.collaborator.jpa.service.CollaboratorRepositoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CollaboratorRepositoryServiceImpl implements CollaboratorRepositoryService {

    private final CollaboratorRepository repository;


    @Override
    public Optional<Collaborator> findByUserName(String username) {
        if(StringUtils.isEmpty(username))
            return Optional.empty();

        return repository.findByUserName(username);
    }

    @Override
    public void updateDateTimeLastAccess(Long idCollaborator, LocalDateTime dateTimeLastAccess) {
        if(Objects.isNull(idCollaborator) && Objects.isNull(dateTimeLastAccess))
            return;

        repository.updateDateTimeLastAccess(idCollaborator,dateTimeLastAccess);
    }

    @Override
    public Optional<Collaborator> findByEmail(String email) {
        if(StringUtils.isEmpty(email))
            return Optional.empty();

        return repository.findByEmail(email);
    }

    @Override
    public Long save(Collaborator collaborator) {
        if(Objects.isNull(collaborator))
            return null;

        return repository.save(collaborator).getId();
    }

    @Override
    public Boolean existsCompany(Long id) {
      if(Objects.isNull(id) || id <=0)
          return false;

        return !Objects.isNull(repository.findByIdCompany(id));
    }

    @Override
    public Optional<Collaborator> findById(Long id) {

        if(Objects.isNull(id) || id <=0)
            return Optional.empty();

        return repository.findById(id);
    }

    @Override
    public Page<Collaborator> search(String searchTerm, String codEmpresa, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "nome");

        return repository.search(
                searchTerm,
                codEmpresa,
                pageRequest);
    }

    @Override
    public Page<Collaborator> findAll() {
        int page = 0;
        int size = 10;

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "nome");
        return new PageImpl<>(
                repository.findAll(),
                pageRequest, size);
    }

    @Override
    public Collaborator findByIdActive(String uuid) {
        if(StringUtils.isEmpty(uuid))
            return null;

       return repository.findByIdActive(uuid).orElse(null);
    }

    @Override
    public void updateStatus(Long idCollaborator, Long idStatus) {

        if(Objects.isNull(idCollaborator) ||
                Objects.isNull(idStatus) ||
                idCollaborator <= 0 || idStatus <= 0)
            return;

        repository.updateStatus(idCollaborator, idStatus);
    }
}
