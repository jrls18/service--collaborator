package br.com.developcorporation.collaborator.rest.validation;

import br.com.developcorporation.collaborator.rest.exception.error.BadRequestEntityException;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import br.com.developcorporation.lib.commons.util.UseFul;

import java.util.List;
import java.util.Objects;

public interface BaseValidator<T>  {

    void addRequestValidation(final T value);

    void updateRequestValidation(final T value);

    default void throwBadRequestGeneric(final List<MessageResponse.DetailsResponse> detailsResponseList, final String message) {
        if(Objects.nonNull(detailsResponseList)){
            List<MessageResponse.DetailsResponse> detailsResponses =
                    UseFul.removeNullToList(detailsResponseList);

            if(!detailsResponses.isEmpty())
                throw new BadRequestEntityException(message, detailsResponses);
        }
    }
}
