package br.com.group.developer.corporation.service.collaborator.jpa.adapter;


import br.com.group.developer.corporation.service.collaborator.domain.model.Authorization;
import br.com.group.developer.corporation.service.collaborator.domain.port.AuthorizationPort;
import br.com.group.developer.corporation.service.collaborator.jpa.mapper.AuthorizationMapper;
import br.com.group.developer.corporation.service.collaborator.jpa.repository.AuthorizationRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthorizationAdapter implements AuthorizationPort {

    private final AuthorizationRepository repository;

    @Override
    public void save(Authorization dto) {
        if(Objects.isNull(dto))
            return;

       repository.save(AuthorizationMapper.INSTANCE.toEntity(dto));
    }

    @Override
    public List<Authorization> getAll() {
        return AuthorizationMapper.INSTANCE.toEntityList(repository.findAll());
    }

    @Override
    public Authorization getById(Long id) {
        if(Objects.isNull(id) || id <= 0)
            return null;

        Optional<br.com.group.developer.corporation.service.collaborator.jpa.entity.Authorization> authorization = repository.findById(id);

        return authorization.map(AuthorizationMapper.INSTANCE::toDto).orElse(null);
    }

    @Override
    public Authorization getByApplicationName(String name) {

        if(StringUtils.isEmpty(name))
            return null;

        Optional<br.com.group.developer.corporation.service.collaborator.jpa.entity.Authorization> authorization = repository.findByApplicationName(name);

        return authorization.map(AuthorizationMapper.INSTANCE::toDto).orElse(null);

    }

    @Override
    public Authorization getBySiglaApp(String sigla) {

        if(StringUtils.isEmpty(sigla))
            return null;

        Optional<br.com.group.developer.corporation.service.collaborator.jpa.entity.Authorization> authorization = repository.findBySiglaApp(sigla);

        return authorization.map(AuthorizationMapper.INSTANCE::toDto).orElse(null);

    }

    @Override
    public boolean existsByName(String name) {
        if(StringUtils.isEmpty(name))
            return false;

        return repository.findByApplicationName(name).isPresent();
    }

    @Override
    public boolean existsBySiglaApp(String app) {
        if(StringUtils.isEmpty(app))
            return false;

       return repository.findBySiglaApp(app).isPresent();
    }


    @Override
    public Optional<Authorization> getByClientIdAndClientSecret(String clientId, String clientSecret) {

        if(Objects.isNull(clientId) || Objects.isNull(clientSecret))
            return Optional.empty();

        Optional<br.com.group.developer.corporation.service.collaborator.jpa.entity.Authorization> entity = repository.findByClientIdAndClientSecret(clientId, clientSecret);

        return entity.map(AuthorizationMapper.INSTANCE::toDto);
    }
}
