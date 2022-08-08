package br.com.developcorporation.lib.commons.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = -7098178618293139990L;

    private String code;
    private String date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    private String message;
    private List<Details> detailsList;

    public Message(final String message){
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
