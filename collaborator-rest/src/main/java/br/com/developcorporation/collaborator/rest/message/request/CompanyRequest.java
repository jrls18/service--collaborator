package br.com.developcorporation.collaborator.rest.message.request;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(FieldConstant.EMPRESA)
public class CompanyRequest implements Serializable {

    private static final long serialVersionUID = 2360032369824553493L;

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
    private Contact contact;

    @JsonProperty(value = FieldConstant.ENDERECO, index = 7)
    private Address address;

    @JsonProperty(value = FieldConstant.PASSWORD, index = 8)
    private String password;

    @JsonProperty(value = FieldConstant.TIPO_SISTEMA, index = 9)
    private SystemType systemType;

    @JsonProperty(value = FieldConstant.STATUS, index = 10)
    private Status status;

    @JsonProperty(value = FieldConstant.FOLLOW_UP, index = 11)
    private FollowUp followUp;

    @JsonProperty(value = FieldConstant.GROUP, index = 12)
    private Group group;

    @JsonProperty(value = FieldConstant.ACEITA_TERMOS, index = 13)
    private boolean acceptTerms;

    @JsonProperty(value = FieldConstant.IMAGE, index = 14)
    private String image;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Contact implements Serializable  {

        private static final long serialVersionUID = 7907282585287515246L;

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

        private static final long serialVersionUID = -8926165463113530296L;

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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Group implements Serializable {

        private static final long serialVersionUID = 7667389132147389630L;

        @JsonProperty(value = FieldConstant.CODIGO, index = 0)
        private String id;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FollowUp implements Serializable {

        private static final long serialVersionUID = 3389123076787025920L;

        @JsonProperty(value = FieldConstant.CODIGO, index = 0)
        private String id;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status implements Serializable {

        private static final long serialVersionUID = -3747349486281478790L;

        @JsonProperty(value = FieldConstant.CODIGO, index = 0)
        private String id;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SystemType implements Serializable {
        private static final long serialVersionUID = 1514675120175479160L;

        @JsonProperty(value = FieldConstant.CODIGO, index = 0)
        private String id;
    }
}
