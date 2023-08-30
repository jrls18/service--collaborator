package br.com.group.developer.corporation.service.collaborator.domain.exception;

public class CompanyInternalServerErrorException extends RuntimeException {
    private static final long serialVersionUID = 7677109049488920793L;

    public CompanyInternalServerErrorException(final String message){
        super(message);
    }
}
