package org.rta.core.entity.licence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseCommonEntity;


/**
 * The persistent class for the status_reference database table.
 * 
 */
@Entity
@Table(name="status_reference")
public class StatusReferenceEntity extends BaseCommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="status_code")
	private String statusCode;

	@Column(name="is_active")
	private String isActive;

	private String status;

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}