package com.avensys.htdx1.EMSystem.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * This code is defining a custom filter called JWTAuthenticationFilter that is used to authenticate requests made to the application.
 * This filter is added to the security filter chain before the UsernamePasswordAuthenticationFilter, which is a filter that is provided by Spring Security
 *  for handling basic authentication.
 * 
 * The filter's doFilterInternal method first attempts to extract a JSON Web Token (JWT) from the request's "Authorization" header. If a token is found, 
 * it is passed to the JWTGenerator's validateToken method to check if it is a valid token. If it is valid, the getUsernameFromJWT method is used to extract
 *  the username from the token, and the CustomUserDetailsService is used to load the user's details using the username.

A UsernamePasswordAuthenticationToken is then created using the user's details and authorities, and it is set as the current authentication in the
 SecurityContextHolder. The filter then allows the request to continue through the filter chain by calling filterChain.doFilter(request, response).

If at any point the token is found to be invalid or expired, the filter will set the response status to 401 (Unauthorized) and write a JSON error message
 to the response.

This JWT filter is responsible for validating the token present in the request and if valid, it sets the authentication details in the SecurityContextHolder
 for further access checks and authorization.

 */

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTGenerator tokenGen;

	@Autowired
	private CustomUserDetailsService cuds;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getJWTFromRequest(request);

			if (StringUtils.hasText(token) && tokenGen.validateToken(token)) {
//			System.out.println("Past hasText and validateToken");
				String username = tokenGen.getUsernameFromJWT(token);
//			System.out.println("Username: " + username);
				UserDetails userDetails = cuds.loadUserByUsername(username);
//			System.out.println("User Details:"+userDetails);
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
//			System.out.println("UsernamePWAuthToken:" + authToken);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//			authToken.setAuthenticated(true);
//			System.out.println("UsernamePWAuthToken after set:" + authToken);
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
//		System.out.println("End of JWT Filter if block");
//		System.out.println("Request: "+ request);
//		System.out.println("Response: "+ response);
			filterChain.doFilter(request, response);
		} catch (AuthenticationCredentialsNotFoundException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.getWriter().write("{\"error\":\"Invalid Token or Token Expired\"}");
		}
	}

	
	/*
	 * The "Bearer " prefix is a standard specified in the RFC 6750 (The OAuth 2.0 Authorization Framework: Bearer Token Usage) for 
	 * the Authorization header in HTTP requests.It's used to indicate that the token following it is a Bearer token, which is a type 
	 * of token that's issued to the client to access a protected resource, such as an API endpoint. The prefix "Bearer " is used to indicate 
	 * that the token is a Bearer token and it is used to prevent ambiguity with other types of tokens that may be present in the Authorization header.

When the server receives a request with a Bearer token, it checks the token and verifies that it's valid and belongs to the right user.
 It's common practice to include the "Bearer " prefix in the token so that the server can quickly identify that this is a bearer token and 
 not another type of token.

In the code above, the getJWTFromRequest method first extracts the Authorization header from the request, then checks if it starts with 
"Bearer ", then returns the token by substringing the "Bearer " prefix so that it can be passed to the validateToken() and getUsernameFromJWT() methods.
	 */
	
	private String getJWTFromRequest(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		System.out.println("Bearer Token: " + bearerToken);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			String s = bearerToken.substring(7, bearerToken.length());
			System.out.println(s);
			return s;
		}
		return null;

	}

}
