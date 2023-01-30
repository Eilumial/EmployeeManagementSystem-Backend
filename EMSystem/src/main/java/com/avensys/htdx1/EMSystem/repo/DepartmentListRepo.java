package com.avensys.htdx1.EMSystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.avensys.htdx1.EMSystem.entity.DepartmentList;

public interface DepartmentListRepo extends CrudRepository<DepartmentList, Long>{
	public boolean existsByRole(String role);
	
	@Query(value = "SELECT * FROM Department_List WHERE Department_List.id NOT IN (SELECT Department.id FROM Department WHERE Department.employee_id = :id);", nativeQuery = true)
	public List<DepartmentList> selectNonAssigned(@Param("id") Long id);
	
//	SELECT Department_List.id, Department_List.role
//	FROM Department_List
//	WHERE Department_List.id NOT IN (
//	  SELECT Department.id FROM Department WHERE Department.employee_id = 1402);
}
