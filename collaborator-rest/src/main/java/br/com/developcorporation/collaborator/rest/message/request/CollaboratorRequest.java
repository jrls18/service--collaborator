package br.com.developcorporation.collaborator.rest.message.request;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Getter
@JsonRootName(FieldConstant.EMPRESA)
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorRequest implements Serializable {

    private static final long serialVersionUID = -9182653756222658250L;

    @JsonProperty(value = FieldConstant.CODIGO, index = 0)
    private String id;

    @JsonProperty(value = FieldConstant.NOME, index = 1)
    private String name;

    @JsonProperty(value = FieldConstant.CPF_CNPJ, index = 2)
    private String cpfCnpj;

    @JsonProperty(value = FieldConstant.DATA_NASCIMENTO, index = 4)
    private String birthDate;

    @JsonProperty(value = FieldConstant.CONTATO, index = 5)
    private CollaboratorRequest.Contact contact;

    @JsonProperty(value = FieldConstant.ENDERECO, index = 6)
    private CollaboratorRequest.Address address;

    @JsonProperty(value = FieldConstant.PASSWORD, index = 7)
    private String password;

    @JsonProperty(value = FieldConstant.TIPO_USUARIO, index = 8)
    private CollaboratorRequest.TypeCollaborator typeCollaborator;

    @JsonProperty(value = FieldConstant.STATUS, index = 9)
    private CollaboratorRequest.Status status;

    @JsonProperty(value = FieldConstant.IMAGE, index = 11)
    private String image;

    @JsonProperty(value = FieldConstant.ID_COMPANY, index = 12)
    private String idCompany;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Contact implements Serializable  {

        private static final long serialVersionUID = -6641315552266440737L;

        @JsonProperty(value = FieldConstant.TELEFONE_PRINCIPAL, index = 0)
        private String  mainPhone;

        @JsonProperty(value = FieldConstant.TELEFONE, index = 1)
        private String telephone;

        @JsonProperty(value = FieldConstant.EMAIL, index = 2)
        private String email;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address implements Serializable {

        private static final long serialVersionUID = 7234925530231387268L;

        @JsonProperty(value = FieldConstant.CEP, index = 0)
        private String zipCode;

        @JsonProperty(value = FieldConstant.LOGRADOURO, index = 1)
        private String address;

        @JsonProperty(value = FieldConstant.COMPLEMENTO, index = 2)
        private String complement;

        @JsonProperty(value = FieldConstant.BAIRRO, index = 3)
        private String neighborhood;

        @JsonProperty(value = FieldConstant.LOCALIDADE, index = 4)
        private String location;

        @JsonProperty(value = FieldConstant.UF, index = 5)
        private String state;

        @JsonProperty(value = FieldConstant.NUMERO, index = 6)
        private String number;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status implements Serializable {

        private static final long serialVersionUID = -2406858146490241701L;

        @JsonProperty(value = FieldConstant.CODIGO, index = 0)
        private String id;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TypeCollaborator implements Serializable {

        private static final long serialVersionUID = -24215801746158605L;

        @JsonProperty(value = FieldConstant.CODIGO, index = 0)
        private String id;
    }
}
