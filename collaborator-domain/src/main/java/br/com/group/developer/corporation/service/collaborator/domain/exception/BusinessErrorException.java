package br.com.group.developer.corporation.service.collaborator.domain.exception;

public class BusinessErrorException extends RuntimeException {
    private static final long serialVersionUID = 2513134763657204769L;

    public BusinessErrorException(final String message){
        super(message);
    }
}
