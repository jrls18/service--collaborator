package br.com.group.developer.corporation.service.collaborator.api.rest.security.service;

import br.com.group.developer.corporation.service.collaborator.api.rest.security.JwtProvider;
import br.com.group.developer.corporation.service.collaborator.api.rest.security.model.Jwt;
import br.com.group.developer.corporation.service.collaborator.domain.constants.MessageDomainConstants;
import br.com.grupo.developer.corporation.libcommons.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AuthenticateService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public Jwt authenticateUser(final String userName, final String password) {

        Authentication authentication;

        if(Objects.isNull(userName) || Objects.isNull(password) || userName.isEmpty() || password.isEmpty())
            throw new UnauthorizedException(MessageDomainConstants.USUARIO_OU_SENHA_INVALIDO);

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userName,
                            password
                    )
            );

        } catch (AuthenticationException ex) {
            if(ex instanceof BadCredentialsException)
                throw new UnauthorizedException(MessageDomainConstants.USUARIO_OU_SENHA_INVALIDO);

            throw new UnauthorizedException(ex.getMessage());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateJwtToken(authentication);
    }
}
