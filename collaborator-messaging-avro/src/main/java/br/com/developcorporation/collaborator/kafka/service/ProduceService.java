package br.com.developcorporation.collaborator.kafka.service;

import br.com.developcorporation.company.message.avro.User;

public interface ProduceService {
    void sendMassage(User user);
}
