package com.avensys.htdx1.EMSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avensys.htdx1.EMSystem.security.JWTGenerator;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@CrossOrigin(origins="*")
public class HomeController {
	
	@Autowired
	JWTGenerator tokenGen;
	
	@GetMapping("/test")
	public String home() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		return "Hello to " + username + " from JWT!";
	}
	
	@GetMapping("/test2")
	public String home2() {
		return "TEST 2!";
	}
	
	// ("/logout") taken? Does not work
	@GetMapping("/logou")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
	  // invalidate the token
		System.out.println("LOGGING OUT");
		try {
		String token = request.getHeader("Authorization");
		  System.out.println("Token:"+token);
		  if (token != null && token.startsWith("Bearer ")) {
			  System.out.println("INSIDE IF");
			  token = token.substring(7);
			  System.out.println("Substring Token: "+token);
			  tokenGen.invalidateToken(token);
			  
//			  SecurityContextHolder.clearContext();
			  
			  // remove JWT from client-side storage
			    Cookie cookie = new Cookie("jwt", null);
			    cookie.setMaxAge(0);
			    cookie.setHttpOnly(true);
			    response.addCookie(cookie);
			  
			  return new ResponseEntity<>("User logout successful", HttpStatus.OK);
		  }else {
			  return new ResponseEntity<>("Invalid Token", HttpStatus.NOT_ACCEPTABLE);
		  }
		}
		catch (AuthenticationCredentialsNotFoundException ex) {
			  return new ResponseEntity<>("JWT already invalid", HttpStatus.NOT_ACCEPTABLE);
		}
		  


	  
	}
	
//	@GetMapping("/logout")
//	public ResponseEntity<?> logout(HttpServletRequest request) {
//	  // invalidate the token
//		System.out.println("LOGGING OUTTTTTTTT");
//	  String token = request.getHeader("Authorization");
//	  System.out.println("Token:"+token);
//	  if (token != null && token.startsWith("Bearer ")) {
//	    token = token.substring(7);
//	    System.out.println("New Token:"+token);
//	    tokenGen.invalidateToken(token);
//	  }
//	  return ResponseEntity.notFound().build();
//	}
}
