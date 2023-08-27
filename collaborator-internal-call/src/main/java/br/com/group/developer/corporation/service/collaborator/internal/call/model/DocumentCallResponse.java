package br.com.group.developer.corporation.service.collaborator.internal.call.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentCallResponse implements Serializable {
    private static final long serialVersionUID = -5467196270454114858L;

    private String arquivo_base_64;
}
