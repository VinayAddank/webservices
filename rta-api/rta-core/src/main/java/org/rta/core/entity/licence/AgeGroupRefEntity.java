package org.rta.core.entity.licence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseCommonEntity;

/**
 * The persistent class for the age_group_ref database table.
 * 
 */
@Entity
@Table(name="age_group_ref")
public class AgeGroupRefEntity extends BaseCommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="age_group_cd")
	private Integer ageGroupCd;

	@Column(name="age_end")
	private Integer ageEnd;

	@Column(name="age_group_desc")
	private String ageGroupDesc;

	@Column(name="age_start")
	private Integer ageStart;

	@Column(name="is_active")
	private String isActive;

	public Integer getAgeGroupCd() {
		return this.ageGroupCd;
	}

	public void setAgeGroupCd(Integer ageGroupCd) {
		this.ageGroupCd = ageGroupCd;
	}

	public Integer getAgeEnd() {
		return this.ageEnd;
	}

	public void setAgeEnd(Integer ageEnd) {
		this.ageEnd = ageEnd;
	}

	public String getAgeGroupDesc() {
		return this.ageGroupDesc;
	}

	public void setAgeGroupDesc(String ageGroupDesc) {
		this.ageGroupDesc = ageGroupDesc;
	}

	public Integer getAgeStart() {
		return this.ageStart;
	}

	public void setAgeStart(Integer ageStart) {
		this.ageStart = ageStart;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
}