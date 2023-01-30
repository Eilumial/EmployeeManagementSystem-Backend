package com.avensys.htdx1.EMSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.avensys.htdx1.EMSystem.dto.NewDepartmentResponseDTO;
import com.avensys.htdx1.EMSystem.dto.RegUserResponseDTO;
import com.avensys.htdx1.EMSystem.entity.Department;
import com.avensys.htdx1.EMSystem.entity.DepartmentList;
import com.avensys.htdx1.EMSystem.repo.DepartmentListRepo;
import com.avensys.htdx1.EMSystem.repo.DepartmentRepo;
import com.avensys.htdx1.EMSystem.repo.EmployeeRepo;

import jakarta.transaction.Transactional;

@Service
public class DepartmentService {
	@Autowired
	DepartmentRepo dr;

	@Autowired
	DepartmentListRepo dlr;

	@Autowired
	EmployeeRepo er;

	public List<Department> getAllDepartment() {
		List<Department> depList = (List<Department>) dr.findAll();
		return depList;
	}

	public List<DepartmentList> getAllDepartmentList() {
		List<DepartmentList> depLList = (List<DepartmentList>) dlr.findAll();
		return depLList;
	}

	public Department addDepartment(Department dep) {

		// Retrieve department ID and employee ID
		Long department_id = dep.getDepartmentId().getId();
		Long employee_id = dep.getDepartmentId().getEmployee_id();

		// Department composite key of Dep ID and Emp ID
		// If no entry exists in DB which matches both of the IDs in received Dep
		if (!dr.existsByDepartmentIdIdAndEmployeeId(department_id, employee_id)) {
			// Then retrieve Employee from DB based on emp ID
			// and save in DB
//			Long id = dep.getDepartmentId().getEmployee_id();
			dep.setEmployee(er.findById(employee_id.intValue()));
			return dr.save(dep);
		} else {
			return new Department();
		}

	}

	public Department updateDepartment(Long id, Department dep) {

		// Retrieve employee ID
		Long employee_id = dep.getDepartmentId().getEmployee_id();

		if (dr.existsByDepartmentIdIdAndEmployeeId(id, employee_id)) {
			Department d1 = dr.findByDepartmentIdIdAndEmployeeId(id, employee_id);

			d1.setRole(dep.getRole());
			d1.setEmployee(er.findById(employee_id.intValue()));

			return dr.save(d1);
		} else {
			return new Department();
		}
	}
	
	@Transactional
	public DepartmentList updateDepartmentList(Long id, DepartmentList dep) {

		// Retrieve employee ID
//		Long dlist_id = dep.getId();

		if (dlr.existsById(id)&&!dlr.existsByRole(dep.getRole())) {
			DepartmentList dl = dlr.findById(id).get();
			dl.setRole(dep.getRole());

			int count = dr.updateRole(dep.getRole(), id);
			System.out.println("Update count: " + count);
			
			return dlr.save(dl);
		} else {
			return new DepartmentList();
		}
	}

	@Transactional
	public String deleteDepartment(Long id, Long emp_id) {
		System.out.println("Service Delete department");
		if (dr.existsByDepartmentIdIdAndEmployeeId(id, emp_id)) {
			int del_count = dr.deleteByDepartmentIdIdAndEmployeeId(id, emp_id);
			if (del_count == 1) {
				return "Record of Department ID (" + id + ") Employee ID (" + emp_id + ") has been deleted";
			} else if (del_count < 1) {
				return "Delete operation failed";
			} else {
				return "Multiple records deleted, something must be wrong";
			}
		} else {
			return "No record of Department ID (" + id + ") Employee ID (" + emp_id + ") was found";
		}
	}

	public ResponseEntity<NewDepartmentResponseDTO> addDepartmentList(DepartmentList depL) {
//		System.out.println("Before Department List save: "+depL);
		System.out.println("Exist by Role: "+dlr.existsByRole(depL.getRole()));
		if (!dlr.existsByRole(depL.getRole())) {
			DepartmentList dl = dlr.save(depL);
			NewDepartmentResponseDTO newDR = new NewDepartmentResponseDTO("success", "Department creation successful",
					dl.getId(), dl.getRole());
			return new ResponseEntity<>(newDR, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new NewDepartmentResponseDTO("failure", "Department already exists"),
					HttpStatus.BAD_REQUEST);
		}

//		return new ResponseEntity<>(
//				new RegUserResponseDTO("success", "User #" + us.getId() + " register successful", us.getId()),
//				HttpStatus.OK);
	}

	@Transactional
	public String deleteDepartmentList(Long id) {
		System.out.println("Service Delete Department List");
		if (dlr.existsById(id)) {
			dlr.deleteById(id);
			dr.deleteByDepartmentIdId(id);
//			if (del_count == 1) {
//				return "Record of Department ID (" + id + ") Employee ID (" + emp_id + ") has been deleted";
//			} else if (del_count < 1) {
//				return "Delete operation failed";
//			} else {
//				return "Multiple records deleted, something must be wrong";
//			}
			
			
			return "Record of Department (*name*) has been deleted";
		} else {
			return "No record of Department (*name*) was found";
		}
	}
	
	public List<DepartmentList> getNonAssignedRoles(Long id){
		return dlr.selectNonAssigned(id);
	}

}
