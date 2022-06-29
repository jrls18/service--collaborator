package br.com.developcorporation.collaborator.domain.model;

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
public class Company implements Serializable {

    private static final long serialVersionUID = -3408651145711532102L;

    private Long id;

    private String corporateName;

    private String fantasyName;

    private String cnpj;

    private String stateRegistration;

    private LocalDate foundationDate;

    private String  mainPhone;

    private String telephone;

    private String email;

    private String password;

    private String zipCode;

    private String publicPlace;

    private String complement;

    private String neighborhood;

    private String location;

    private String state;

    private int number;

    private LocalDateTime dateRegister;

    private Status status;

    private FollowUp followUp;

    private Group group;

    private SystemType systemType;

    private boolean acceptTerms;

}
