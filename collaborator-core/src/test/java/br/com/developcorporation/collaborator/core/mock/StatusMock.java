package br.com.developcorporation.collaborator.core.mock;

import br.com.developcorporation.collaborator.domain.model.Status;

import java.time.LocalDateTime;

public final class StatusMock {

    private StatusMock(){}

    public static Status getStatus(){
        Status status = new Status();
        status.setId(1L);
        status.setDateRegister(LocalDateTime.now());
        status.setDescription("Ativo");
        return status;
    }
}
