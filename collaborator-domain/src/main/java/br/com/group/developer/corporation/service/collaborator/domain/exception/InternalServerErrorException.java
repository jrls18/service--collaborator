package br.com.group.developer.corporation.service.collaborator.domain.exception;

public class InternalServerErrorException extends RuntimeException {
    private static final long serialVersionUID = -7501314447979886448L;

    public InternalServerErrorException(final String message){
        super(message);
    }
}
