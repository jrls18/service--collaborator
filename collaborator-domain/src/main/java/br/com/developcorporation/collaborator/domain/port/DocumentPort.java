package br.com.developcorporation.collaborator.domain.port;

public interface DocumentPort {
    byte[] getImage(final String idCompany, final String nomeImage);
}
