package br.com.developcorporation.collaborator.services.internal.adapter;

import br.com.developcorporation.collaborator.domain.model.Company;
import br.com.developcorporation.collaborator.domain.port.CompanyPort;
import br.com.developcorporation.collaborator.services.internal.mapper.CompanyMapper;
import br.com.developcorporation.collaborator.services.internal.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyAdapter implements CompanyPort {

    private final CompanyService service;

    @Override
    public Company consultaPorId(Long id) {
        return CompanyMapper.INSTANCE.toDomain(service.consultaPorCodigoEmpresa(id));
    }
}
