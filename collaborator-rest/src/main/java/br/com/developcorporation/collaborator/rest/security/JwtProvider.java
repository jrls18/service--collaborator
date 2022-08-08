package br.com.developcorporation.collaborator.rest.security;


import br.com.developcorporation.collaborator.rest.constants.MessageConstant;
import br.com.developcorporation.collaborator.rest.security.model.Jwt;
import br.com.developcorporation.collaborator.rest.security.model.UserPrinciple;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	private String jwtSecret;
	private int jwtExpiration;

	public JwtProvider(
			  final @Value("${app.jwtSecret}") String jwtSecret
			, final @Value("${app.jwtExpiration}") int jwtExpiration) {
		this.jwtExpiration = jwtExpiration;
		this.jwtSecret = jwtSecret;
	}

	public Jwt generateJwtToken(final Authentication authentication) {

		UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

		Date date = new Date((new Date()).getTime() + jwtExpiration * 1000);

		String token =	Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(date)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

		return new Jwt(token, MessageConstant.BEARER, date);
	}

	public boolean validateJwtToken(final String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (Exception e) {
			logger.error(MessageConstant.TOKEN_TWT_INVALIDO_MENSAGEM, e);
		}
		return false;
	}

	public String getUserNameFromJwtToken(final String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
}