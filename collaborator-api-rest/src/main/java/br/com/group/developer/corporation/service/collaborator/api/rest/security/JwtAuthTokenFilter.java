package br.com.group.developer.corporation.service.collaborator.api.rest.security;

import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.constants.MessageDomainConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        try {

            String jwt = getJwt(request);
            if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
                String username = tokenProvider.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error(MessageDomainConstants.NAO_E_POSSIVEL_DEFINIR_A_AUTENTICACAO_DO_USUARIO_MENSAGEM, e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwt(final HttpServletRequest request) {
        String authHeader = request.getHeader(FieldDomainConstants.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(FieldDomainConstants.BEARER)) {
            return authHeader.replace(FieldDomainConstants.BEARER, "");
        }

        return null;
    }
}
