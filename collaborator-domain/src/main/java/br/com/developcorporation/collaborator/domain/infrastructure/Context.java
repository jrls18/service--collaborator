package br.com.developcorporation.collaborator.domain.infrastructure;

import java.util.Map;

public interface Context extends Map<String, Object> {

    String getClientId();

    String getApplicationName();

    void setApplicationName(final String applicationName);

    String getCorrelationId();

    void setCorrelationId(String correlationId);

    String getClientSecret();

    String getMethod();

    String getRequestUri();
    void setRequestUri(final String correlationId);

    Map<String, String> getRequestUriParameterString();

    void setRequestUriParameterString(final Map<String, String> parameterString);

    Map<String, String> getRequestUriHeaders();

    void setRequestUriHeaders(final Map<String, String> headers);


    void setInstanceId(final String instanceId);

    String getInstanceId();

    void setMap(final String key, final Object value);

    Map<String, Object> getMap();
}
