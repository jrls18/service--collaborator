package br.com.developcorporation.collaborator.jpa.adapter;

import br.com.developcorporation.collaborator.domain.model.Company;
import br.com.developcorporation.collaborator.domain.port.CompanyPort;
import br.com.developcorporation.collaborator.jpa.mapper.CompanyMapper;
import br.com.developcorporation.collaborator.jpa.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CompanyAdapter implements CompanyPort {

   private final CompanyRepository repository;

    @Override
    public Long add(Company dto) {
      return repository.save(CompanyMapper.INSTANCE.toEntity(dto)).getId();
    }

    @Override
    public void update(Company dto) {
        repository.save(CompanyMapper.INSTANCE.toEntity(dto));
    }

    @Override
    public Company getById(Long id) {

       Optional<br.com.developcorporation.collaborator.jpa.entity.Company> optionalCompany =  repository.findById(id);

       if (optionalCompany.isEmpty())
           return null;

        return CompanyMapper.INSTANCE.toDto(optionalCompany.get());
    }

    @Override
    public Company getByCnpj(String cnpj) {

        Optional<br.com.developcorporation.collaborator.jpa.entity.Company> optionalCompany =  repository.findByCnpj(cnpj);

        if (optionalCompany.isEmpty())
            return null;

        return CompanyMapper.INSTANCE.toDto(optionalCompany.get());
    }

    @Override
    public Company getByCorporateName(String corporateName) {

        Optional<br.com.developcorporation.collaborator.jpa.entity.Company> optionalCompany =  repository.findByCorporateName(corporateName);

        if (optionalCompany.isEmpty())
            return null;

        return CompanyMapper.INSTANCE.toDto(optionalCompany.get());
    }
}
