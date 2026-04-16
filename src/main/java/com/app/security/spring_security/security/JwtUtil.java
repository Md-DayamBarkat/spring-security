package com.app.security.spring_security.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${app.jwt.secret}")
	private String secret;

	@Value("${app.jwt.expiration-ms}")
	private long expirationMs;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return createToken(claims, userDetails.getUsername());
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private String createToken(Map<String, Object> claims, String subject) {
		Date issuedAt = new Date();
		Date expiryDate = new Date(issuedAt.getTime() + this.expirationMs);
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(subject)
			.setIssuedAt(issuedAt)
			.setExpiration(expiryDate)
			.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret)), SignatureAlgorithm.HS256)
			.compact();
	}

	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret)))
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
}
