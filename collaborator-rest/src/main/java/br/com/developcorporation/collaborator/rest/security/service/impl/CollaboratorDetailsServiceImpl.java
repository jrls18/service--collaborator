package br.com.developcorporation.collaborator.rest.security.service.impl;

import br.com.developcorporation.collaborator.core.service.CollaboratorService;
import br.com.developcorporation.collaborator.domain.model.Collaborator;
import br.com.developcorporation.collaborator.rest.security.model.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CollaboratorDetailsServiceImpl implements UserDetailsService {

    private final CollaboratorService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Collaborator collaborator = service.findByUsername(username)
                    .orElseThrow(() ->
                            new UsernameNotFoundException("Usuario ou senha não encontrado." + username)
                    );

            return UserPrinciple.build(collaborator);
    }
}
