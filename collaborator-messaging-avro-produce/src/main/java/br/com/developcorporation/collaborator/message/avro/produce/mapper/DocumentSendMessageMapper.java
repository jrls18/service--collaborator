package br.com.developcorporation.collaborator.message.avro.produce.mapper;

import br.com.developcorporation.collaborator.domain.infrastructure.ContextHolder;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.documents.message.avro.DocumentsMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;

@Mapper
public interface DocumentSendMessageMapper {

    DocumentSendMessageMapper INSTANCE = Mappers.getMapper(DocumentSendMessageMapper.class);

    @Mapping(source = "id", target = "idUser")
    @Mapping(source = "idCompany", target = "idCompany")
    @Mapping(source = "document.idCatalago", target = "category")
    @Mapping(source = "document.nameDocument", target = "nameFile")
    @Mapping(source = "document.logo", target = "logo")
    @Mapping(source = "document.document", target = "file", qualifiedByName = "convertToString")
    @Mapping(source = "document.command", target = "command")
    br.com.developcorporation.documents.message.avro.Documents toAvro(Collaborator domain);


    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "dataProcessamento", target = "dataPostagem")
    @Mapping(source = "siglaOrigm", target = "siglaSistemaOrigem")
    br.com.developcorporation.documents.message.avro.MessageControl toMessageControl(final String uuid, final String dataProcessamento, final String siglaOrigm);

    br.com.developcorporation.documents.message.avro.DocumentsMessage toDocumentsMessage(br.com.developcorporation.documents.message.avro.Documents documents, br.com.developcorporation.documents.message.avro.MessageControl messageControl);

    @Named("convertToString")
    default String convertToString(byte[] document){
        if(Objects.isNull(document))
            return null;

      return Base64.getEncoder().encodeToString(document);
    }

}
