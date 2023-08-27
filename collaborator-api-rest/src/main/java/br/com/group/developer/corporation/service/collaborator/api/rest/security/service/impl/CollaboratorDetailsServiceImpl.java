package br.com.group.developer.corporation.service.collaborator.api.rest.security.service.impl;

import br.com.group.developer.corporation.service.collaborator.api.rest.security.model.UserPrinciple;
import br.com.group.developer.corporation.service.collaborator.core.service.CollaboratorService;
import br.com.group.developer.corporation.service.collaborator.domain.constants.MessageDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
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
                            new UsernameNotFoundException(MessageDomainConstants.USUARIO_OU_SENHA_NAO_ENCONTRADO + username)
                    );

            return UserPrinciple.build(collaborator);
    }
}
