package br.com.developcorporation.collaborator.rest.exception.error;

import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private List<MessageResponse.DetailsResponse> detailsResponseList;

    public UnprocessableEntityException(final String message, final List<MessageResponse.DetailsResponse> detailsResponses){
        super(message);
        this.detailsResponseList = detailsResponses;
    }

    public List<MessageResponse.DetailsResponse> getDetailsResponseList(){
        return this.detailsResponseList;
    }
}
