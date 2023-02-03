package br.com.developcorporation.collaborator.core.validation;

import br.com.developcorporation.collaborator.core.enums.CoreEnum;
import br.com.grupo.developer.corporation.libcommons.exception.DomainException;
import br.com.grupo.developer.corporation.libcommons.message.Message;
import br.com.grupo.developer.corporation.libcommons.utils.UseFul;


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
