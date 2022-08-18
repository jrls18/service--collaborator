package br.com.developcorporation.collaborator.jpa.adapter;

import br.com.developcorporation.collaborator.jpa.mapper.StatusMapper;
import br.com.developcorporation.collaborator.domain.model.Status;
import br.com.developcorporation.collaborator.domain.port.StatusPort;
import br.com.developcorporation.collaborator.jpa.repository.StatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StatusAdapter implements StatusPort {

    private final StatusRepository  repository;

    @Override
    public Status getById(Long id) {

        Optional<br.com.developcorporation.collaborator.jpa.entity.Status> statusOptional = this.repository.findById(id);

        if(statusOptional.isEmpty())
            return null;

        return StatusMapper.INSTANCE.toDto(statusOptional.get());
    }

    @Override
    public List<Status> getByAll() {

        List<br.com.developcorporation.collaborator.jpa.entity.Status> statusOptional = this.repository.findAll();

        if(statusOptional.isEmpty())
            return null;

        return StatusMapper.INSTANCE.toEntityList(statusOptional);
    }
}
