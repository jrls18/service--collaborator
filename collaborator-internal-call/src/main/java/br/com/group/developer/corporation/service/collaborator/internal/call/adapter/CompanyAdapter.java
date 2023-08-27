package br.com.group.developer.corporation.service.collaborator.internal.call.adapter;

import br.com.group.developer.corporation.service.collaborator.domain.model.Company;
import br.com.group.developer.corporation.service.collaborator.domain.port.CompanyPort;
import br.com.group.developer.corporation.service.collaborator.internal.call.mapper.CompanyMapper;
import br.com.group.developer.corporation.service.collaborator.internal.call.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CompanyAdapter implements CompanyPort {

    private final CompanyService service;

    @Override
    public Company findByCompanyId(Long id) {

        if(Objects.isNull(id) || id <= 0)
            return null;

        return CompanyMapper.INSTANCE.toDomain(service.getByCompanyId(id));
    }
}
