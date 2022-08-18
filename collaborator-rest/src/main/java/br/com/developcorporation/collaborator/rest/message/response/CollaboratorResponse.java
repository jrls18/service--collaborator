package br.com.developcorporation.collaborator.rest.message.response;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import br.com.developcorporation.collaborator.rest.message.request.CollaboratorRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(FieldConstant.EMPRESA)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollaboratorResponse implements Serializable {

    private static final long serialVersionUID = 2763823017618602227L;
    @JsonProperty(value = FieldConstant.CODIGO, index = 0)
    private String id;

    @JsonProperty(value = FieldConstant.NOME, index = 1)
    private String name;

    @JsonProperty(value = FieldConstant.CPF_CNPJ, index = 2)
    private String cpfCnpj;

    @JsonProperty(value = FieldConstant.DATA_NASCIMENTO, index = 4)
    private String birthDate;

    @JsonProperty(value = FieldConstant.DATA_CADASTRO)
    private String dateRegister;

    @JsonProperty(value = FieldConstant.CONTATO, index = 5)
    private CollaboratorResponse.Contact contact;

    @JsonProperty(value = FieldConstant.ENDERECO, index = 6)
    private CollaboratorResponse.Address address;

    @JsonIgnore
    @JsonProperty(value = FieldConstant.PASSWORD, index = 7)
    private String password;

    @JsonProperty(value = FieldConstant.TIPO_USUARIO, index = 8)
    private String typeUser;

    @JsonProperty(value = FieldConstant.STATUS, index = 9)
    private CollaboratorResponse.Status status;

    @JsonProperty(value = FieldConstant.IMAGE, index = 11)
    private String image;

    @JsonProperty(value = FieldConstant.ID_COMPANY, index = 12)
    private String idCompany;

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Contact implements Serializable  {

        private static final long serialVersionUID = -7210855052175147764L;

        @JsonProperty(value = FieldConstant.TELEFONE_PRINCIPAL, index = 0)
        private String  mainPhone;

        @JsonProperty(value = FieldConstant.TELEFONE, index = 1)
        private String telephone;

        @JsonProperty(value = FieldConstant.EMAIL, index = 2)
        private String email;
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address implements Serializable {

        private static final long serialVersionUID = 6506423960337758206L;

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

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status implements Serializable {

        private static final long serialVersionUID = 249379087391903640L;

        @JsonProperty(value = FieldConstant.CODIGO, index = 0)
        private String id;
    }
}
