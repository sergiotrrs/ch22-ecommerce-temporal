package org.generation.app.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class LoginFilter extends AbstractAuthenticationProcessingFilter {

	protected LoginFilter(String url, AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		//Obtenemos el body de la petición, el cuál vendrá en formato Json
		InputStream body = request.getInputStream();
		
		//Asumimos que el Json tiene la forma {"suername":"sergio", "password":"123"}
		// Realizamos un mapeo a nuestra clase Uer para obtener los datos.
		AuthCredentials user= new ObjectMapper().readValue(body, AuthCredentials.class);
		
		//Autenticamos el usuario		
		
		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						user.getEmail(),
						user.getPassword(),
						Collections.emptyList()
						)
				);
	}
	
	@Override
	  protected void successfulAuthentication(
	            HttpServletRequest req,
	            HttpServletResponse res, FilterChain chain,
	            Authentication auth) throws IOException, ServletException {		
		
	// si la autentificación fue correcta, agregamos el token a la respueta
		JwtUtil.addAuthentication(res, auth.getName());
	
	}
	
		

}
