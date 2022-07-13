package br.com.developcorporation.collaborator.jpa.adapter;

import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.domain.port.CompanyPort;
import br.com.developcorporation.collaborator.jpa.repository.CollaboratorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CollaboratorAdapter implements CompanyPort {

   private final CollaboratorRepository repository;

    @Override
    public Long add(Collaborator dto) {
      //return repository.save(CompanyMapper.INSTANCE.toEntity(dto)).getId();
        return null;
    }

    @Override
    public void update(Collaborator dto) {
        //repository.save(CompanyMapper.INSTANCE.toEntity(dto));
    }

    @Override
    public Collaborator getById(Long id) {

       Optional<br.com.developcorporation.collaborator.jpa.entity.Collaborator> optionalCompany =  repository.findById(id);

       if (optionalCompany.isEmpty())
           return null;

       // return CompanyMapper.INSTANCE.toDto(optionalCompany.get());
        return null;
    }

    @Override
    public Collaborator getByCnpj(String cnpj) {

        Optional<br.com.developcorporation.collaborator.jpa.entity.Collaborator> optionalCompany =  repository.findByCnpj(cnpj);

        if (optionalCompany.isEmpty())
            return null;

        //return CompanyMapper.INSTANCE.toDto(optionalCompany.get());
        return null;
    }

    @Override
    public Collaborator getByCorporateName(String corporateName) {

        Optional<br.com.developcorporation.collaborator.jpa.entity.Collaborator> optionalCompany =  repository.findByCorporateName(corporateName);

        if (optionalCompany.isEmpty())
            return null;

        //return CompanyMapper.INSTANCE.toDto(optionalCompany.get());
       return null;
    }
}
