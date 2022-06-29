package br.com.developcorporation.collaborator.jpa.adapter;

import br.com.developcorporation.collaborator.domain.model.FollowUp;
import br.com.developcorporation.collaborator.domain.port.FollowUpPort;
import br.com.developcorporation.collaborator.jpa.mapper.FollowUpMapper;
import br.com.developcorporation.collaborator.jpa.repository.FollowUpRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FollowUpAdapter implements FollowUpPort {

    FollowUpRepository repository;

    @Override
    public FollowUp getById(Long id) {

        Optional<br.com.developcorporation.collaborator.jpa.entity.FollowUp> followUpOptional = this.repository.findById(id);

        if(followUpOptional.isEmpty())
            return null;

        return FollowUpMapper.INSTANCE.toDto(followUpOptional.get());
    }

    @Override
    public List<FollowUp> getByAll() {
        List<br.com.developcorporation.collaborator.jpa.entity.FollowUp> followUpOptional = this.repository.findAll();

        if(followUpOptional.isEmpty())
            return null;

        return FollowUpMapper.INSTANCE.toEntityList(followUpOptional);
    }
}
