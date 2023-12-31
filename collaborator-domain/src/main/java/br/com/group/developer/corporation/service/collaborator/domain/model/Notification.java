package br.com.group.developer.corporation.service.collaborator.domain.model;

import br.com.grupo.developer.corporation.libcommons.message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements Serializable {

    private static final long serialVersionUID = 8334442973691673238L;

    private String email;

    private String name;

    private String cellPhone;

    private String idActive;

    private TypeNotification typeNotification;

    private String idLayout;

    private String password;

    private Message message;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TypeNotification implements Serializable {

        private static final long serialVersionUID = -431068704133638154L;

        private Long id;
    }
}
