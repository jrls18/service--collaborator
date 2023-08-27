package br.com.group.developer.corporation.service.collaborator.internal.call.adapter;

import br.com.group.developer.corporation.service.collaborator.domain.port.DocumentPort;
import br.com.group.developer.corporation.service.collaborator.internal.call.model.DocumentCallResponse;
import br.com.group.developer.corporation.service.collaborator.internal.call.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class DocumentsAdapter implements DocumentPort {

    private final DocumentService service;

    @Override
    public byte[] getImage(String idCompany, String nomeImage) {

        if(StringUtils.isEmpty(idCompany) || StringUtils.isEmpty(nomeImage))
            return new byte[0];

        DocumentCallResponse callResponse = service.getByCompanyDocumentImage(idCompany,nomeImage);

        if(Objects.isNull(callResponse) || StringUtils.isEmpty(callResponse.getArquivo_base_64()))
           return new byte[0];

        return Base64.getDecoder().decode(callResponse.getArquivo_base_64());
    }
}
