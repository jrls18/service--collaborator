package br.com.developcorporation.collaborator.rest.message.response;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(FieldConstant.EMPRESA)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyResponse implements Serializable {

    private static final long serialVersionUID = -1718255039511769027L;

    @JsonProperty(value = FieldConstant.CODIGO, index = 0)
    private String id;

    @JsonProperty(value = FieldConstant.RAZAO_SOCIAL, index = 1)
    private String corporateName;

    @JsonProperty(value = FieldConstant.NOME_FANTASIA, index = 2)
    private String fantasyName;

    @JsonProperty(value = FieldConstant.CNPJ, index = 3)
    private String cnpj;

    @JsonProperty(value = FieldConstant.INSCRICAO_ESTADUAL, index = 4)
    private String stateRegistration;

    @JsonProperty(value = FieldConstant.DATA_FUNDACAO, index = 5)
    private String foundationDate;

    @JsonProperty(value = FieldConstant.CONTATO, index = 6)
    private CompanyResponse.Contact contact;

    @JsonProperty(value = FieldConstant.ENDERECO, index = 7)
    private CompanyResponse.Address address;

    @JsonIgnore
    @JsonProperty(value = FieldConstant.PASSWORD, index = 8)
    private String password;

    @JsonProperty(value = FieldConstant.TIPO_SISTEMA, index = 9)
    private CompanyResponse.SystemType systemType;

    @JsonProperty(value = FieldConstant.STATUS, index = 10)
    private CompanyResponse.Status status;

    @JsonProperty(value = FieldConstant.FOLLOW_UP, index = 11)
    private CompanyResponse.FollowUp followUp;

    @JsonProperty(value = FieldConstant.GROUP, index = 12)
    private CompanyResponse.Group group;

    @JsonProperty(value = FieldConstant.DATA_CADASTRO, index = 13)
    private String dateRegister;

    @JsonProperty(value = FieldConstant.ACEITA_TERMOS, index = 14)
    private boolean acceptTerms;

    @JsonProperty(value = FieldConstant.NOME_IMAGEM, index = 15)
    private String nameImage;

    @JsonProperty(value = FieldConstant.IMAGE, index = 16)
    private byte[] img;


    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Contact implements Serializable  {

        private static final long serialVersionUID = -8389457904018424019L;

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

        private static final long serialVersionUID = -2762310855910191608L;

        @JsonProperty(value = FieldConstant.CEP, index = 0)
        private String zipCode;

        @JsonProperty(value = FieldConstant.LOGRADOURO, index = 1)
        private String publicPlace;

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
    public static class Group implements Serializable {

        private static final long serialVersionUID = 2198166645927555517L;

        @JsonProperty(value = FieldConstant.CODIGO, index = 0)
        private String id;

        @JsonProperty(value = FieldConstant.DESCRICAO, index = 1)
        private String description;
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FollowUp implements Serializable {

        private static final long serialVersionUID = 7159090304540637279L;

        @JsonProperty(value = FieldConstant.CODIGO, index = 0)
        private String id;

        @JsonProperty(value = FieldConstant.DESCRICAO, index = 1)
        private String description;
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status implements Serializable {

        private static final long serialVersionUID = 5394157680535502049L;

        @JsonProperty(value = FieldConstant.CODIGO, index = 0)
        private String id;

        @JsonProperty(value = FieldConstant.DESCRICAO, index = 1)
        private String description;
    }

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SystemType implements Serializable {

        private static final long serialVersionUID = 5635931797508290934L;

        @JsonProperty(value = FieldConstant.CODIGO, index = 0)
        private String id;

        @JsonProperty(value = FieldConstant.DESCRICAO, index = 1)
        private String description;
    }
}
