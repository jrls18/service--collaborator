package br.com.developcorporation.collaborator.rest.mapper;

import br.com.developcorporation.collaborator.domain.constants.FieldConstants;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.message.request.CollaboratorRequest;
import br.com.developcorporation.collaborator.rest.message.response.CollaboratorResponse;
import br.com.grupo.developer.corporation.libcommons.message.Pagination;
import br.com.grupo.developer.corporation.libcommons.message.response.PaginationResponse;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Base64;
import java.util.Objects;

@Mapper(uses =  BaseMapper.class)
public interface CollaboratorMapper {

    CollaboratorMapper INSTANCE = Mappers.getMapper(CollaboratorMapper.class);

    @Mapping(source = "birthDate", target = "birthDate", dateFormat = MessageConstant.DATA_FORMAT)
    @Mapping(source = "image", target = "document.document", qualifiedByName = "convertToByte")
    Collaborator toDomain(final CollaboratorRequest collaborator);

    @Mapping(source = "birthDate", target = "birthDate", dateFormat = MessageConstant.DATA_FORMAT)
    @Mapping(source = "dateRegister", target = "dateRegister", dateFormat = MessageConstant.DATA_HORA_FORMAT)
    @Mapping(source = "document.document", target = "image", qualifiedByName = "convertToString")
    CollaboratorResponse toResponse(final Collaborator domain);

    PaginationResponse<CollaboratorResponse> toResponse(final Pagination<Collaborator> collaboratorPagination);


    @Named("convertToByte")
    default byte[] convertToByte(final String value){
        if(StringUtils.isEmpty(value))
            return null;

        return Base64.getDecoder().decode(value.replace(FieldConstants.PNG_BASE_64,""));
    }

    @Named("convertToString")
    default String convertToString(final byte[]  value){
        if(Objects.isNull(value))
            return null;

        return Base64.getEncoder().encodeToString(value);
    }

}
