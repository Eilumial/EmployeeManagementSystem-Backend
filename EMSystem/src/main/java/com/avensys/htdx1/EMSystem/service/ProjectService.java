package com.avensys.htdx1.EMSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.avensys.htdx1.EMSystem.dto.NewDepartmentResponseDTO;
import com.avensys.htdx1.EMSystem.dto.NewProjectResponseDTO;
import com.avensys.htdx1.EMSystem.entity.DepartmentList;
import com.avensys.htdx1.EMSystem.entity.Project;
import com.avensys.htdx1.EMSystem.entity.ProjectList;
import com.avensys.htdx1.EMSystem.repo.EmployeeRepo;
import com.avensys.htdx1.EMSystem.repo.ProjectListRepo;
import com.avensys.htdx1.EMSystem.repo.ProjectRepo;

import jakarta.transaction.Transactional;

@Service
public class ProjectService {
	@Autowired
	ProjectRepo pr;
	@Autowired
	ProjectListRepo plr;
	@Autowired
	EmployeeRepo er;

	public List<Project> getAllProject() {
		List<Project> projList = (List<Project>) pr.findAll();
		return projList;
	}

	public List<ProjectList> getAllProjectList() {
		List<ProjectList> projList = (List<ProjectList>) plr.findAll();
		return projList;
	}

	public Project addProject(Project proj) {

		// Retrieve project ID and employee ID
		Long project_id = proj.getProjectId().getId();
		Long employee_id = proj.getProjectId().getEmployee_id();

		// Project composite key of Dep ID and Emp ID
		// If no entry exists in DB which matches both of the IDs in received Dep
		if (!pr.existsByProjectIdIdAndEmployeeId(project_id, employee_id)) {
			// Then retrieve Employee from DB based on emp ID
			// and save in DB
//			
			proj.setEmployee(er.findById(employee_id.intValue()));
			return pr.save(proj);
		} else {
			return new Project();
		}

	}

	public ResponseEntity<NewProjectResponseDTO> addProjectList(ProjectList projL) {
		if (!plr.existsByProjectName(projL.getProjectName())) {
			ProjectList pl = plr.save(projL);
			NewProjectResponseDTO newDR = new NewProjectResponseDTO("success", "Project creation successful",
					pl.getId(), pl.getProjectName());
			return new ResponseEntity<>(newDR, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new NewProjectResponseDTO("failure", "Project already exists"),
					HttpStatus.BAD_REQUEST);
		}
	}

//	public ResponseEntity<NewDepartmentResponseDTO> addDepartmentList(DepartmentList depL) {
////		System.out.println("Before Department List save: "+depL);
//		if (!dlr.existsByRole(depL.getRole())) {
//			DepartmentList dl = dlr.save(depL);
//			NewDepartmentResponseDTO newDR = new NewDepartmentResponseDTO("success", "Department creation successful",
//					dl.getId(), dl.getRole());
//			return new ResponseEntity<>(newDR, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(new NewDepartmentResponseDTO("failure", "Department already exists"),
//					HttpStatus.BAD_REQUEST);
//		}
//
////		return new ResponseEntity<>(
////				new RegUserResponseDTO("success", "User #" + us.getId() + " register successful", us.getId()),
////				HttpStatus.OK);
//	}

	public Project updateProject(Long id, Project proj) {

		// Retrieve employee ID
		Long employee_id = proj.getProjectId().getEmployee_id();

		if (pr.existsByProjectIdIdAndEmployeeId(id, employee_id)) {
			Project p1 = pr.findByProjectIdIdAndEmployeeId(id, employee_id);

			p1.setProject_name(proj.getProject_name());
			p1.setEmployee(er.findById(employee_id.intValue()));

			return pr.save(p1);
		} else {
			return new Project();
		}
	}

	@Transactional
	public String deleteProject(Long id, Long emp_id) {
		System.out.println("Service Delete Project");
		if (pr.existsByProjectIdIdAndEmployeeId(id, emp_id)) {
			int del_count = pr.deleteByProjectIdIdAndEmployeeId(id, emp_id);
			if (del_count == 1) {
				return "Record of Project ID (" + id + ") Employee ID (" + emp_id + ") has been deleted";
			} else if (del_count < 1) {
				return "Delete operation failed";
			} else {
				return "Multiple records deleted, something must be wrong";
			}
		} else {
			return "No record of Project ID (" + id + ") Employee ID (" + emp_id + ") was found";
		}
	}

	@Transactional
	public ProjectList updateProjectList(Long id, ProjectList plInput) {
		if (plr.existsById(id)&&!plr.existsByProjectName(plInput.getProjectName())) {
			ProjectList p = plr.findById(id).get();
			p.setProjectName(plInput.getProjectName());

			int count = pr.updateProjectName(plInput.getProjectName(), id);
			
			return plr.save(p);
		} else {
			return new ProjectList();
		}
	}
	
	@Transactional
	public String deleteProjectList(Long id) {
		if(plr.existsById(id)) {
			plr.deleteById(id);
			pr.deleteByProjectIdId(id);
			return "Record of Project (*name*) has been deleted";
		} else {
			return "No record of Project (*name*) was found";
		}
	}

}
