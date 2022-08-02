package br.com.developcorporation.collaborator.rest.security.service;

import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.exception.error.UnauthorizedException;
import br.com.developcorporation.collaborator.rest.security.JwtProvider;
import br.com.developcorporation.collaborator.rest.security.model.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
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
            throw new UnauthorizedException(MessageConstant.USUARIO_OU_SENHA_INVALIDO);

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userName,
                            password
                    )
            );
        } catch (AuthenticationException ex) {
            throw new UnauthorizedException(MessageConstant.USUARIO_OU_SENHA_INVALIDO);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateJwtToken(authentication);
    }
}
