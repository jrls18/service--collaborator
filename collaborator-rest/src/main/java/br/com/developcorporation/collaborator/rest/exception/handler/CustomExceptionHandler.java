package br.com.developcorporation.collaborator.rest.exception.handler;


import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.exception.error.*;
import br.com.developcorporation.collaborator.rest.mapper.MessageMapper;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;

import br.com.grupo.developer.corporation.lib.logger.logger.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@AllArgsConstructor
@Component
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<MessageResponse> handleUserNotFoundException
            (RecordNotFoundException ex)  {

        MessageResponse error = new MessageResponse(String.valueOf(HttpStatus.NOT_FOUND.value()), null, ex.getLocalizedMessage(), null);

        log.warn(MessageConstant.RESPOSTA, Logger.severe(error,"WARN", String.valueOf(HttpStatus.NOT_FOUND.value()), ex ));

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {

            List<String> details = new ArrayList<>();

            details.add(ex.getLocalizedMessage());

            MessageResponse error = new MessageResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), null,MessageConstant.HOUVE_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE, null);

            log.error(MessageConstant.RESPOSTA, Logger.severe(error,"ERROR", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex ));

            return genericResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DomainException.class)
    public final ResponseEntity<Object> handleAll(Exception ex) {

        ResponseEntity<Object> responseEntity = null;

       switch (((DomainException) ex).getCode()){
           case "401":
               responseEntity = customDomainException("WARN", HttpStatus.UNAUTHORIZED, ex);
               break;
           case "400":
               responseEntity = customDomainException("WARN", HttpStatus.BAD_REQUEST, ex);
               break;
           case "422":
               responseEntity = customDomainException("WARN", HttpStatus.UNPROCESSABLE_ENTITY, ex);
               break;
           default:
               responseEntity = customDomainException("ERROR", HttpStatus.INTERNAL_SERVER_ERROR, ex);

       }
       return responseEntity;
    }

    private ResponseEntity<Object> customDomainException(String info ,HttpStatus httpStatus, Exception ex){

        MessageResponse messageResponse = new MessageResponse(
                String.valueOf(httpStatus.value()),
                null,
                ((DomainException) ex).getMessage(),
                MessageMapper.INSTANCE.domainToDetailsResponseList(((DomainException) ex).getDetails()));

       if(info.equalsIgnoreCase("ERROR"))
           log.warn(MessageConstant.RESPOSTA, Logger.severe(messageResponse,info, String.valueOf(httpStatus.value()), ex));
       else
           log.warn(MessageConstant.RESPOSTA, Logger.severe(messageResponse,info,String.valueOf( httpStatus.value()), null));

        return new ResponseEntity<>(messageResponse, httpStatus);
    }



    @ExceptionHandler(MissingHeaderInfoException.class)
    public final ResponseEntity<Object> handleUnprocessableEntityExceptions(MissingHeaderInfoException ex) throws JsonProcessingException {

        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

       if(ex.getDetailsResponse() != null){
           for (MessageResponse.DetailsResponse line : ex.getDetailsResponse()) {
               detailsResponses.add(new MessageResponse.DetailsResponse(line.getField(), line.getMessage(), line.getValue()));
           }
       }

        MessageResponse error = new MessageResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),null, ex.getMessage(),  detailsResponses);

        log.warn(MessageConstant.RESPOSTA, Logger.severe(error,"WARN", String.valueOf( HttpStatus.BAD_REQUEST.value()), null));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public final ResponseEntity<Object> handleUnprocessableEntityExceptions(UnprocessableEntityException ex) throws JsonProcessingException {

        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();
        for (MessageResponse.DetailsResponse line : ex.getDetailsResponseList()) {
            detailsResponses.add(new MessageResponse.DetailsResponse(line.getField(), line.getMessage(), line.getValue()));
        }

        MessageResponse error = new MessageResponse(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()),null, ex.getMessage(), detailsResponses);

        log.warn(MessageConstant.RESPOSTA, Logger.severe(error,"WARN", String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()), null));

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BadRequestEntityException.class)
    public final ResponseEntity<Object> handleBadRequestEntityException(BadRequestEntityException ex) throws JsonProcessingException {

        List<MessageResponse.DetailsResponse> details = new ArrayList<>();

        if(ex.getDetailsResponseList() != null){
            for (MessageResponse.DetailsResponse line : ex.getDetailsResponseList()) {
                details.add(new MessageResponse.DetailsResponse(line.getField(), line.getMessage(), line.getValue()));
            }
        }

        MessageResponse error = new MessageResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),null, ex.getMessage(), details);

        log.warn(MessageConstant.RESPOSTA, Logger.severe(error,"WARN", String.valueOf(HttpStatus.BAD_REQUEST.value()), null));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<Object> handleBadRequestEntityException(UnauthorizedException ex) throws JsonProcessingException {

        MessageResponse error = new MessageResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()),null, ex.getMessage(), null);

        log.warn(MessageConstant.RESPOSTA, Logger.severe(error,"WARN", String.valueOf(HttpStatus.UNAUTHORIZED.value()), null));

        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex)   {

        log.warn(MessageConstant.RESPOSTA, Logger.severe(ex.getResponseBodyAsString(),"WARN", String.valueOf(ex.getStatusCode().value()), ex));

        return new ResponseEntity<>(ex.getResponseBodyAsString(),ex.getStatusCode());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        MessageResponse error = new MessageResponse( String.valueOf(HttpStatus.BAD_REQUEST.value()),null, MessageConstant.FALHA_NA_VALIDACAO, null);

        log.warn(MessageConstant.RESPOSTA, Logger.severe(error,"WARN", String.valueOf(HttpStatus.BAD_REQUEST.value()), ex));

        return genericResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> genericResponseEntity(Object obj, HttpStatus httpStatus) {
        return new ResponseEntity<>(obj, httpStatus);
    }
}
