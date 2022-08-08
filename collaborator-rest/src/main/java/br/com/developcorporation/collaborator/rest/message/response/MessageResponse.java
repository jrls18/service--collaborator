package br.com.developcorporation.collaborator.rest.message.response;

import br.com.developcorporation.collaborator.rest.constants.FieldConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MessageResponse implements Serializable {

    private static final long serialVersionUID = 8546092362745129740L;

    @JsonProperty(value = FieldConstant.CODIGO, index = 0)
    private String code;

    @Getter
    @JsonProperty(value = FieldConstant.DATA_CADASTRO, index = 1)
    private String date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

    @JsonProperty(value = FieldConstant.MENSAGEM, index = 2)
    private String message;

    @JsonProperty(value = FieldConstant.DETALHES, index = 3)
    private List<DetailsResponse> detailsList;

    public MessageResponse(final String message){
        this.message = message;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class DetailsResponse implements Serializable {

        private static final long serialVersionUID = -5480714928022137321L;

        @JsonProperty(value = FieldConstant.CAMPO, index = 0)
        private String field;

        @JsonProperty(value = FieldConstant.MENSAGEM, index = 1)
        private String message;

        @JsonProperty(value = FieldConstant.VALOR, index = 2)
        private String value;
    }

}
