package br.com.developcorporation.collaborator.services.internal.service.httpUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HttpUtil {

    @Value(value = "${collaborator.client_id}")
    private String clientId;

    @Value(value = "${collaborator.client_secret}")
    private String clientSecret;

    public HttpHeaders getHeader(){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("client_id", clientId);
        headers.set("client_secret", clientSecret);
        headers.set("correlation_id", UUID.randomUUID().toString());

        return headers;
    }

    public HttpEntity<Void> getRequestEntityBase(){
        return new HttpEntity<>(this.getHeader());
    }
}
