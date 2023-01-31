package br.com.developcorporation.collaborator.core.mock;

import br.com.developcorporation.collaborator.domain.model.Authorization;

import java.time.LocalDateTime;

public final class AuthorizationMock {

    private AuthorizationMock(){

    }

    public static Authorization getAuthorization(){
        Authorization authorization = new Authorization();
        authorization.setId(1L);
        authorization.setApplicationName("service--EAP");
        authorization.setClientId("a9d53133-981b-4d09-88be-7d643b7dfeea");
        authorization.setClientSecret("b85736f7-235e-4d5e-8342-98dad9d1db9d");
        authorization.setDateRegister(LocalDateTime.now());
        authorization.setSiglaApp("SERVICE_EAP");
        authorization.setStatus(StatusMock.getStatus());
        return authorization;
    }
}
