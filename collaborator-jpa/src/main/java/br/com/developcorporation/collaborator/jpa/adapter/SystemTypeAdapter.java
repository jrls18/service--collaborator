package br.com.developcorporation.collaborator.jpa.adapter;

import br.com.developcorporation.collaborator.jpa.mapper.SystemTypeMapper;
import br.com.developcorporation.collaborator.domain.model.SystemType;
import br.com.developcorporation.collaborator.domain.port.SystemTypePort;
import br.com.developcorporation.collaborator.jpa.repository.SystemTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SystemTypeAdapter implements SystemTypePort {

    private final SystemTypeRepository repository;

    @Override
    public SystemType getById(Long id) {
        Optional<br.com.developcorporation.collaborator.jpa.entity.SystemType> systemTypeOptional = this.repository.findById(id);

        if(systemTypeOptional.isEmpty())
            return null;

        return SystemTypeMapper.INSTANCE.toDto(systemTypeOptional.get());
    }

    @Override
    public List<SystemType> getByAll() {
        List<br.com.developcorporation.collaborator.jpa.entity.SystemType> systemTypeOptional = this.repository.findAll();

        if(systemTypeOptional.isEmpty())
            return null;

        return SystemTypeMapper.INSTANCE.toEntityList(systemTypeOptional);
    }
}
