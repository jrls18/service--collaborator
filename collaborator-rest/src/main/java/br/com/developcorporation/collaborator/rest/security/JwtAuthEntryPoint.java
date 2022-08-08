package br.com.developcorporation.collaborator.rest.security;

import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException e)
            throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, MessageConstant.NAO_AUTORIZADO);
    }
}