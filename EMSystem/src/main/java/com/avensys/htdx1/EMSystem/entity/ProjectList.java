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
@Table(name = "ProjectList")
public class ProjectList extends Auditable<String> {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String projectName;
	
	public ProjectList(String projectName) {
		this.projectName = projectName;
	}

	public ProjectList() {
		// TODO Auto-generated constructor stub
	}
//	
//	@ManyToMany(fetch = FetchType.EAGER)
//	private UserEntity userEntity;
}