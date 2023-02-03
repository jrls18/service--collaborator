package br.com.developcorporation.collaborator.domain.message;

import br.com.grupo.developer.corporation.libcommons.message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigureMenuUser implements Serializable {

    private static final long serialVersionUID = -7410771900695378682L;

    private MessageControl messageControl;

    private User user;

    private Message message;

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

        public MessageControl(final String uuid, final String dataPostagem, final String siglaSistemaOrigem, final String situacaoDoProcessamento){
            this.uuid = uuid;
            this.dataPostagem = dataPostagem;
            this.siglaSistemaOrigem = siglaSistemaOrigem;
            this.situacaoDoProcessamento = situacaoDoProcessamento;
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User implements Serializable  {

        private static final long serialVersionUID = 757312769699414049L;

        private Long  id;

        private boolean collaborator;
    }
}
