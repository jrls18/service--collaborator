package br.com.group.developer.corporation.service.collaborator.internal.call.config;

import br.com.group.developer.corporation.service.collaborator.domain.exception.BusinessErrorException;
import br.com.group.developer.corporation.service.collaborator.domain.exception.CompanyInternalServerErrorException;
import br.com.grupo.developer.corporation.libcommons.constants.FieldAssistantConstants;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Value(value = "${properties.configMap.clientId}")
    private String clientId;

    @Value(value = "${properties.configMap.clientSecret}")
    private String clientSecret;

    @Bean
    public WebClient getWebClient()
    {
        HttpClient httpClient = HttpClient.create()
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(10))
                        .addHandlerLast(new WriteTimeoutHandler(10)));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(FieldAssistantConstants.CLIENT_ID,this.clientId)
                .defaultHeader(FieldAssistantConstants.CLIENT_SECRET,this.clientSecret)
                .filter(errorFilterHandler())
                .build();
    }

    public static ExchangeFilterFunction errorFilterHandler() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().is5xxServerError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new CompanyInternalServerErrorException(errorBody)));
            } else if (clientResponse.statusCode().is4xxClientError()) {
                return clientResponse.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new BusinessErrorException(errorBody)));
            } else {
                return Mono.just(clientResponse);
            }
        });
    }
}
