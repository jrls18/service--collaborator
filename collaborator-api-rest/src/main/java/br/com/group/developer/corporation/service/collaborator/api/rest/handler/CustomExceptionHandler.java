package br.com.group.developer.corporation.service.collaborator.api.rest.handler;


import br.com.group.developer.corporation.lib.logger.logger.LoggerService;
import br.com.group.developer.corporation.libauthentication.exceptions.BadRequestAuthenticationException;
import br.com.group.developer.corporation.libauthentication.exceptions.InternalServerErrorAuthenticationException;
import br.com.group.developer.corporation.libauthentication.exceptions.NoAuthenticationException;
import br.com.group.developer.corporation.libparametrizador.exceptions.BadRequestParameterizeException;
import br.com.group.developer.corporation.libparametrizador.exceptions.KeyNaoPodeSerNulaOuVaziaException;
import br.com.group.developer.corporation.libparametrizador.exceptions.NaoExisteMockConfiguradoException;
import br.com.group.developer.corporation.libparametrizador.exceptions.NaoExisteParametroConfiguradoNoParametrizadorException;
import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.constants.MessageDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.exception.BusinessErrorException;
import br.com.group.developer.corporation.service.collaborator.domain.exception.CompanyInternalServerErrorException;
import br.com.group.developer.corporation.service.collaborator.domain.exception.InternalServerErrorException;
import br.com.grupo.developer.corporation.libcommons.exception.BadRequestEntityException;
import br.com.grupo.developer.corporation.libcommons.exception.DomainException;
import br.com.grupo.developer.corporation.libcommons.exception.RecordNotFoundException;
import br.com.grupo.developer.corporation.libcommons.exception.UnauthorizedException;
import br.com.grupo.developer.corporation.libcommons.mapper.MessageMapper;
import br.com.grupo.developer.corporation.libcommons.message.response.MessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
@AllArgsConstructor
@Component
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final LoggerService loggerService;

    @ExceptionHandler({ConnectException.class,
            CompanyInternalServerErrorException.class,
            InternalServerErrorException.class,
            InternalServerErrorAuthenticationException.class,
            WebClientRequestException.class})
    public final ResponseEntity<MessageResponse> handleInternalServerErrorException(RuntimeException e){
        MessageResponse error = new MessageResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), null, e.getMessage(), null);

        loggerService.error(error, HttpStatus.INTERNAL_SERVER_ERROR.name(),null);

        error.setMessage(MessageDomainConstants.OCORREU_UM_ERRO_INTERNO_TENTE_NOVAMENTE_MAIS_TARDE);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({BusinessErrorException.class, BadRequestAuthenticationException.class})
    public final ResponseEntity<Object> handleBusinessErrorException(RuntimeException e) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> messageResponse =  mapper.readValue(e.getMessage(), Map.class);

        if(String.valueOf(HttpStatus.BAD_REQUEST.value()).equals(messageResponse.get(FieldDomainConstants.CODIGO))){
            loggerService.warning(messageResponse,HttpStatus.BAD_REQUEST.name());
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }else if(String.valueOf(HttpStatus.UNAUTHORIZED.value()).equals(messageResponse.get(FieldDomainConstants.CODIGO))){
            loggerService.warning(messageResponse,HttpStatus.UNAUTHORIZED.name());
            return new ResponseEntity<>(messageResponse, HttpStatus.UNAUTHORIZED);
        } else{
            loggerService.warning(messageResponse,HttpStatus.UNPROCESSABLE_ENTITY.name());
            return new ResponseEntity<>(messageResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    @ExceptionHandler({NoAuthenticationException.class})
    public final ResponseEntity<Object> handleNoAuthenticationException(NoAuthenticationException e) throws JsonProcessingException {

        loggerService.warning(e.getMessageResponse(),HttpStatus.UNAUTHORIZED.name());
        return new ResponseEntity<>(e.getMessageResponse(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<MessageResponse> handleUserNotFoundException
            (RecordNotFoundException ex)  {

        MessageResponse error = new MessageResponse(String.valueOf(HttpStatus.NOT_FOUND.value()), null, ex.getLocalizedMessage(), null);

        loggerService.warning(error,HttpStatus.NOT_FOUND.name());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
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
                MessageMapper.INSTANCE.toDetailsResponseList(((DomainException) ex).getDetails()));

        if(info.equalsIgnoreCase("ERROR"))
            loggerService.error(messageResponse,httpStatus.name(),ex);
        else
            loggerService.warning(messageResponse,httpStatus.name());

        return new ResponseEntity<>(messageResponse, httpStatus);
    }



    @ExceptionHandler(br.com.grupo.developer.corporation.libcommons.exception.MissingHeaderInfoException.class)
    public final ResponseEntity<Object> handleUnprocessableEntityExceptions(br.com.grupo.developer.corporation.libcommons.exception.MissingHeaderInfoException ex) throws JsonProcessingException {

        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();

        if(ex.getDetailsResponse() != null){
            for (MessageResponse.DetailsResponse line : ex.getDetailsResponse()) {
                detailsResponses.add(new MessageResponse.DetailsResponse(line.getField(), line.getMessage(), line.getValue()));
            }
        }

        MessageResponse response = new MessageResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),null, ex.getMessage(),  detailsResponses);

        loggerService.warning(response,HttpStatus.BAD_REQUEST.name());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({br.com.grupo.developer.corporation.libcommons.exception.UnprocessableEntityException.class})
    public final ResponseEntity<Object> handleUnprocessableEntityExceptions(br.com.grupo.developer.corporation.libcommons.exception.UnprocessableEntityException ex) throws JsonProcessingException {

        List<MessageResponse.DetailsResponse> detailsResponses = new ArrayList<>();
        for (MessageResponse.DetailsResponse line : ex.getDetailsResponseList()) {
            detailsResponses.add(new MessageResponse.DetailsResponse(line.getField(), line.getMessage(), line.getValue()));
        }

        MessageResponse response = new MessageResponse(String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()),null, ex.getMessage(), detailsResponses);

        loggerService.warning(response,HttpStatus.UNPROCESSABLE_ENTITY.name());

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({BadRequestEntityException.class})
    public final ResponseEntity<Object> handleBadRequestEntityException(BadRequestEntityException ex) throws JsonProcessingException {

        List<MessageResponse.DetailsResponse> details = new ArrayList<>();

        if(ex.getDetailsResponseList() != null){
            for (MessageResponse.DetailsResponse line : ex.getDetailsResponseList()) {
                details.add(new MessageResponse.DetailsResponse(line.getField(), line.getMessage(), line.getValue()));
            }
        }

        MessageResponse response = new MessageResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),null, ex.getMessage(), details);

        loggerService.warning(response,HttpStatus.BAD_REQUEST.name());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<Object> handleBadRequestEntityException(UnauthorizedException ex)  {

        MessageResponse response = new MessageResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()),null, ex.getMessage(), null);

        loggerService.warning(response,HttpStatus.UNAUTHORIZED.name());

        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({KeyNaoPodeSerNulaOuVaziaException.class,
            NaoExisteParametroConfiguradoNoParametrizadorException.class,
            NaoExisteMockConfiguradoException.class,
            BadRequestParameterizeException.class
    })
    public final ResponseEntity<Object> handleParameterizerException(RuntimeException ex) {

        MessageResponse response = new MessageResponse(
                String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()),
                null, ex.getMessage(), null);

        loggerService.warning(response,HttpStatus.UNPROCESSABLE_ENTITY.name());

        return new ResponseEntity<>(response,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public final ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex)   {

        loggerService.warning(ex.getResponseBodyAsString(),ex.getStatusCode().toString());

        return new ResponseEntity<>(ex.getResponseBodyAsString(),ex.getStatusCode());
    }
}
