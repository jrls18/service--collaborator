package br.com.developcorporation.collaborator.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Locale;

@Getter
@Setter
public class Collaborator implements Serializable {

    private static final long serialVersionUID = 7912848167188452899L;

    private Long id;

    private String name;

    private String cpfCnpj;

    private String birthDate;

    private LocalDateTime dataRegister;

    private Contact contact;

    private Address address;

    private String password;

    private String typeUser;

    private Status status;

    private String image;

    private String idCompany;

    @Getter
    @Setter
    public static class Contact implements Serializable  {

        private static final long serialVersionUID = -7231291149789420778L;

        private String  mainPhone;

        private String telephone;

        private String email;
    }

    @Getter
    @Setter
    public static class Address implements Serializable {

        private static final long serialVersionUID = -5922440009383664553L;

        private String zipCode;

        private String address;

        private String complement;

        private String neighborhood;

        private String location;

        private String state;

        private String number;
    }

    @Getter
    @Setter
    public static class Status implements Serializable {

        private static final long serialVersionUID = 6648959694803135072L;

        private String id;
    }
}
