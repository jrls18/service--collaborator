package br.com.group.developer.corporation.service.collaborator.api.rest.mapper;

import br.com.group.developer.corporation.service.collaborator.api.rest.message.request.CollaboratorRequest;
import br.com.group.developer.corporation.service.collaborator.api.rest.message.response.CollaboratorResponse;
import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import br.com.grupo.developer.corporation.libcommons.constants.OtherConstants;
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

    @Mapping(target = "operationType", ignore = true)
    @Mapping(target = "idActive", ignore = true)
    @Mapping(target = "dateRegister", ignore = true)
    @Mapping(source = "birthDate", target = "birthDate", dateFormat = OtherConstants.YYYY_MM_DD)
    @Mapping(source = "image", target = "document.document", qualifiedByName = "convertToByte")
    Collaborator toDomain(final CollaboratorRequest collaborator);

    @Mapping(source = "birthDate", target = "birthDate", dateFormat = OtherConstants.YYYY_MM_DD)
    @Mapping(source = "dateRegister", target = "dateRegister", dateFormat = OtherConstants.DATA_FORMAT)
    @Mapping(source = "document.document", target = "image", qualifiedByName = "convertToString")
    CollaboratorResponse toResponse(final Collaborator domain);

    PaginationResponse<CollaboratorResponse> toResponse(final Pagination<Collaborator> collaboratorPagination);


    @Named("convertToByte")
    default byte[] convertToByte(final String value){
        if(StringUtils.isEmpty(value))
            return new byte[0];

        return Base64.getDecoder().decode(value.replace(FieldDomainConstants.PNG_BASE_64,""));
    }

    @Named("convertToString")
    default String convertToString(final byte[]  value){
        if(Objects.isNull(value) || value.length == 0)
            return null;

        return Base64.getEncoder().encodeToString(value);
    }

}
