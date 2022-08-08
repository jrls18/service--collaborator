package br.com.developcorporation.collaborator.domain.exception;

import br.com.developcorporation.collaborator.domain.message.Message;
import lombok.Getter;

import java.util.List;

@Getter
public class DomainException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private List<Message.Details> details;
    private String message;
    private String code;

    public DomainException(final String code, final String message, final List<Message.Details> details){
        this.message = message;
        this.details = details;
        this.code = code;
    }

}
