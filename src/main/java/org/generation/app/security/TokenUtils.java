package org.generation.app.security;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Esta clase nos ayuda a generar el token JWT y también para verificar el token
 * @author TuX3
 *
 */
public class TokenUtils {
	private final static String ACCESS_TOKEN_SECRET = "crR/lggroR-o6?qFOpAuTTRkt!aGW49wsys5mwkJP9YnLNVPuuJ2Z!jXiJGGzULqmIgWltkfcYQD/twTzCo/zb/wt=jVP/2NjFd2aVc!uKpdNiDB48-lNFRNTZ7i2L36ZCEhUWUDYtkGjG3BDZcA5SSJzRYClKvAZjLGGUx5MW8ZZUj7iSVv!3eA4BcayMr6IdjxPZqIU7h0?JcsI3mohEFlFHb2MCRJRvNDsey3mHMbcQfH5ChVz!FzJjIlMsVY";
	private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 300L;
	
	/**
	 * Crea el tokenJWT y envía al cliente en el header de la respuesta
	 * @param nombre
	 * @param email
	 * @return
	 */
	public static String createToken(String nombre, String email) {
		long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
		Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
		
		Map<String, Object> extra = new HashMap<>();
		extra.put("name", nombre);
		
		return Jwts
				.builder()
				.setSubject(email)
				.setExpiration(expirationDate)
				.addClaims(extra)
				.signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
				//.signWith(SignatureAlgorithm.HS256, ACCESS_TOKEN_SECRET.getBytes())
				.compact();						
	}
	
	/**
	 * Validar el token recibido por el cliente
	 * @param token
	 * @return
	 */
	public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
		
		try {
			Claims claims = Jwts
					.parserBuilder()
					.setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
					.build()
					.parseClaimsJws(token)
					.getBody();
			String email = claims.getSubject();
			
			return new UsernamePasswordAuthenticationToken(email,null, Collections.emptyList());			
		} catch (JwtException e) {
			System.out.println(e);
			return null;
		}
	}
	
			

}