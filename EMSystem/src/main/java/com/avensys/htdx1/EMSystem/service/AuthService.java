package com.avensys.htdx1.EMSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.avensys.htdx1.EMSystem.dto.RegUserDTO;
import com.avensys.htdx1.EMSystem.dto.RegUserResponseDTO;
import com.avensys.htdx1.EMSystem.entity.Department;
import com.avensys.htdx1.EMSystem.entity.Employee;
import com.avensys.htdx1.EMSystem.entity.Project;
import com.avensys.htdx1.EMSystem.entity.Role;
import com.avensys.htdx1.EMSystem.entity.UserEntity;
import com.avensys.htdx1.EMSystem.repo.EmployeeRepo;
import com.avensys.htdx1.EMSystem.repo.RoleRepo;
import com.avensys.htdx1.EMSystem.repo.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager am;
	@Autowired
	private UserRepo ur;
	@Autowired
	private RoleRepo rr;
	@Autowired
	private EmployeeRepo er;
	@Autowired
	private EmployeeService es;
	@Autowired
	private PasswordEncoder pe;

	@Transactional
	public ResponseEntity<RegUserResponseDTO> addUserEmployee(RegUserDTO regUser) {
		if (ur.existsByUsername(regUser.getUsername())) {
			return new ResponseEntity<>(
					new RegUserResponseDTO("failure", "Username already exists, please try another"),
					HttpStatus.BAD_REQUEST);
		}

		if (er.existsByEmail(regUser.getEmail())) {
			return new ResponseEntity<>(new RegUserResponseDTO("failure", "Duplicate email, please try another"),
					HttpStatus.BAD_REQUEST);
		}

		//String first_name, String last_name, Integer age, String gender, Double salary, String email
		Employee regEmp = new Employee(regUser.getFirst_name(), regUser.getLast_name(), regUser.getAge(),
				regUser.getGender(), 0.0, regUser.getEmail());

		UserEntity user = new UserEntity();
		user.setUsername(regUser.getUsername());
		user.setPassword(pe.encode(regUser.getPassword()));
		//user.setPassword(regUser.getPassword());

//		Role roles = rr.findByName("USER").get();
		Role user_role = rr.findByName("USER").get();
		Role admin_role = rr.findByName("ADMIN").get();
		List<Role> roles = new ArrayList<>();
		roles.add(user_role);

		// >>>>>ADMIN ROLE<<<<<<
//		roles.add(admin_role);
		user.setRoles(roles);
		System.out.println(user.toString());
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		if (regEmp.getProjects() == null) {
			// search and set Projects if exist
			List<Project> projList = new ArrayList<Project>();
			regEmp.setProjects(projList);
		}

		if (regEmp.getDepartments() == null) {
			// search and set Departments if exist
			List<Department> depList = new ArrayList<Department>();
			regEmp.setDepartments(depList);
		}

//		if (!er.existsByUserEntityId(id)) {
//			if (regEmp.getUserEntity() == null) {
//				regEmp.setUserEntity(ur.findById(id.intValue()));
//			}
//			return er.save(e1);
//		} else {
//			return new Employee();
//		}

//		user.setEmployee(regEmp);
		UserEntity us = ur.save(user);
		regEmp.setUserEntity(user);
		er.save(regEmp);

		return new ResponseEntity<>(
				new RegUserResponseDTO("success", "User #" + us.getId() + " register successful", us.getId()),
				HttpStatus.OK);
		// String first_name, String last_name, Integer age, String gender, Double
		// salary, String email

//		Employee savedEmp = es.add(us.getId(), regEmp);
//
//		if (savedEmp.getErrorMessage() != null) {
//			return new ResponseEntity<>(new RegUserResponseDTO("failure", savedEmp.getErrorMessage()),
//					HttpStatus.BAD_REQUEST);
//		}
//
//		return new ResponseEntity<>(
//				new RegUserResponseDTO("success", "User #" + us.getId() + " register successful", us.getId()),
//				HttpStatus.OK);

//		if (er.existsByUserEntityId(id)) {
//			Employee emp = new Employee();
//			emp.setErrorMessage("Employee record exists for user");
//			return emp;
//		}

	}

//	@Transactional
//	public ResponseEntity<RegUserResponseDTO> addUserEmployee(RegUserDTO regUser) {
//		if (ur.existsByUsername(regUser.getUsername())) {
//			return new ResponseEntity<>(
//					new RegUserResponseDTO("failure", "Username already exists, please try another"),
//					HttpStatus.BAD_REQUEST);
//		}
//
//		UserEntity user = new UserEntity();
//		user.setUsername(regUser.getUsername());
//		// user.setPassword(pe.encode(regUser.getPassword()));
//		user.setPassword(regUser.getPassword());
//
////		Role roles = rr.findByName("USER").get();
//		Role user_role = rr.findByName("USER").get();
//		Role admin_role = rr.findByName("ADMIN").get();
//		List<Role> roles = new ArrayList<>();
//		roles.add(user_role);
//
//		// >>>>>ADMIN ROLE<<<<<<
//		roles.add(admin_role);
//		user.setRoles(roles);
//		System.out.println(user.toString());
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		UserEntity us = ur.save(user);
//		// String first_name, String last_name, Integer age, String gender, Double
//		// salary, String email
//		Employee regEmp = new Employee(regUser.getFirst_name(), regUser.getLast_name(), regUser.getAge(),
//				regUser.getGender(), regUser.getEmail());
//
//		Employee savedEmp = es.add(us.getId(), regEmp);
//
//		if (savedEmp.getErrorMessage() != null) {
//			return new ResponseEntity<>(new RegUserResponseDTO("failure", savedEmp.getErrorMessage()),
//					HttpStatus.BAD_REQUEST);
//		}
//
//		return new ResponseEntity<>(
//				new RegUserResponseDTO("success", "User #" + us.getId() + " register successful", us.getId()),
//				HttpStatus.OK);
//	}

}
