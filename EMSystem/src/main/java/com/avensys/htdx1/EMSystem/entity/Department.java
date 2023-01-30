package com.avensys.htdx1.EMSystem.entity;

import com.avensys.htdx1.EMSystem.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "department")
public class Department extends Auditable<String> {

	@EmbeddedId
	private DepartmentId departmentId;

	private String role;

	@ManyToOne
//	@JsonManagedReference
//	@JsonIgnore
	@JsonBackReference
	@MapsId("employee_id") // departmentId's employee_id
	@JoinColumn(name = "employee_id") // foreign key, cover over employee_id column
	private Employee employee;

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

//	public DepartmentId getDepartmentId() {
//		return departmentId;
//	}
//
//	public void setDepartmentId(DepartmentId departmentId) {
//		this.departmentId = departmentId;
//	}
//
//	public String getRole() {
//		return role;
//	}
//
//	public void setRole(String role) {
//		this.role = role;
//	}
//
//	public Employee getEmployee() {
//		return employee;
//	}
//
//	public void setEmployee(Employee employee) {
//		this.employee = employee;
//	}
//
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

	public Department() {

	}
}