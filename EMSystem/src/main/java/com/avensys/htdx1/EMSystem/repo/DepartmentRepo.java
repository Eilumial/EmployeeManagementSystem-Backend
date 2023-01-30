package com.avensys.htdx1.EMSystem.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.avensys.htdx1.EMSystem.entity.Department;

public interface DepartmentRepo extends CrudRepository<Department, Long> {

	public Department findByEmployeeId(Long id);

//public boolean existsByDepartmentIdIdAndDepartmentIdEmployee_id(Long id, Long employee_id);
	// public boolean existsByIdAndEmployee_Id(Long id, Long empId);
	public boolean existsByDepartmentIdIdAndEmployeeId(Long id, Long empId);
	public Department findByDepartmentIdIdAndEmployeeId(Long id, Long empId);

	public Integer deleteByDepartmentIdIdAndEmployeeId(Long id, Long empId);
	
	public Integer deleteByDepartmentIdId(Long id);

	//@Query(value = "SELECT id FROM employee WHERE email = :email", nativeQuery = true)
	@Modifying
	@Query(value ="UPDATE Department SET role = :newRole WHERE id = :id", nativeQuery = true)
	public Integer updateRole(@Param("newRole") String newRole, @Param("id") Long id);
//	UPDATE Department SET role = 'Opts' WHERE id = 3;
	
	
}
