package br.com.group.developer.corporation.service.collaborator.internal.call.service;


import br.com.group.developer.corporation.service.collaborator.internal.call.model.DocumentCallResponse;

public interface DocumentService {
    DocumentCallResponse getByCompanyDocumentImage(final String idCompany, final String nomeImage);
}
