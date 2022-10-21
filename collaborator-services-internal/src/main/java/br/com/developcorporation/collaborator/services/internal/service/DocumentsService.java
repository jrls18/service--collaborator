package br.com.developcorporation.collaborator.services.internal.service;

import br.com.developcorporation.collaborator.services.internal.model.DocumentsModel;

public interface DocumentsService {
    DocumentsModel consultaImagem(final String idCompany, final String nomeImage);
}
