package br.com.group.developer.corporation.service.collaborator.domain.port;

public interface DocumentPort {
    byte[] getImage(final String idCompany, final String nomeImage);
}
