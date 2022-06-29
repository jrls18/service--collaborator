package br.com.developcorporation.collaborator.kafka.mapper;

import br.com.developcorporation.collaborator.kafka.constans.DataConstant;
import br.com.developcorporation.collaborator.domain.model.Company;
import br.com.developcorporation.company.message.avro.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SendMessageMapper {
    SendMessageMapper INSTANCE = Mappers.getMapper(SendMessageMapper.class);

    @Mapping(source = "id", target = "idCompany")
    @Mapping(source = "foundationDate", target = "foundationDate", dateFormat = DataConstant.DATA_FORMAT)
    User toAvroUser(Company company);
}
