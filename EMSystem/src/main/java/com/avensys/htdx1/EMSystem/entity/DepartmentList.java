package com.avensys.htdx1.EMSystem.entity;

import com.avensys.htdx1.EMSystem.audit.Auditable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "DepartmentList")
public class DepartmentList extends Auditable<String> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String role;
//	
//	@ManyToMany(fetch = FetchType.EAGER)
//	private UserEntity userEntity;

	public DepartmentList(String role) {
		this.role = role;
	}
	
	public DepartmentList(Long id, String role) {
		this.id = id;
		this.role = role;
	}
	
	public DepartmentList() {
		
	}
	
	
}