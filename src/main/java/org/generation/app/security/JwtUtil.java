package org.generation.app.security;

import java.util.Date;



import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static java.util.Collections.emptyList;

/**
 * Esta clase nos ayuda a generar el token JWT y también para verificar el token
 * @author TuX3
 *
 */

public class JwtUtil {
	static final String secretTxt = "bc3562bcd57e480c802202c79f14add2802202c79f14add2802202c79f14add2802202c79f14add2"; 
	
	//Método para crear el tokenJWT y enviarlo al cliente en el header de la respuesta
	static void addAuthentication(HttpServletResponse response, String username) {
		
		String token = Jwts.builder()
				.setSubject(username)
				//Agregamos un tiempo de expiración de 5 minutos
				.setExpiration(new Date(System.currentTimeMillis()+300000))
				.signWith(SignatureAlgorithm.HS256, secretTxt)
				.compact();
		
		//agregamos en el encabezado el token
		response.addHeader("Authorization", "Bearer " + token);
	}
	
	//Método para calidar el token recibido por el cliente
	static Authentication getAuthentication(HttpServletRequest request) {
		
		//Obtenemos el token que viene en el encabezado de la petición
		String token = request.getHeader("Authorization");
		
		//Si hay un token, lo validamos
		if (token != null) {
			String user = Jwts.parser()
					.setSigningKey(secretTxt)
					//Validamos el token
					.parseClaimsJws(token.replace("Bearer ", "")) 
					.getBody()
					.getSubject();
			return user !=null ?
					new UsernamePasswordAuthenticationToken(user, null, emptyList()): null ;
		}		
		return null;		
	}
	

}