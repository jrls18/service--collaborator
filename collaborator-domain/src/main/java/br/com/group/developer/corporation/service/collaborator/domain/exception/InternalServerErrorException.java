package br.com.group.developer.corporation.service.collaborator.domain.exception;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(final String message){
        super(message);
    }
}
