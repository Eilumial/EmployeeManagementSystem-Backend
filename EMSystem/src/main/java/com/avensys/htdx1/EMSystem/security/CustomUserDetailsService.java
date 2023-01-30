package com.avensys.htdx1.EMSystem.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.avensys.htdx1.EMSystem.entity.Role;
import com.avensys.htdx1.EMSystem.entity.UserEntity;
import com.avensys.htdx1.EMSystem.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepo ur;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = ur.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
		return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
