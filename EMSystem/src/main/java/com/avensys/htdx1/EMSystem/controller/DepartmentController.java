package com.avensys.htdx1.EMSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.avensys.htdx1.EMSystem.dto.NewDepartmentDTO;
import com.avensys.htdx1.EMSystem.dto.NewDepartmentResponseDTO;
import com.avensys.htdx1.EMSystem.entity.Department;
import com.avensys.htdx1.EMSystem.entity.DepartmentList;
import com.avensys.htdx1.EMSystem.service.DepartmentService;

@RestController
@CrossOrigin(origins = "*")
public class DepartmentController {
	@Autowired
	DepartmentService ds;

	final static String DEP_API_PATH = "/ems/dep";

	@GetMapping(DEP_API_PATH)
	public List<Department> getAllDepartment() {
		return ds.getAllDepartment();
	}

	@GetMapping(DEP_API_PATH + "/list")
	public List<DepartmentList> getAllDepartmentList() {
		return ds.getAllDepartmentList();
	}

	@PostMapping(DEP_API_PATH + "/list")
	public ResponseEntity<NewDepartmentResponseDTO> addDepartmentList(@RequestBody NewDepartmentDTO depL) {
		DepartmentList dl = new DepartmentList(depL.getRole());
		return ds.addDepartmentList(dl);
	}

	@PostMapping(DEP_API_PATH)
	public Department addDepartment(@RequestBody Department dep) {
		return ds.addDepartment(dep);
	}

	@PutMapping(DEP_API_PATH + "/{id}")
	public Department updateDepartment(@PathVariable("id") Long id, @RequestBody Department d) {
		return ds.updateDepartment(id, d);
	}
	
	@PutMapping(DEP_API_PATH + "/list/{id}")
	public DepartmentList updateDepartmentList(@PathVariable("id") Long id, @RequestBody DepartmentList dl) {
		return ds.updateDepartmentList(id, dl);
	}

	@DeleteMapping(DEP_API_PATH + "/{id}/{empid}")
	public String deleteDepartment(@PathVariable("id") Long id, @PathVariable("empid") Long emp_id) {
		System.out.println("Controller Delete");
		return ds.deleteDepartment(id, emp_id);
	}
	
	@DeleteMapping(DEP_API_PATH + "/list/{id}")
	public String deleteDepartmentList(@PathVariable("id") Long id) {
		System.out.println("Controller Delete");
		return ds.deleteDepartmentList(id);
	}
	
	//Get roles currently unassigned to the input employee_id
	@GetMapping(DEP_API_PATH+"/list/na/{id}")
	public List<DepartmentList> getNonAssignedRoles(@PathVariable("id") Long id){
		return ds.getNonAssignedRoles(id);
	}
}
