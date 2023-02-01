package br.com.developcorporation.collaborator.services.internal.service.httpUtils;

import br.com.grupo.developer.corporation.lib.spring.context.holder.infrastructure.ContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

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
        headers.set("correlation_id", ContextHolder.get().getCorrelationId());

        return headers;
    }

    public HttpEntity<Void> getRequestEntityBase(){
        return new HttpEntity<>(this.getHeader());
    }
}
