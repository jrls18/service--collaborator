package br.com.developcorporation.collaborator.domain.infrastructure;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

public class RequestContext extends HashMap<String, Object> implements Context {

    private static final long serialVersionUID = 1550729560244970202L;

    public static final String UUID_REGEX = "[0-9a-fA-F]{8}(?:-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}";

    public static final Pattern PATTERN = Pattern.compile(UUID_REGEX);


    private  String clientId;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    private String clientSecret;

    private final boolean verifyCorrelationFormat;

    private Optional<String> correlationId = Optional.empty();

    private final String  currentCorrelationId = UUID.randomUUID().toString();

    private String method;

    private String instanceID;

    private Map<String, Object> map = new HashMap<>();

    private Map<String, String> queryParameters;

    private Map<String, String> headers;

    @Override
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getRequestUri() {
        return requestUri;
    }

    @Override
    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    @Override
    public Map<String, String> getRequestUriParameterString() {
        return queryParameters;
    }

    @Override
    public void setRequestUriParameterString(Map<String, String> parameterString) {
       this.queryParameters = parameterString;
    }

    @Override
    public Map<String, String> getRequestUriHeaders() {
        return this.headers;
    }

    @Override
    public void setRequestUriHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public void setInstanceId(String instanceId) {
        this.instanceID = instanceId;
    }

    @Override
    public String getInstanceId() {
        return instanceID;
    }

    @Override
    public void setMap(String key, Object value) {
        map.put(key, value);
    }

    @Override
    public Map<String, Object> getMap() {
        return map;
    }

    private String requestUri;
    private String applicationName;

    public RequestContext(boolean verifyCorrelationFormat){
        this.verifyCorrelationFormat = verifyCorrelationFormat;
    }

    public RequestContext(){
        this(false);
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public String getApplicationName() {
        return this.applicationName;
    }

    @Override
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public String getCorrelationId() {
        return  isPresent() && isVerify() ? correlationId.get() : currentCorrelationId;
    }

    @Override
    public void setCorrelationId(final String correlationId) {
        this.correlationId = Optional.ofNullable(correlationId);
    }



    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }


    private boolean isPresent(){
        return correlationId.isPresent() && StringUtils.isNotBlank(correlationId.get());
    }

    private boolean isVerify(){
        return !verifyCorrelationFormat || PATTERN.matcher(correlationId.get()).matches();
    }


}
