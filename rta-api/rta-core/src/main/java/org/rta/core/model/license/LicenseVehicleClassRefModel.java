package org.rta.core.model.license;

public class LicenseVehicleClassRefModel {
	
	private String vehicleClassCode;
	private String licenceClassType;
	private String ageGroupCode;
	private String idpClass;
	private Boolean requiresDoctorCert;
	private Boolean badgeAvailable;
	private Boolean hazardous;
	private Integer validityPeriod;
	private Integer maxAge;
	
	public String getVehicleClassCode() {
		return vehicleClassCode;
	}
	public void setVehicleClassCode(String vehicleClassCode) {
		this.vehicleClassCode = vehicleClassCode;
	}
	public String getLicenceClassType() {
		return licenceClassType;
	}
	public void setLicenceClassType(String licenceClassType) {
		this.licenceClassType = licenceClassType;
	}
	public String getAgeGroupCode() {
		return ageGroupCode;
	}
	public void setAgeGroupCode(String ageGroupCode) {
		this.ageGroupCode = ageGroupCode;
	}
	public String getIdpClass() {
		return idpClass;
	}
	public void setIdpClass(String idpClass) {
		this.idpClass = idpClass;
	}
	public Boolean getRequiresDoctorCert() {
		return requiresDoctorCert;
	}
	public void setRequiresDoctorCert(Boolean requiresDoctorCert) {
		this.requiresDoctorCert = requiresDoctorCert;
	}
	public Boolean getBadgeAvailable() {
		return badgeAvailable;
	}
	public void setBadgeAvailable(Boolean badgeAvailable) {
		this.badgeAvailable = badgeAvailable;
	}
	public Boolean getHazardous() {
		return hazardous;
	}
	public void setHazardous(Boolean hazardous) {
		this.hazardous = hazardous;
	}
	public Integer getValidityPeriod() {
		return validityPeriod;
	}
	public void setValidityPeriod(Integer validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
	public Integer getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}


}
