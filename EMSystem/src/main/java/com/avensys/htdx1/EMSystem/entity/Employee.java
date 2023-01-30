package com.avensys.htdx1.EMSystem.entity;

import java.util.List;

import com.avensys.htdx1.EMSystem.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class Employee extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	private String first_name;

	private String last_name;

	private Integer age;

	private String gender;

	private Double salary;

	private String email;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	// @JsonBackReference(value="EmployeeDepartments")
//	@JsonIgnore
	@JsonManagedReference
	private List<Department> departments;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	// @JsonBackReference(value="EmployeeProjects")
//	@JsonIgnore
	@JsonManagedReference
	private List<Project> projects;

	@Transient
    private String errorMessage;

//	@CreatedBy
//	private String createdBy;
//
//	@CreatedDate
//	private LocalDateTime createdOn;
//
//	@LastModifiedBy
//	private String lastModifiedBy;
//
//	@LastModifiedDate
//	private LocalDateTime lastModifiedOn;
//	// getters and setters

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public void setFirst_name(String first_name) {
//		this.first_name = first_name;
//	}
//
//	public String getFirst_name() {
//		return first_name;
//	}
//
//	public String getLast_name() {
//		return last_name;
//	}
//
//	public void setLast_name(String last_name) {
//		this.last_name = last_name;
//	}
//
//	public Integer getAge() {
//		return age;
//	}
//
//	public void setAge(Integer age) {
//		this.age = age;
//	}
//
//	public String getGender() {
//		return gender;
//	}
//
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//
//	public Double getSalary() {
//		return salary;
//	}
//
//	public void setSalary(Double salary) {
//		this.salary = salary;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//	public List<Department> getDepartments() {
//		return departments;
//	}
//
//	public void setDepartments(List<Department> departments) {
//		this.departments = departments;
//	}
//
//	public List<Project> getProjects() {
//		return projects;
//	}
//
//	public void setProjects(List<Project> projects) {
//		this.projects = projects;
//	}

//	public String getCreatedBy() {
//		return createdBy;
//	}
//
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//
//	public LocalDateTime getCreatedOn() {
//		return createdOn;
//	}
//
//	public void setCreatedOn(LocalDateTime createdOn) {
//		this.createdOn = createdOn;
//	}
//
//	public String getLastModifiedBy() {
//		return lastModifiedBy;
//	}
//
//	public void setLastModifiedBy(String lastModifiedBy) {
//		this.lastModifiedBy = lastModifiedBy;
//	}
//
//	public LocalDateTime getLastModifiedOn() {
//		return lastModifiedOn;
//	}
//
//	public void setLastModifiedOn(LocalDateTime lastModifiedOn) {
//		this.lastModifiedOn = lastModifiedOn;
//	}

	public Employee() {

	}

	public Employee(String first_name, String last_name, Integer age, String gender, Double salary, String email,
			List<Department> departments, List<Project> projects) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.age = age;
		this.gender = gender;
		this.salary = salary;
		this.email = email;
		this.departments = departments;
		this.projects = projects;
	}

	public Employee(String first_name, String last_name, Integer age, String gender, Double salary, String email) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.age = age;
		this.gender = gender;
		this.salary = salary;
		this.email = email;
	}

	public Employee(String first_name, String last_name, Integer age, String gender, Double salary, String email,
			UserEntity userEntity, List<Department> departments, List<Project> projects) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.age = age;
		this.gender = gender;
		this.salary = salary;
		this.email = email;
		this.userEntity = userEntity;
		this.departments = departments;
		this.projects = projects;
	}

	public Employee(Long id, String first_name, String last_name, Integer age, String gender, Double salary,
			String email, UserEntity userEntity, List<Department> departments, List<Project> projects) {
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.age = age;
		this.gender = gender;
		this.salary = salary;
		this.email = email;
		this.userEntity = userEntity;
		this.departments = departments;
		this.projects = projects;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Employee(String first_name, String last_name, Integer age, String gender, String email) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.age = age;
		this.gender = gender;
		this.email = email;
	}

	public Employee(String first_name, String last_name, Integer age, String gender, String email,
			List<Department> departments, List<Project> projects) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.age = age;
		this.gender = gender;
		this.email = email;
		this.departments = departments;
		this.projects = projects;
	}
}