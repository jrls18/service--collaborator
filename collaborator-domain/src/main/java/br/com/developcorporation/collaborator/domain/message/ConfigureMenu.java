package br.com.developcorporation.collaborator.domain.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigureMenu implements Serializable {
    private static final long serialVersionUID = 7803740248866903339L;

    private ConfigureMenu.User user;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User implements Serializable {
        private static final long serialVersionUID = 757312769699414049L;
        private Long  id;
        private boolean collaborator;
    }
}
