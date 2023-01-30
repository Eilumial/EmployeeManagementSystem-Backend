package com.avensys.htdx1.EMSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "jwtblacklist")
public class JwtBlacklist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String jwt;

	public JwtBlacklist(String jwt) {
		this.jwt = jwt;
	}
	
	public JwtBlacklist() {
		
	}
}
