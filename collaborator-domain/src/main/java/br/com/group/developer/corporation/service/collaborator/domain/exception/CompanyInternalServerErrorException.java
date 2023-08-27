package br.com.group.developer.corporation.service.collaborator.domain.exception;

public class CompanyInternalServerErrorException extends RuntimeException {
    public CompanyInternalServerErrorException(final String message){
        super(message);
    }
}
