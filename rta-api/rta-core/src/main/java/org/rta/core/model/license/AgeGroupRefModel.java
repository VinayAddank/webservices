package org.rta.core.model.license;

public class AgeGroupRefModel {
	
	private Integer ageGroupCd;
	private Integer ageEnd;
	private String ageGroupDesc;
	private Integer ageStart;
	private String isActive;
	
	public Integer getAgeGroupCd() {
		return ageGroupCd;
	}
	public void setAgeGroupCd(Integer ageGroupCd) {
		this.ageGroupCd = ageGroupCd;
	}
	public Integer getAgeEnd() {
		return ageEnd;
	}
	public void setAgeEnd(Integer ageEnd) {
		this.ageEnd = ageEnd;
	}
	public String getAgeGroupDesc() {
		return ageGroupDesc;
	}
	public void setAgeGroupDesc(String ageGroupDesc) {
		this.ageGroupDesc = ageGroupDesc;
	}
	public Integer getAgeStart() {
		return ageStart;
	}
	public void setAgeStart(Integer ageStart) {
		this.ageStart = ageStart;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}
