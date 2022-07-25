package br.com.developcorporation.collaborator.core.validation;

import br.com.developcorporation.collaborator.domain.model.Collaborator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CollaboratorValidation implements BaseValidator<Collaborator>{


    @Override
    public void add(Collaborator value) {

    }

    @Override
    public void update(Collaborator value) {

    }
}
