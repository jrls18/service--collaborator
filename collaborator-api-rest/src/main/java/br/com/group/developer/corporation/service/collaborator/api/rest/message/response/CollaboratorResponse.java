package br.com.group.developer.corporation.service.collaborator.api.rest.message.response;

import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(FieldDomainConstants.COLLABORATOR)
public class CollaboratorResponse implements Serializable {

    private static final long serialVersionUID = 2763823017618602227L;
    @JsonProperty(value = FieldDomainConstants.CODIGO, index = 0)
    private String id;

    @JsonProperty(value = FieldDomainConstants.NOME, index = 1)
    private String name;

    @JsonProperty(value = FieldDomainConstants.CPF_CNPJ, index = 2)
    private String cpfCnpj;

    @JsonProperty(value = FieldDomainConstants.DATA_NASCIMENTO, index = 4)
    private String birthDate;

    @JsonProperty(value = FieldDomainConstants.DATA_HORA_CADASTRO)
    private String dateRegister;

    @JsonProperty(value = FieldDomainConstants.CONTATO, index = 5)
    private Contact contact;

    @JsonProperty(value = FieldDomainConstants.ENDERECO, index = 6)
    private Address address;

    @JsonIgnore
    @JsonProperty(value = FieldDomainConstants.PASSWORD, index = 7)
    private String password;

    @JsonProperty(value = FieldDomainConstants.TIPO_USUARIO, index = 8)
    private TypeCollaborator typeCollaborator;

    @JsonProperty(value = FieldDomainConstants.SITUACAO, index = 9)
    private Status status;

    @JsonProperty(value = FieldDomainConstants.ID_COMPANY, index = 11)
    private String idCompany;

    @JsonProperty(value = FieldDomainConstants.IMAGE, index = 12)
    private String image;

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Contact implements Serializable  {

        private static final long serialVersionUID = -7210855052175147764L;

        @JsonProperty(value = FieldDomainConstants.TELEFONE_PRINCIPAL, index = 0)
        private String  mainPhone;

        @JsonProperty(value = FieldDomainConstants.TELEFONE, index = 1)
        private String telephone;

        @JsonProperty(value = FieldDomainConstants.EMAIL, index = 2)
        private String email;
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address implements Serializable {

        private static final long serialVersionUID = 6506423960337758206L;

        @JsonProperty(value = FieldDomainConstants.CEP, index = 0)
        private String zipCode;

        @JsonProperty(value = FieldDomainConstants.LOGRADOURO, index = 1)
        private String publicPlace;

        @JsonProperty(value = FieldDomainConstants.COMPLEMENTO, index = 2)
        private String complement;

        @JsonProperty(value = FieldDomainConstants.BAIRRO, index = 3)
        private String neighborhood;

        @JsonProperty(value = FieldDomainConstants.LOCALIDADE, index = 4)
        private String location;

        @JsonProperty(value = FieldDomainConstants.UF, index = 5)
        private String state;

        @JsonProperty(value = FieldDomainConstants.NUMERO, index = 6)
        private String number;
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status implements Serializable {

        private static final long serialVersionUID = 249379087391903640L;

        @JsonProperty(value = FieldDomainConstants.CODIGO, index = 0)
        private String id;

        @JsonProperty(value = FieldDomainConstants.DESCRICAO, index = 1)
        private String description;
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TypeCollaborator implements Serializable {

        private static final long serialVersionUID = -24215801746158605L;

        @JsonProperty(value = FieldDomainConstants.CODIGO, index = 0)
        private String id;

        @JsonProperty(value = FieldDomainConstants.DESCRICAO, index = 1)
        private String name;
    }
}
