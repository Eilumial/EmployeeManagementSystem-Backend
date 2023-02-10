package com.avensys.htdx1.EMSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.avensys.htdx1.EMSystem.dto.PWChangeDTO;
import com.avensys.htdx1.EMSystem.entity.UserEntity;
import com.avensys.htdx1.EMSystem.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired
	private UserService us;

	final static String USER_API_PATH = "/ems/user";

	@GetMapping(USER_API_PATH)
	public List<UserEntity> getAllUser() {
		return us.getAllUser();
	}

	@GetMapping(USER_API_PATH + "/{id}")
	public UserEntity getUserById(@PathVariable Long id) {
		return us.getById(id);
	}
	
	
//	@GetMapping("/ems/user/d/{id}")
//	public List<Employee> getEmployeeByDepartmentID(@PathVariable("id") Long id){
//		return es.getEmployeeByDepartmentID(id);
//	}
//	
//	@GetMapping("/ems/emp/p/{id}")
//	public List<Employee> getEmployeeByProjectID(@PathVariable("id") Long id){
//		return es.getEmployeeByProjectID(id);
//	}

	
	/*
	 * DUPLICATE OF REGISTER IN AuthController
	 */
//	@PostMapping(USER_API_PATH)
//	public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity u) {
////		System.out.println(u);
//		UserEntity user = us.add(u);
//
//		if (user.getErrorMessage() != null) {
//			return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
//		} else {
//			return new ResponseEntity<>(user, HttpStatus.OK);
//		}
//	}

	@PutMapping(USER_API_PATH + "/{id}")
	public ResponseEntity<UserEntity> updateUser(@PathVariable("id") Long id, @RequestBody UserEntity u) {

		UserEntity user = us.updateUserData(id, u);

		if (user.getErrorMessage() != null) {
			return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}

	}

	@DeleteMapping(USER_API_PATH + "/{id}")
	public boolean deleteUser(@PathVariable("id") Long id) {
		return us.deleteUser(id);
	}
	
	@PostMapping(value= {USER_API_PATH+"/pw","/ems/u"+"/pw"})
	public String updatePassword(@RequestBody PWChangeDTO logUser) {
		return us.updatePassword(logUser);
		
	}
	
	@PutMapping(USER_API_PATH+"/authadmin/{id}")
	public String authAdmin(@PathVariable("id") Long id) {
		return us.authAdmin(id);
	}
	
	
//	@PostMapping(value="/ems/u"+"/pw")
//	public String updatePasswordForUser(@RequestBody PWChangeDTO logUser) {
//		return us.updatePassword(logUser);
//		
//	}
}
