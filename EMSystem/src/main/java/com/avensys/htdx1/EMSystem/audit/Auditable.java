package com.avensys.htdx1.EMSystem.audit;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {
	@CreatedBy
	protected U createdBy;
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdOn;
	@LastModifiedBy
	protected U lastModifiedBy;
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastModifiedOn;
//
//	@PrePersist
//	public void setCreationDateToGmt8() {
//		creationDate = Date.from(Instant.now().atZone(ZoneId.of("Asia/Singapore")).toInstant());
//
//	}
//
//	@PreUpdate
//	public void setLastModifiedDateToGmt8() {
//		lastModifiedDate = Date.from(Instant.now().atZone(ZoneId.of("Asia/Singapore")).toInstant());
//
//	}
	public U getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(U createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public U getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(U lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}


// Getters and Setters
}