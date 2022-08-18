package br.com.developcorporation.collaborator.core.validation;

import br.com.developcorporation.collaborator.core.enums.CoreEnum;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.domain.message.Message;

import br.com.developcorporation.lib.commons.util.UseFul;

import java.util.List;
import java.util.Objects;

public interface BaseValidator<T>  {

    void add(final T value);

    void update(final T value);

    default void throwDomainExceptionGeneric(final CoreEnum coreEnum, final String message, final List<Message.Details> detailsList) {
        if(Objects.nonNull(detailsList)){
            List<Message.Details> detailsResponses =
                    UseFul.removeNullToList(detailsList);

            if(!detailsResponses.isEmpty())
                throw new DomainException(coreEnum.getCode(), message, detailsResponses);
        }
    }
}
