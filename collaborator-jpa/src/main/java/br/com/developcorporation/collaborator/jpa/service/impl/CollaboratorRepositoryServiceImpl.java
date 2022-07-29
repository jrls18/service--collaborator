package br.com.developcorporation.collaborator.jpa.service.impl;

import br.com.developcorporation.collaborator.jpa.entity.Collaborator;
import br.com.developcorporation.collaborator.jpa.repository.CollaboratorRepository;
import br.com.developcorporation.collaborator.jpa.service.CollaboratorRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Optional<Collaborator> consultaPorCodigo(Long id) {
        return repository.findById(id);
    }


}
