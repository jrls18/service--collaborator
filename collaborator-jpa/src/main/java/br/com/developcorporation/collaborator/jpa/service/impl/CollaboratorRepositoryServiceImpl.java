package br.com.developcorporation.collaborator.jpa.service.impl;

import br.com.developcorporation.collaborator.jpa.entity.Collaborator;
import br.com.developcorporation.collaborator.jpa.repository.CollaboratorRepository;
import br.com.developcorporation.collaborator.jpa.service.CollaboratorRepositoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CollaboratorRepositoryServiceImpl implements CollaboratorRepositoryService {

    private final CollaboratorRepository repository;


    @Override
    public Optional<Collaborator> findByUserName(String username) {
        return repository.findByUserName(username);
    }

    @Override
    public Optional<Collaborator> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Long save(Collaborator collaborator) {
        return repository.save(collaborator).getId();
    }

    @Override
    public Boolean existeEmpresa(Long id) {
        if(repository.existsByIdCompany(id).intValue() > 0)
            return true;
        return false;
    }

    @Override
    public Optional<Collaborator> consultaPorCodigo(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<Collaborator> search(String searchTerm,String codEmpresa,int page,int size) {
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
        if(Objects.nonNull(idCollaborator) && Objects.nonNull(idStatus))
            repository.updateStatus(idCollaborator, idStatus);
    }
}
