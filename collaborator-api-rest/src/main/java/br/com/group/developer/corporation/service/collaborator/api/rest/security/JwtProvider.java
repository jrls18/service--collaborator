package br.com.group.developer.corporation.service.collaborator.api.rest.security;


import br.com.group.developer.corporation.service.collaborator.api.rest.security.model.Jwt;
import br.com.group.developer.corporation.service.collaborator.api.rest.security.model.UserPrinciple;
import br.com.group.developer.corporation.service.collaborator.domain.constants.FieldDomainConstants;
import br.com.group.developer.corporation.service.collaborator.domain.constants.MessageDomainConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	private final String jwtSecret;
	private final int jwtExpiration;

	public JwtProvider(
			  final @Value("${spring.security.jwt.secret-key}") String jwtSecret
			, final @Value("${spring.security.jwt.expiration}") int jwtExpiration) {
		this.jwtExpiration = jwtExpiration;
		this.jwtSecret = jwtSecret;
	}

	public Jwt generateJwtToken(final Authentication authentication) {

		UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

		Date date = new Date((new Date()).getTime() + jwtExpiration * 1000L);

		String token =	Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(date)
				.signWith(getSigningKey(),SignatureAlgorithm.HS256).compact();

		return new Jwt(token, FieldDomainConstants.BEARER, date);
	}

	public boolean validateJwtToken(final String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
			return true;
		} catch (Exception e) {
			logger.error(MessageDomainConstants.TOKEN_TWT_INVALIDO_MENSAGEM, e);
		}
		return false;
	}
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	public String getUserNameFromJwtToken(final String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
				.getBody().getSubject();
	}
}