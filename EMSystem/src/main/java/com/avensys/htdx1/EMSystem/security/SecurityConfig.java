package com.avensys.htdx1.EMSystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService userDetailsSevice;

	@Autowired
	private JwtAuthEntryPoint authEntryPoint;

	@Autowired
	public JWTAuthenticationFilter jwtFilter;

	// This code is configuring a SecurityFilterChain bean using the HttpSecurity
	// object passed in as a parameter. The HttpSecurity object is used to configure
	// various security options for the application.
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()// The first line in the method disables CORS (Cross-Origin Resource Sharing) 
											//and CSRF (Cross-site request forgery)
											// protection, which is enabled by default in Spring Security.

//		exceptionHandling().authenticationEntryPoint(authEntryPoint) is used to configure an AuthenticationEntryPoint 
//		that will be invoked when an authentication failure occurs. The authEntryPoint is an instance of JwtAuthEntryPoint 
//		which will handle unauthorized (401) responses.
				.exceptionHandling().authenticationEntryPoint(authEntryPoint).and()

				// using the authorizeHttpRequests() method to authorize all incoming requests.

				/*
				 * sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) is
				 * used to configure the session management policy for the application. By
				 * setting the policy to SessionCreationPolicy.STATELESS, it ensures that no
				 * session is created or used by Spring Security. This means that the
				 * application will not maintain a session and all requests will be treated as
				 * stateless. In the context of web development, a stateless application is one
				 * in which the server does not store any information about the client session.
				 * This means that each request made to the server is self-contained, and the
				 * server does not maintain any information about the client's previous
				 * requests. This is in contrast to stateful applications, in which the server
				 * maintains information about the client's session and uses it to process
				 * subsequent requests. Stateless applications are generally considered to be
				 * more scalable and easier to maintain, because they do not require the server
				 * to keep track of the client's state.
				 */
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				/*
				 * authorizeHttpRequests() is used to configure the authorization rules for the
				 * application. This method returns an ExpressionUrlAuthorizationConfigurer
				 * which can be used to set up the authorization rules for different URLs in the
				 * application. For example, you can use it to configure which URLs are
				 * accessible to which roles or users.
				 */

				.and().authorizeHttpRequests()

				/*
				 * The requestMatchers("/api/auth/**") method is specifying that any request
				 * that starts with "/api/auth/" should be permitted for all users, regardless
				 * of authentication. The .anyRequest().authenticated() method is specifying
				 * that any other request made to the application should be authenticated.
				 * Finally, the .httpBasic() method is enabling basic authentication for the
				 * application. This means that users will have to provide a valid username and
				 * password in order to access any resources on the application.
				 */

				.requestMatchers("/api/auth/**").permitAll()
				
//				.hasRole("ADMIN")
//				.hasAnyRole("USER","ADMIN")
//				.requestMatchers(HttpMethod.GET).authenticated()
//				.requestMatchers(HttpMethod.PUT).authenticated()
//				.antMatchers(HttpMethod.GET, "/api/auth/**").hasRole("ADMIN")
//				.antMatchers(HttpMethod.POST, "/api/auth/**").hasAnyRole("ADMIN", "USER")
				.anyRequest().authenticated() //// Then, the anyRequest() method is used to specify that any request
				//// made to the application should be authenticated.
				
//				.formLogin()
//	                .and()
//	            .logout()
//	                .logoutUrl("/logout")
//	                .logoutSuccessUrl("/login?logout")
//	                .addLogoutHandler(logoutHandler)
//	                .logoutSuccessHandler(logoutSuccessHandler)
//	                .invalidateHttpSession(true)
//	                .deleteCookies("JSESSIONID")
//	                .permitAll();
				
				
				
				.and()// The and() method is used to chain together multiple security configurations.
				.httpBasic(); // Finally, httpBasic() method is used to enable basic authentication for the
								// application.

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
		// The http.build() return the security filter chain containing all the security
		// configuration that's been set up.
		return http.build();
	}
	// SAMPLE CODE
	// change antMatchers to requestMatchers
	// Limit /api
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	    http.cors().and().csrf().disable()
//
//	        .exceptionHandling().authenticationEntryPoint(authEntryPoint).and().sessionManagement()
//	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests()
//	        .requestMatchers("/api/auth/**").permitAll()
//			.requestMatchers("/api/user/**").hasRole("USER")
//			.requestMatchers("/api/**").hasRole("ADMIN")
//	        	
//			.requestMatchers(HttpMethod.GET, "/api/user/**").hasRole("USER")
//	        .requestMatchers(HttpMethod.POST, "/api/admin/**").hasRole("ADMIN")
//	        
//	        .and()
//	        .httpBasic(); 
//
//	    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//	    return http.build();
//	}

//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.cors().and().csrf().disable()
//
//				.exceptionHandling().authenticationEntryPoint(authEntryPoint).and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests()
//				.requestMatchers("/api/auth/**").permitAll()
//				.requestMatchers(HttpMethod.GET).authenticated()
//				.anyRequest().authenticated() 
//				.and()
//				.httpBasic(); 
//
//		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//		
//		return http.build();
//	}

//	@Bean
//	public UserDetailsService users() {
//		UserDetails admin = User.builder().username("admin").password("admin").roles("ADMIN").build();
//		UserDetails user = User.builder().username("user").password("user").roles("USER").build();
//		
//		return new InMemoryUserDetailsManager(admin, user);
//	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {

		// return NoOpPasswordEncoder.getInstance();

		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public JWTAuthenticationFilter jwtAuthenticationFilter() {
//		return new JWTAuthenticationFilter();
//	}

}
