package br.com.developcorporation.collaborator.rest.exception.error;

import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingHeaderInfoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private List<MessageResponse.DetailsResponse> detailsResponses;

    public MissingHeaderInfoException(final String message, final List<MessageResponse.DetailsResponse> details) {
        super(message);
        this.detailsResponses = details;
    }

    public MissingHeaderInfoException(final String message){
        super(message);
    }

    public List<MessageResponse.DetailsResponse> getDetailsResponse() {
        return detailsResponses;
    }
}
