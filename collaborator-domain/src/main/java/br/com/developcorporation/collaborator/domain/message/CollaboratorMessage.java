package br.com.developcorporation.collaborator.domain.message;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorMessage implements Serializable {

    private static final long serialVersionUID = -7410771900695378682L;

    private CollaboratorMessage.MessageControl messageControl;

    private CollaboratorMessage.Collaborator collaborator;

    private CollaboratorMessage.Message message;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MessageControl implements Serializable  {

        private static final long serialVersionUID = 7274670363909116546L;

        private String uuid;

        private String dataPostagem;

        private String dataInicioProcessamento;

        private String dataFimProcessamento;

        private String siglaSistemaOrigem;

        private String situacaoDoProcessamento;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Collaborator implements Serializable  {

        private static final long serialVersionUID = 757312769699414049L;

        private Long  id;

        private Long typeUser;

        private String operationType;

        private String idCompany;

        private String birthDate;

        private String name;

        private String cpfCnpj;

        private String email;

        private String password;

        private String mainPhone;

        private String telephone;

        private String zipCode;

        private String publicPlace;

        private String neighborhood;

        private String location;

        private String state;

        private String number;

        private String complement;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message implements Serializable {

        private static final long serialVersionUID = 8347916797445802502L;

        private String code;
        private String date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        private String message;
        private List<CollaboratorMessage.Message.Details> detailsList;

        public Message(String code, final String message){
            this.code = code;
            this.message = message;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Details implements Serializable {

            private static final long serialVersionUID = 5331487158476702759L;
            private String field;
            private String message;
            private String value;
        }
    }
}
