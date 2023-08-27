package br.com.group.developer.corporation.service.collaborator.jpa.adapter;


import br.com.group.developer.corporation.service.collaborator.domain.model.Status;
import br.com.group.developer.corporation.service.collaborator.domain.port.StatusPort;
import br.com.group.developer.corporation.service.collaborator.jpa.mapper.StatusMapper;
import br.com.group.developer.corporation.service.collaborator.jpa.repository.StatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class StatusAdapter implements StatusPort {

    private final StatusRepository repository;

    @Override
    public Status getById(Long id) {

        if(Objects.isNull(id) || id <= 0)
            return null;

        Optional<br.com.group.developer.corporation.service.collaborator.jpa.entity.Status> statusOptional = this.repository.findById(id);

        return statusOptional.map(StatusMapper.INSTANCE::toDto).orElse(null);

    }

    @Override
    public List<Status> getByAll() {

        List<br.com.group.developer.corporation.service.collaborator.jpa.entity.Status> statusOptional = this.repository.findAll();

        if(statusOptional.isEmpty())
            return Collections.emptyList();

        return StatusMapper.INSTANCE.toEntityList(statusOptional);
    }
}
