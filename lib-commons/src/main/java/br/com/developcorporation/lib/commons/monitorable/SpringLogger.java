package br.com.developcorporation.lib.commons.monitorable;

import br.com.developcorporation.lib.commons.constants.OtherConstants;
import br.com.developcorporation.lib.commons.service.CryptographyService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpringLogger implements Serializable {

    private static final long serialVersionUID = 7801264312129230930L;

    private String correlationId;
    private String applicationName;
    private String method;
    private String requestUri;
    private Object payload;
    private String exception;
    private Exception exceptionDetails;
    private String instance;
    private String info;
    private String statusCode;

    private String message;

    private String date = DateTimeFormatter.ofPattern(OtherConstants.DATA_FORMAT).format(LocalDateTime.now());

    public SpringLogger(
            final String correlationId,
            final String applicationName,
            final String method,
            final String requestUri,
            final Object payload,
            final String ex,
            final String instance,
            final String info,
            final String statusCode,
            final String message) {
        this.correlationId = correlationId;
        this.applicationName = applicationName;
        this.method = method;
        this.requestUri = requestUri;
        this.payload = payload;
        this.exception = ex;
        this.exceptionDetails = null;
        this.instance = instance;
        this.info = info;
        this.statusCode = statusCode;
        this.message = message;
    }

    public SpringLogger(
            final String correlationId,
            final String applicationName,
            final String method,
            final String requestUri,
            final Object payload,
            final Exception ex,
            final String instance,
            final String info,
            final String statusCode) {
        this.correlationId = correlationId;
        this.applicationName = applicationName;
        this.method = method;
        this.requestUri = requestUri;
        this.payload = CryptographyService.mapCryptography(payload);
        this.exception = ex.toString();
        this.exceptionDetails = ex;
        this.instance = instance;
        this.info = info;
        this.statusCode = statusCode;
    }
}