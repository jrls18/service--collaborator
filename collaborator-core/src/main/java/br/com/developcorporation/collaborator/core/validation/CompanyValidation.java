package br.com.developcorporation.collaborator.core.validation;

import br.com.developcorporation.collaborator.core.constants.FieldConstants;
import br.com.developcorporation.collaborator.core.constants.MessageConstants;
import br.com.developcorporation.collaborator.core.enums.CoreEnum;
import br.com.developcorporation.collaborator.core.mapper.MessageMapper;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.message.Message;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.lib.commons.validation.Validation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CompanyValidation implements BaseValidator<Collaborator>{


    @Override
    public void add(Collaborator value) {

    }

    @Override
    public void update(Collaborator value) {

    }
}
