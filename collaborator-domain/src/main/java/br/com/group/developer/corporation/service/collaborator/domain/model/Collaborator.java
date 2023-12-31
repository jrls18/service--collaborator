package br.com.group.developer.corporation.service.collaborator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Collaborator implements Serializable {

    private static final long serialVersionUID = 7912848167188452899L;

    private Long id;

    private String name;

    private String cpfCnpj;

    private LocalDate birthDate;

    private LocalDateTime dateRegister;

    private Contact contact;

    private Address address;

    private String password;

    private TypeCollaborator typeCollaborator;

    private Status status;

    private Document document;

    private String idCompany;

    private String operationType;

    private String idActive;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Contact implements Serializable  {

        private static final long serialVersionUID = -7231291149789420778L;

        private String  mainPhone;

        private String telephone;

        private String email;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address implements Serializable {

        private static final long serialVersionUID = -5922440009383664553L;

        private String zipCode;

        private String publicPlace;

        private String complement;

        private String neighborhood;

        private String location;

        private String state;

        private Long number;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Status implements Serializable {

        private static final long serialVersionUID = 6648959694803135072L;

        private Long id;

        private String description;

    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TypeCollaborator implements Serializable {

        private static final long serialVersionUID = 6148560197178848612L;

        private Long id;

        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Document implements Serializable {

        private static final long serialVersionUID = 6148560197178848612L;

        private String nameDocument;

        private Long idCatalago;

        private boolean logo;

        private byte[] document;

        private String command;

    }
}
