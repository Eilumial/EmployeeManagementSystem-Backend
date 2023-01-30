package com.avensys.htdx1.EMSystem.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avensys.htdx1.EMSystem.dto.AuthResponseDTO;
import com.avensys.htdx1.EMSystem.dto.LoginUserDTO;
import com.avensys.htdx1.EMSystem.dto.RegUserDTO;
import com.avensys.htdx1.EMSystem.dto.RegUserResponseDTO;
import com.avensys.htdx1.EMSystem.entity.Role;
import com.avensys.htdx1.EMSystem.entity.UserEntity;
import com.avensys.htdx1.EMSystem.repo.RoleRepo;
import com.avensys.htdx1.EMSystem.repo.UserRepo;
import com.avensys.htdx1.EMSystem.security.JWTGenerator;
import com.avensys.htdx1.EMSystem.security.SecurityConstants;
import com.avensys.htdx1.EMSystem.service.AuthService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager am;
	@Autowired
	private AuthService as;
	@Autowired
	private UserRepo ur;
	@Autowired
	private RoleRepo rr;
	@Autowired
	private PasswordEncoder pe;
	@Autowired
	private JWTGenerator jwtGen;
	
	@PostMapping("register")
	public ResponseEntity<RegUserResponseDTO> register(@RequestBody RegUserDTO regUserDTO) {
		return as.addUserEmployee(regUserDTO);
	}

	// if using RequestMapping, the / after the path is added automatically before
	// the @PostMapping path
//	@PostMapping("register")
//	public ResponseEntity<RegUserResponseDTO> register(@RequestBody RegUserDTO regUserDTO) {
//		if (ur.existsByUsername(regUserDTO.getUsername())) {
//			return new ResponseEntity<>(
//					new RegUserResponseDTO("failure", "Username already exists, please try another"),
//					HttpStatus.BAD_REQUEST);
//		}
//
//		UserEntity user = new UserEntity();
//		user.setUsername(regUserDTO.getUsername());
//		// user.setPassword(pe.encode(regUser.getPassword()));
//		user.setPassword(regUserDTO.getPassword());
//
////		Role roles = rr.findByName("USER").get();
//		Role user_role = rr.findByName("USER").get();
//		Role admin_role = rr.findByName("ADMIN").get();
//		List<Role> roles = new ArrayList<>();
//		roles.add(user_role);
//
//		// >>>>>ADMIN ROLE<<<<<<
//		roles.add(admin_role);
//		user.setRoles(roles);
//		System.out.println(user.toString());
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		UserEntity us = ur.save(user);
//
//		return new ResponseEntity<>(
//				new RegUserResponseDTO("success", "User #" + us.getId() + " register successful", us.getId()),
//				HttpStatus.OK);
//	}

	@PostMapping("login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginUserDTO logUser) {

		try {
			System.out.println(logUser);
			Authentication authentication = am.authenticate(
					new UsernamePasswordAuthenticationToken(logUser.getUsername(), logUser.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtGen.generateToken(authentication);
			Date currentDate = new Date();
			Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
			System.out.println("Token in Controller/Login:" + token);
			return new ResponseEntity<>(new AuthResponseDTO("success", token, expireDate), HttpStatus.OK);
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(new AuthResponseDTO("failure", "Invalid username or password"),
					HttpStatus.UNAUTHORIZED);
		}

//		Authentication authentication = am.authenticate(new UsernamePasswordAuthenticationToken(logUser.getUsername(), logUser.getPassword()));
//		
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String token = jwtGen.generateToken(authentication);
//		
//		return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
	}

//	
//	@PostMapping("login")
//	public ResponseEntity<String> login(@RequestBody LoginUser logUser){
//		Authentication authentication = am.authenticate(new UsernamePasswordAuthenticationToken(logUser.getUsername(), logUser.getPassword()));
//		
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		return new ResponseEntity<>("Login Success!", HttpStatus.OK);
//	}

}
