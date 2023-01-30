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
@Table(name = "project")
public class Project extends Auditable<String> {

	@EmbeddedId
	private ProjectId projectId;

	private String project_name;

	@ManyToOne
//  @JsonBackReference
//	@JsonIgnore
	@JsonBackReference
	@MapsId("employee_id") // departmentId's employee_id
	@JoinColumn(name = "employee_id") // foreign key, cover over employee_id column
	private Employee employee;

//    @CreatedBy
//    private String createdBy;
//
//    @CreatedDate
//    private LocalDateTime createdOn;
//    
//    @LastModifiedBy
//    private String lastModifiedBy;
//
//    @LastModifiedDate
//    private LocalDateTime lastModifiedOn;

//	public ProjectId getProjectId() {
//		return projectId;
//	}
//
//	public void setProjectId(ProjectId projectId) {
//		this.projectId = projectId;
//	}
//
//	public String getProject_name() {
//		return project_name;
//	}
//
//	public void setProject_name(String project_name) {
//		this.project_name = project_name;
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

	public Project() {

	}

}
