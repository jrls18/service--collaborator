package br.com.developcorporation.collaborator.domain.port;

import br.com.developcorporation.collaborator.domain.model.Company;

public interface SendMessagePort {
    void send(Company dto);
}
