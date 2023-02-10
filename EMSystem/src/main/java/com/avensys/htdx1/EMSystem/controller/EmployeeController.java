package com.avensys.htdx1.EMSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.avensys.htdx1.EMSystem.entity.Employee;
import com.avensys.htdx1.EMSystem.service.EmployeeService;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeController {
	@Autowired
	private EmployeeService es;

	final static String EMP_API_PATH = "/ems/emp";

	@GetMapping(EMP_API_PATH)
	public List<Employee> getAllEmployee() {
		return es.getAllEmployee();
	}

	@GetMapping(value= {EMP_API_PATH + "/{id}","/ems/u/{id}"})
	public Employee getEmployeeByID(@PathVariable("id") Long id) {
		return es.getEmployeeByID(id);
	}
	//EMP_API_PATH + "un/{id}",
	@GetMapping(value= {"/ems/u/un/{un}"})
	public List<Employee> getEmployeeByUsername(@PathVariable("un") String username) {
		return es.getEmployeeByUsername(username);
	}
	
	@GetMapping(EMP_API_PATH + "/e/{email}")
	public List<Employee> getEmployeeByID(@PathVariable("email") String email) {
		System.out.println("Email: "+email);
		return es.getEmployeeByEmail(email);
	}

	@GetMapping(EMP_API_PATH + "/d/{id}")
	public List<Employee> getEmployeeByDepartmentID(@PathVariable("id") Long id) {
		return es.getEmployeeByDepartmentID(id);
	}

	@GetMapping(EMP_API_PATH + "/p/{id}")
	public List<Employee> getEmployeeByProjectID(@PathVariable("id") Long id) {
		return es.getEmployeeByProjectID(id);
	}

	@PutMapping(EMP_API_PATH + "/{id}")
	public ResponseEntity<Employee> addEmployee(@PathVariable("id") Long id, @RequestBody Employee e) {
		try {
			Employee emp = es.add(id, e);

			if (emp.getErrorMessage() != null) {
				return new ResponseEntity<>(emp, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(emp, HttpStatus.OK);
			}
		} catch (Exception ex) {
			System.out.println("Inside add emp catch");
			ex.printStackTrace();
			Employee emp = new Employee();
			emp.setErrorMessage("ERROR");
			return new ResponseEntity<>(emp, HttpStatus.BAD_REQUEST);
		}
	}

//	@PutMapping(EMP_API_PATH + "/{id}")
//	public Employee addEmployee(@PathVariable("id") Long id, @RequestBody Employee e) {
//		try {
//			return es.add(id, e);
//		} catch (Exception ex) {
//			System.out.println("Inside catch");
//			ex.printStackTrace();
//			return new Employee();
//		}
//	}

	@PutMapping(value= {EMP_API_PATH + "/update/{id}","/ems/u/{id}"})
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee e) {
		try {
			System.out.println("UPDATE EMP id "+id);
			System.out.println("UPDATE EMP e: "+e);
			// employee id
			Employee emp = es.updateEmpData(id, e);

			if (emp.getErrorMessage() != null) {
				return new ResponseEntity<>(emp, HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(emp, HttpStatus.OK);
			}
		} catch (Exception ex) {
			System.out.println("Inside update emp catch");
			ex.printStackTrace();
			Employee emp = new Employee();
			emp.setErrorMessage("ERROR");
			return new ResponseEntity<>(emp, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(EMP_API_PATH + "/{id}")
	public String deleteEmployee(@PathVariable("id") Long id) {
		es.deleteEmp(id);
		return "Employee Deleted";
	}

//	@GetMapping("/empApp")
//	public List<Employee> getAllEmployee() {
//		return es.getAllEmployee();
//	}
//	
//	@GetMapping("/empApp/{id}")
//	public List<Employee> getEmployeeByID(@PathVariable("id") int id) {
//		return es.getEmployeeById(id);
//	}
//
//	@PostMapping("/empApp")
//	public Employee addEmployee(@RequestBody Employee e) {
//		return es.add(e);
//	}
//
//	@PutMapping("/empApp/{id}")
//	public String updateEmployee(@PathVariable("id") int id, @RequestBody Employee e) {
//		es.updateEmpData(id, e);
//		return "Employee ID:" + id + " updated";
//	}
//
//	@DeleteMapping("/empApp/{id}")
//	public String deleteEmployee(@PathVariable("id") int id) {
//		es.deleteEmp(id);
//		return "Employee Deleted";
//	}
//	
//	@GetMapping("/empApp/o/{id}")
//	public List<Employee> getEmpOfficeByID(@PathVariable("id") int id) {
//		return es.getByOfficeId(id);
//	}
}
