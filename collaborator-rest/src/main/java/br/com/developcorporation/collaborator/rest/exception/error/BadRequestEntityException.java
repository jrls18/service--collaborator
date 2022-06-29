package br.com.developcorporation.collaborator.rest.exception.error;

import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestEntityException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 8596015964298545306L;

    private final List<MessageResponse.DetailsResponse> detailsResponseList;

    public BadRequestEntityException(final String message,final List<MessageResponse.DetailsResponse> detailsResponses){
        super(message);
        this.detailsResponseList = detailsResponses;
    }

    public List<MessageResponse.DetailsResponse> getDetailsResponseList(){
        return this.detailsResponseList;
    }
}
