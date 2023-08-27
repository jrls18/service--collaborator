package br.com.group.developer.corporation.service.collaborator.message.avro.produce.mapper;

import br.com.group.developer.corporation.document.avro.DocumentMessage;
import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import br.com.grupo.developer.corporation.libcommons.message.MessageAsync;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Base64;
import java.util.Objects;

@Mapper
public interface DocumentSendMessageMapper {

    DocumentSendMessageMapper INSTANCE = Mappers.getMapper(DocumentSendMessageMapper.class);

    @Mapping(source = "obj.id", target = "document.idUser")
    @Mapping(source = "obj.document.idCatalago", target = "document.category")
    @Mapping(source = "obj.document.nameDocument", target = "document.nameFile")
    @Mapping(source = "obj.document.logo", target = "document.logo")
    @Mapping(source = "obj.document.document", target = "document.file", qualifiedByName = "convertToString")
    @Mapping(source = "obj.document.command", target = "document.command")
    DocumentMessage toAvro(final MessageAsync<Collaborator> domain);

    @Named("convertToString")
    default String convertToString(byte[] document){
        if(Objects.isNull(document))
            return null;

      return Base64.getEncoder().encodeToString(document);
    }
}
