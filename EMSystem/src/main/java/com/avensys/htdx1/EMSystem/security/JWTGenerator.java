package com.avensys.htdx1.EMSystem.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.avensys.htdx1.EMSystem.entity.JwtBlacklist;
import com.avensys.htdx1.EMSystem.repo.JwtBlacklistRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTGenerator {

	@Autowired
	JwtBlacklistRepo jbr;

	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
		/*
		 * JWT claims are a set of key-value pairs that are encoded in a JSON object and
		 * used to securely transmit information between two parties. These claims are
		 * contained in the payload of a JSON Web Token (JWT). They are typically used
		 * to transmit information such as the identity of the user, the user's roles
		 * and permissions, and any other information that is required by the
		 * application. JWT claims are signed and encrypted, which makes them
		 * tamper-proof and secure. This means that the information contained in a JWT
		 * claim cannot be altered without detection, and it can be verified that the
		 * information came from a trusted source.
		 */
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("authorities", authentication.getAuthorities());
		String token = Jwts.builder()
//	                .setSubject(username)
				.setClaims(claims).setIssuedAt(new Date()).setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET).compact();
		return token;
	}

	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			if (jbr.existsByJwt(token)) {
				throw new Exception("");
			}
			Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token);
			System.out.println("Past parseclaims");
			return true;
		} catch (Exception ex) {
			System.out.println("JWT was expired or incorrect.");
			throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect.");
		}
	}

	public void invalidateToken(String token) {
		System.out.println("JWT Exist:" + jbr.existsByJwt(token));
		if (!jbr.existsByJwt(token)) {

			jbr.save(new JwtBlacklist(token));
		}
	}
}
