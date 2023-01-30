package com.avensys.htdx1.EMSystem.dto;

import lombok.Data;

@Data
public class RegUserDTO {
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private Integer age;
	private String gender;
	private Double salary;
	private String email;
}
