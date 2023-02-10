package com.avensys.htdx1.EMSystem.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		//Retrieve username from Security Context
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(username=="anonymousUser") {
			username="SELF-REGISTER";
		}
		
		return Optional.ofNullable(username);
		// Old code:
		// return ((User)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()
	}
}