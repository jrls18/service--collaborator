package br.com.developcorporation.collaborator.rest.exception.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CallApiException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1264139410081274626L;

    public String getError() {
        return error;
    }

    private String error;
    public CallApiException(final String message, String ex){
        super(message);
        this.error = ex;
    }

}
