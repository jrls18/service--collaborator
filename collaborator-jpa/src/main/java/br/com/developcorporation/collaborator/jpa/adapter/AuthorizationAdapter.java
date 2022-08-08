package br.com.developcorporation.collaborator.jpa.adapter;

import br.com.developcorporation.collaborator.domain.model.Authorization;
import br.com.developcorporation.collaborator.domain.port.AuthorizationPort;
import br.com.developcorporation.collaborator.jpa.mapper.AuthorizationMapper;
import br.com.developcorporation.collaborator.jpa.repository.AuthorizationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthorizationAdapter implements AuthorizationPort {

    private final AuthorizationRepository repository;

    @Override
    public void add(Authorization dto) {
       repository.save(AuthorizationMapper.INSTANCE.toEntity(dto));
    }

    @Override
    public void update(Authorization dto) {
        repository.save(AuthorizationMapper.INSTANCE.toEntity(dto));
    }

    @Override
    public List<Authorization> getAll() {
        return AuthorizationMapper.INSTANCE.toEntityList(repository.findAll());
    }

    @Override
    public Authorization getById(Long id) {

       Optional<br.com.developcorporation.collaborator.jpa.entity.Authorization> authorization = repository.findById(id);

       if(!authorization.isPresent())
           return null;

        return  AuthorizationMapper.INSTANCE.toDto(authorization.get());
    }

    @Override
    public Authorization getByApplicationName(String name) {

        Optional<br.com.developcorporation.collaborator.jpa.entity.Authorization> authorization = repository.findByApplicationName(name);

        if(!authorization.isPresent())
            return null;

        return AuthorizationMapper.INSTANCE.toDto(authorization.get());
    }

    @Override
    public Authorization getBySiglaApp(String sigla) {
        Optional<br.com.developcorporation.collaborator.jpa.entity.Authorization> authorization = repository.findBySiglaApp(sigla);

        if(!authorization.isPresent())
            return null;

        return AuthorizationMapper.INSTANCE.toDto(authorization.get());
    }

    @Override
    public boolean existsByName(String name) {
        return repository.findByApplicationName(name).isPresent();
    }

    @Override
    public boolean existsBySiglaApp(String app) {
       return repository.findBySiglaApp(app).isPresent();
    }


    @Override
    public Optional<Authorization> getByClientIdAndClientSecret(String clientId, String clientSecret) {

        if(Objects.isNull(clientId) || Objects.isNull(clientSecret))
            return Optional.empty();

        Optional<br.com.developcorporation.collaborator.jpa.entity.Authorization> entity = repository.findByClientIdAndClientSecret(clientId, clientSecret);

        if(entity.isPresent())
            return Optional.ofNullable(AuthorizationMapper.INSTANCE.toDto(entity.get()));
        else
            return Optional.empty();
    }
}
