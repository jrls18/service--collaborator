package br.com.developcorporation.collaborator.rest.exception.handler;


import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.exception.error.*;
import br.com.developcorporation.collaborator.rest.mapper.MessageMapper;
import br.com.developcorporation.collaborator.core.infrastructure.ContextHolder;
import br.com.developcorporation.collaborator.domain.exception.DomainException;
import br.com.developcorporation.collaborator.rest.exception.error.*;
import br.com.developcorporation.collaborator.rest.message.response.MessageResponse;
import br.com.developcorporation.lib.commons.monitorable.SpringLogger;
import br.com.developcorporation.lib.commons.util.Convert;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @SneakyThrows
    private SpringLogger springLogger(final Object value, final String info, final String statusCode, final Exception ex){
      return  new SpringLogger(
                ContextHolder.get().getCorrelationId(),
                "service--company",
                ContextHolder.get().getMethod(),
                ContextHolder.get().getRequestUri(),
                value,
                ex,
                ContextHolder.get().getInstanceId(),
                info,
                statusCode
        );
    }

    @SneakyThrows
    private void setLogger(final Object value, final String info, final String statusCode, final Exception ex){
        LOG.warn(MessageConstant.RESPOSTA, Convert.toJson(

               new SpringLogger(
                       ContextHolder.get().getCorrelationId(),
                       "service--company",
                       ContextHolder.get().getMethod(),
                       ContextHolder.get().getRequestUri(),
                       value,
                       ex,
                       ContextHolder.get().getInstanceId(),
                       info,
                       statusCode
                       )
        ));
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<MessageResponse> handleUserNotFoundException
            (RecordNotFoundException ex, WebRequest request) throws JsonProcessingException {
        MessageResponse error = new MessageResponse(String.valueOf(HttpStatus.NOT_FOUND.value()), null, ex.getLocalizedMessage(), null);

        setLogger(error, "WARN",  HttpStatus.NOT_FOUND.toString(), ex);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


   // ((DomainException) ex).getCode()


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws JsonProcessingException {

            List<String> details = new ArrayList<>();
            details.add(ex.getLocalizedMessage());
            MessageResponse error = new MessageResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), null,MessageConstant.HOUVE_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE, null);

            LOG.warn(MessageConstant.RESPOSTA, Convert.toJson(
                    springLogger(error, "ERROR",HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex)
            ));

            return genericResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
       }

    @ExceptionHandler(DomainException.class)
    public final ResponseEntity<Object> handleAll(Exception ex, WebRequest request) throws JsonProcessingException {

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

        LOG.warn(MessageConstant.RESPOSTA, Convert.toJson(
                springLogger(messageResponse, info,httpStatus.toString(), ex)
        ));

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

        setLogger(error, "WARN",  HttpStatus.BAD_REQUEST.toString(), ex);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public final ResponseEntity<Object> handleUnprocessableEntityExceptions(UnprocessableEntityException ex) throws JsonProcessingException {

        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();
        for (MessageResponse.DetailsResponse line : ex.getDetailsResponseList()) {
            detailsResponses.add(new MessageResponse.DetailsResponse(line.getField(), line.getMessage(), line.getValue()));
        }

        MessageResponse error = new MessageResponse(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()),null, ex.getMessage(), detailsResponses);

        setLogger(error, "WARN",  HttpStatus.UNPROCESSABLE_ENTITY.toString(), ex);

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

        setLogger(error, "WARN",  HttpStatus.BAD_REQUEST.toString(), ex);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<Object> handleBadRequestEntityException(UnauthorizedException ex) throws JsonProcessingException {

        MessageResponse error = new MessageResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()),null, ex.getMessage(), null);

        LOG.warn(MessageConstant.RESPOSTA, Convert.toJson(error) );

        setLogger(error, "WARN",  HttpStatus.UNAUTHORIZED.toString(), ex);

        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex)   {

        LOG.warn(MessageConstant.RESPOSTA, ex.getMessage() );

        setLogger(ex.getResponseBodyAsString(), "WARN", String.valueOf(ex.getStatusCode().value()), ex);

        return new ResponseEntity<>(ex.getResponseBodyAsString(),ex.getStatusCode());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        MessageResponse error = new MessageResponse( String.valueOf(HttpStatus.BAD_REQUEST.value()),null, MessageConstant.FALHA_NA_VALIDACAO, null);

        setLogger(error, "WARN",  HttpStatus.BAD_REQUEST.toString(), ex);

        return genericResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> genericResponseEntity(Object obj, HttpStatus httpStatus){
        return new ResponseEntity<>(obj, httpStatus);
    }

}
