package com.avensys.htdx1.EMSystem.dto;

import lombok.Data;

@Data
public class PWChangeDTO {
	private String username;
	private String password;
	private String oldpassword;
}
