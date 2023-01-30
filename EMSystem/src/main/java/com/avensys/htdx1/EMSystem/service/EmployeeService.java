package com.avensys.htdx1.EMSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.avensys.htdx1.EMSystem.entity.Department;
import com.avensys.htdx1.EMSystem.entity.Employee;
import com.avensys.htdx1.EMSystem.entity.Project;
import com.avensys.htdx1.EMSystem.repo.EmployeeRepo;
import com.avensys.htdx1.EMSystem.repo.UserRepo;

@Service
@Component
public class EmployeeService {

	@Autowired
	private EmployeeRepo er;

	@Autowired
	private UserRepo ur;

//	public List<Employee> getEmployeeById(Long id){
//		List<Employee> empList = (List<Employee>) er.findById(id.intValue());
//		return empList;
//	}

	public List<Employee> getAllEmployee() {
		List<Employee> empList = (List<Employee>) er.findAll();
		return empList;
	}

	public List<Employee> getEmployeeByDepartmentID(Long id) {
		List<Employee> empList = er.findByDepartmentsDepartmentIdId(id);
		return empList;
	}

	public List<Employee> getEmployeeByEmail(String email) {
		System.out.println(er.existsByEmail(email));
		if (er.existsByEmail(email)) {
//			System.out.println(er.findByEmail(email));
			return er.findByEmail(email);
		}else {
			Employee e = new Employee();
			List<Employee> eList = new ArrayList<Employee>();
			e.setErrorMessage("No records found for this email");
			eList.add(e);
			return eList;
		}
	}

	public List<Employee> getEmployeeByProjectID(Long id) {
		System.out.println("Proj ID: "+id);
		List<Employee> empList = er.findByProjectsProjectIdId(id);
//		System.out.println("empList "+empList.get(0).toString());
		return empList;
	}

	public Employee getEmployeeByID(Long id) {
		Employee emp = er.findById(id.intValue());
		return emp;
	}

	public Employee add(Long id, Employee e) {
		// System.out.println(er.existsByUserEntityId(id));
		if (er.existsByUserEntityId(id)) {
			Employee emp = new Employee();
			emp.setErrorMessage("Employee record exists for user");
			return emp;
		}

		if (er.existsByEmail(e.getEmail())) {
			Employee emp = new Employee();
			emp.setErrorMessage("Duplicate Email");
			return emp;
		}

		Employee e1 = new Employee(e.getFirst_name(), e.getLast_name(), e.getAge(), e.getGender(), e.getSalary(),
				e.getEmail(), e.getDepartments(), e.getProjects());

		if (e1.getProjects() == null) {
			// search and set Projects if exist

			// TEMP! REMOVE!
			List<Project> projList = new ArrayList<Project>();
			e1.setProjects(projList);
		}

		if (e1.getDepartments() == null) {
			// search and set Departments if exist

			// TEMP! REMOVE!
			List<Department> depList = new ArrayList<Department>();
			e1.setDepartments(depList);
		}

		if (!er.existsByUserEntityId(id)) {
			if (e1.getUserEntity() == null) {
				e1.setUserEntity(ur.findById(id.intValue()));
			}
			return er.save(e1);
		} else {
			return new Employee();
		}

	}

	public Employee updateEmpData(Long id, Employee e) {
		// er.findByEmail(e.getEmail()).getId();
		List<Long> idList = er.findAllIdByEmail(e.getEmail());
		if (idList.size() > 0) {
			for (Long i : idList) {

				if (!i.equals(id)) {
//					System.out.println("loop list id "+i);
//					System.out.println("loop id "+id);
					Employee emp = new Employee();
					emp.setErrorMessage("Email already exists in system");
					return emp;
				}
			}
		}

		// id = employee_id
		if (er.existsById(id)) {
			Employee emp = er.findById(id.intValue());

			emp.setFirst_name(e.getFirst_name());
			emp.setLast_name(e.getLast_name());
			emp.setAge(e.getAge());
			emp.setGender(e.getGender());
			emp.setSalary(e.getSalary());
			emp.setEmail(e.getEmail());
			return er.save(emp);
		} else {
			return er.save(e);
		}

		// Long email_id = er.findIdByEmail(e.getEmail());
//		System.out.println("e.getemail: " + e.getEmail());
//		System.out.println(er.findByEmail(e.getEmail()).getId());
//		List<Employee> empList = er.findByEmail(e.getEmail());
//		if (empList.size() > 0) {
//			for(Employee e1: empList) {
//				System.out.println(e1);
//			}
//		}
//		Long email_id = 0L;
//		List<Long> idList = er.findIdByEmail(e.getEmail());
//		
//		if(idList.size()>0) {
//			email_id = idList.get(0);
//		}

//		if (email_id != id) {
//			Employee emp = new Employee();
//			emp.setErrorMessage("Email already exists in system");
//			return emp;
//		}

//		String first_name, String last_name, Integer age, String gender, Double salary, String email,
//		List<Department> departments, List<Project> projects

//		Employee e1 = new Employee(e.getId(), e.getFirst_name(), e.getLast_name(), e.getAge(), e.getGender(), e.getSalary(),
//				e.getEmail(), e.getUser(), e.getDepartments(), e.getProjects());
//		
//		if (e1.getProjects() == null) {
//			// search and set Projects if exist
//			e1.setProjects(er.findByUserId(id).getProjects());
//			
//			
//			// TEMP! REMOVE!
////			List<Project> projList = new ArrayList<Project>();
////			e1.setProjects(projList);
//		}
//
//		if (e1.getDepartments() == null) {
//			// search and set Departments if exist
//			e1.setDepartments(er.findByUserId(id).getDepartments());
//			
//			//TEMP! REMOVE!
////			List<Department> depList = new ArrayList<Department>();
////			e1.setDepartments(depList);
//		}
//
//		if(e1.getUser()==null) {
//			User u = er.findByUserId(id).getUser();
//			e1.setUser(u);
//		}else {
//			
//		}
//		
//		
//		if(ur.existsById(id)) {
//			
//		}
//		e1.setUser(ur.findById(id.intValue()));
//
//		er.save(e1);
	}

	public void deleteEmp(Long id) {
		er.deleteById(id);
	}

}
