package br.com.group.developer.corporation.service.collaborator.domain.exception;

public class BusinessErrorException extends RuntimeException {
    public BusinessErrorException(final String message){
        super(message);
    }
}
