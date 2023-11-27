package com.example.demo.config.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	private static final String AUTHORITIES_KEY = "roles";

	@Autowired
	private JwtProperties jwtProperties;

	private SecretKey secretKey;

	@PostConstruct
	protected void init() {
		var secret = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
		secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String createToken(Authentication authentication) {

		String username = authentication.getName();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		var claimsBuilder = Jwts.claims().subject(username);
		if (!authorities.isEmpty()) {
			claimsBuilder.add(AUTHORITIES_KEY,
					authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
		}

		var claims = claimsBuilder.build();

		Date now = new Date();
		Date validity = new Date(now.getTime() + this.jwtProperties.getValidityInMs());

		return Jwts.builder().claims(claims).issuedAt(now).expiration(validity).signWith(this.secretKey, Jwts.SIG.HS256)
				.compact();

	}

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(token).getPayload();

		Object authoritiesClaim = claims.get(AUTHORITIES_KEY);

		Collection<? extends GrantedAuthority> authorities = authoritiesClaim == null ? AuthorityUtils.NO_AUTHORITIES
				: AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(token);
			// parseClaimsJws will check expiration date. No need do here.
			log.info("expiration date: {}", claims.getPayload().getExpiration());
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			log.info("Invalid JWT token: {}", e.getMessage());
			log.trace("Invalid JWT token trace.", e);
		}
		return false;
	}
}
