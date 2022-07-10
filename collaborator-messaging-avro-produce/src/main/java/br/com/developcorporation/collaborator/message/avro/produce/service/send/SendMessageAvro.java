package br.com.developcorporation.collaborator.message.avro.produce.service.send;

import org.apache.avro.specific.SpecificRecord;

public interface SendMessageAvro<T extends SpecificRecord> {

     void sendMassage(T classSpecificRecord);
}
