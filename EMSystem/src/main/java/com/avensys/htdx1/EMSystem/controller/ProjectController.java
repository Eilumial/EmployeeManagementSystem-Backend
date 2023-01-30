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
import com.avensys.htdx1.EMSystem.dto.NewProjectDTO;
import com.avensys.htdx1.EMSystem.dto.NewProjectResponseDTO;
import com.avensys.htdx1.EMSystem.entity.DepartmentList;
import com.avensys.htdx1.EMSystem.entity.Project;
import com.avensys.htdx1.EMSystem.entity.ProjectList;
import com.avensys.htdx1.EMSystem.service.ProjectService;

@RestController
@CrossOrigin(origins = "*")
public class ProjectController {
	@Autowired
	ProjectService ps;
	
	final static String PROJ_API_PATH = "/ems/proj";
	
	@GetMapping(PROJ_API_PATH)
	public List<Project> getAllProject(){
		return ps.getAllProject();
	}
	
	@GetMapping(PROJ_API_PATH+"/list")
	public List<ProjectList> getAllProjectList(){
		return ps.getAllProjectList();
	}
	
	@PostMapping(PROJ_API_PATH)
	public Project addProject(@RequestBody Project proj) {
		return ps.addProject(proj);
	}
	
	@PostMapping(PROJ_API_PATH+"/list")
	public ResponseEntity<NewProjectResponseDTO> addProjectList(@RequestBody NewProjectDTO proj) {
		ProjectList pl = new ProjectList(proj.getProjectName());
		return ps.addProjectList(pl);
	}
	
//	@PostMapping(DEP_API_PATH + "/list")
//	public ResponseEntity<NewDepartmentResponseDTO> addDepartmentList(@RequestBody NewDepartmentDTO depL) {
//		DepartmentList dl = new DepartmentList(depL.getRole());
//		return ds.addDepartmentList(dl);
//	}

	@PutMapping(PROJ_API_PATH + "/{id}")
	public Project updateProject(@PathVariable("id") Long id, @RequestBody Project d) {
		return ps.updateProject(id, d);
	}

	@DeleteMapping(PROJ_API_PATH + "/{id}/{empid}")
	public String deleteProject(@PathVariable("id") Long id, @PathVariable("empid") Long emp_id) {
		System.out.println("Controller Delete");
		return ps.deleteProject(id, emp_id);
	}
	
	@PutMapping(PROJ_API_PATH + "/list/{id}")
	public ProjectList updateProjectList(@PathVariable("id") Long id, @RequestBody ProjectList pl) {
		return ps.updateProjectList(id, pl);
	}
	
	@DeleteMapping(PROJ_API_PATH + "/list/{id}")
	public String deleteProjectList(@PathVariable("id") Long id) {
		System.out.println("Controller Delete");
		return ps.deleteProjectList(id);
	}
}
