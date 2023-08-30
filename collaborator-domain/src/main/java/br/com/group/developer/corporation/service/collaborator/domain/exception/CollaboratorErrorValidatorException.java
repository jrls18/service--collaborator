package br.com.group.developer.corporation.service.collaborator.domain.exception;

import br.com.grupo.developer.corporation.libcommons.exception.DomainException;
import br.com.grupo.developer.corporation.libcommons.message.Message;

import java.util.List;

public class CollaboratorErrorValidatorException extends DomainException {
    private static final long serialVersionUID = 5952608748452583657L;

    public CollaboratorErrorValidatorException(String code,
                                               String message,
                                               List<Message.Details> details) {
        super(code, message, details);
    }
}
