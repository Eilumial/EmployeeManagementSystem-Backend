package com.avensys.htdx1.EMSystem.repo;

import org.springframework.data.repository.CrudRepository;

import com.avensys.htdx1.EMSystem.entity.ProjectList;

public interface ProjectListRepo extends CrudRepository<ProjectList, Long>{
	public boolean existsByProjectName(String projectName);
}
