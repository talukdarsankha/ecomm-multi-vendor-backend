package com.xyz.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtProvider {
	
	SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public String generateToken(Authentication authentication) {
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String roles = populateAuthorities(authorities);

		String jwt = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+864000000))
				.claim("email", authentication.getName())
				.claim("authorities", roles)
				.signWith(key).compact();
		
		return jwt;
		
	}
	
	public String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Set<String> auths = new HashSet<>();
		
		for(GrantedAuthority e: authorities) {
			auths.add(e.getAuthority());
		}
		
		return String.join(",", auths);
	}
	
	
	
	public String getEmailFromToken(String jwt) {
		  
		jwt = jwt.substring(7);
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		
		String email = String.valueOf(claims.get("email"));
		return email;
	}
	
	
	

}
