package br.com.group.developer.corporation.service.collaborator.domain.exception;

public class DocumentInternalServerErrorException extends Exception {
    private static final long serialVersionUID = -4377783663728304980L;

    public DocumentInternalServerErrorException(final String message){
        super(message);
    }
}
