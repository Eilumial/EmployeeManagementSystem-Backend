package com.avensys.htdx1.EMSystem.dto;

import lombok.Data;

@Data
public class RegUserResponseDTO {
	private String status;
	private String msg;
	private Long id;
	public RegUserResponseDTO(String status, String msg) {
		this.status = status;
		this.msg = msg;
	}
	public RegUserResponseDTO(String status, String msg, Long id) {
		this.status = status;
		this.msg = msg;
		this.id = id;
	}
}
