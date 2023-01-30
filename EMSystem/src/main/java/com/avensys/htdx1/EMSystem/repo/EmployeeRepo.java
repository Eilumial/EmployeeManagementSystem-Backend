package com.avensys.htdx1.EMSystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.avensys.htdx1.EMSystem.entity.Employee;

public interface EmployeeRepo extends CrudRepository<Employee, Long> {
	
	public Employee findById(int id);
	
	public List<Employee> findByDepartmentsDepartmentIdId(Long id);
	public List<Employee> findByProjectsProjectIdId(Long id);
	public List<Employee> findByEmail(String email);
//	public List<Long> findAllIdByEmail(String email);
//	public List<Employee> findByEmail(String email);
//	public Employee findByEmail(String email);
	public Employee findByUserEntityId(Long id);
	public boolean existsByUserEntityId(Long id);
	public boolean existsByIdAndUserEntityId(long id, long userId);
	public boolean existsByEmail(String email);
	
	@Query(value = "SELECT id FROM employee WHERE email = :email", nativeQuery = true)
	List<Long> findAllIdByEmail(@Param("email") String email);
}

