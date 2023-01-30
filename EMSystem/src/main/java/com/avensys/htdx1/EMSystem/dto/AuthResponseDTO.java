package com.avensys.htdx1.EMSystem.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AuthResponseDTO {
	private String status;
	private String accessToken;
	private String tokenType = "Bearer ";
	private Date expire;
	public AuthResponseDTO(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public AuthResponseDTO(String status, String accessToken) {
		this.status = status;
		this.accessToken = accessToken;
	}

	public AuthResponseDTO(String status, String accessToken, Date expire) {
		this.status = status;
		this.accessToken = accessToken;
		this.expire = expire;
	}
	
	
}
