package com.avensys.htdx1.EMSystem.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * A strategy for handling unauthenticated requests. 
 * The commence method is called when an unauthenticated user tries to access a protected resource, 
 * and is used to commence the authentication process. It typically sends a 401 Unauthorized response to the client,
 * and also starts the authentication process. The JwtAuthEntryPoint class is usually used to handle unauthorized requests 
 * and provide a JSON response with a specific message such as 'Unauthorized' or 'Invalid credentials' 
 * instead of redirecting the user to a login page.
 */

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
	}

}
