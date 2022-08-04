package br.com.developcorporation.collaborator.jpa.adapter;

import antlr.StringUtils;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.domain.port.CollaboratorPort;
import br.com.developcorporation.collaborator.jpa.mapper.CollaboratorMapper;
import br.com.developcorporation.collaborator.jpa.service.CollaboratorRepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CollaboratorAdapter implements CollaboratorPort {

   private final CollaboratorRepositoryService service;

    @Override
    public Long add(Collaborator dto) {
        return service.save(CollaboratorMapper.INSTANCE.toEntity(dto));
    }

    @Override
    public void update(Collaborator dto) {
        service.save(CollaboratorMapper.INSTANCE.toEntity(dto));
    }

    @Override
    public Boolean existeEmpresa(String id) {
        return service.existeEmpresa(Long.parseLong(id));
    }

    @Override
    public Collaborator getById(Long id) {
        return CollaboratorMapper.INSTANCE.toDomain(service.consultaPorCodigo(id).orElse(null));
    }

    @Override
    public Collaborator getEmail(String email) {
        return CollaboratorMapper.INSTANCE.toDomain(service.findByEmail(email).orElse(null));
    }

    @Override
    public Optional<Collaborator> findByUserName(final String username){

        Optional<br.com.developcorporation.collaborator.jpa.entity.Collaborator> collaboratorOptional  = service.findByUserName(username);

        if(collaboratorOptional.isEmpty())
            return Optional.empty();

       return Optional.of(CollaboratorMapper.INSTANCE.toDomain(collaboratorOptional.get()));
    }


     /*
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

     */
}
