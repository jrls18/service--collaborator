package br.com.developcorporation.collaborator.services.internal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentsModel implements Serializable {
    private static final long serialVersionUID = -5467196270454114858L;

    private String arquivo_base_64;
}
