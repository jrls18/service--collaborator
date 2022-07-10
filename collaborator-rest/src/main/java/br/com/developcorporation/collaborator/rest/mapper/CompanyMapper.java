package br.com.developcorporation.collaborator.rest.mapper;

import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.message.response.CompanyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = BaseMapper.class)
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    /*
    @Mapping(source = "address.zipCode", target = "zipCode")
    @Mapping(source = "address.publicPlace", target = "publicPlace")
    @Mapping(source = "address.complement", target = "complement")
    @Mapping(source = "address.neighborhood", target = "neighborhood")
    @Mapping(source = "address.location", target = "location")
    @Mapping(source = "address.state", target = "state")
    @Mapping(source = "address.number", target = "number")

    @Mapping(source = "contact.mainPhone", target = "mainPhone")
    @Mapping(source = "contact.telephone", target = "telephone")
    @Mapping(source = "contact.email", target = "email")
    Company toDto(final CompanyRequest request);



    @Mapping(source = "zipCode", target = "address.zipCode")
    @Mapping(source = "publicPlace", target = "address.publicPlace")
    @Mapping(source = "complement", target = "address.complement")
    @Mapping(source = "neighborhood", target = "address.neighborhood")
    @Mapping(source = "location", target = "address.location")
    @Mapping(source = "state", target = "address.state")
    @Mapping(source = "number", target = "address.number")

    @Mapping(source = "mainPhone", target = "contact.mainPhone")
    @Mapping(source = "telephone", target = "contact.telephone")
    @Mapping(source = "email", target = "contact.email")
    @Mapping(source = "dateRegister", target = "dateRegister", dateFormat = MessageConstant.DATA_FORMAT)
    CompanyResponse toResponse(final Company dto);

     */
}
