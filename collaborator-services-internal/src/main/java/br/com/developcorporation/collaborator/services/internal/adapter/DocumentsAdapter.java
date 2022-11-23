package br.com.developcorporation.collaborator.services.internal.adapter;

import br.com.developcorporation.collaborator.domain.port.DocumentPort;
import br.com.developcorporation.collaborator.services.internal.model.DocumentsModel;
import br.com.developcorporation.collaborator.services.internal.service.DocumentsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class DocumentsAdapter implements DocumentPort {

    private final DocumentsService service;

    @Override
    public byte[] getImage(String idCompany, String nomeImage) {
        if(StringUtils.isEmpty(idCompany) || StringUtils.isEmpty(nomeImage))
            return null;

       DocumentsModel model = service.consultaImagem(idCompany, nomeImage);

       if(Objects.isNull(model))
           return null;

       if(StringUtils.isEmpty(model.getArquivo_base_64()))
           return null;

        return Base64.getDecoder().decode(model.getArquivo_base_64());
    }
}
