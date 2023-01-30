package com.avensys.htdx1.EMSystem.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.avensys.htdx1.EMSystem.entity.Project;

public interface ProjectRepo extends CrudRepository<Project, Long> {

	public Project findByEmployeeId(Long id);

	public Project findByProjectIdIdAndEmployeeId(Long id, Long empId);
	
	public boolean existsByProjectIdIdAndEmployeeId(Long id, Long empId);

	public Integer deleteByProjectIdIdAndEmployeeId(Long id, Long empId);
	
	public Integer deleteByProjectIdId(Long id);
	
	@Modifying
	@Query(value ="UPDATE Project SET project_name = :newProjName WHERE id = :id", nativeQuery = true)
	public int updateProjectName(@Param("newProjName") String newProjName, @Param("id") Long id);

}
