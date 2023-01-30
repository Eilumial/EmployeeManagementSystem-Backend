package com.avensys.htdx1.EMSystem.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class DepartmentId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	private Long employee_id;

//	public Long getEmployee_id() {
//		return employee_id;
//	}
//
//	public void setEmployee_id(Long employee_id) {
//		this.employee_id = employee_id;
//	}

	public DepartmentId() {

	}
}