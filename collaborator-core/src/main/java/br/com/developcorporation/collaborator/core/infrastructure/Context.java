package br.com.developcorporation.collaborator.core.infrastructure;

import java.util.Map;

public interface Context extends Map<String, Object> {

    String getClientId();

    String getCorrelationId();

    void setCorrelationId(String correlationId);

    String getClientSecret();

    String getMethod();

    String getRequestUri();

    void setRequestUri(final String correlationId);

    void setInstanceId(final String instanceId);

    String getInstanceId();

    void setMap(final String key, final Object value);

    Map<String, Object> getMap();
}
