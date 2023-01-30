package com.avensys.htdx1.EMSystem.dto;

import lombok.Data;

@Data
public class NewProjectResponseDTO {
	private String status;
	private String msg;
	private Long id;
	private String projectName;
	public NewProjectResponseDTO(String status, String msg, Long id, String projectName) {
		this.status = status;
		this.msg = msg;
		this.id = id;
		this.projectName = projectName;
	}
	public NewProjectResponseDTO(String status, String msg) {
		this.status = status;
		this.msg = msg;
	}
	
	
}
