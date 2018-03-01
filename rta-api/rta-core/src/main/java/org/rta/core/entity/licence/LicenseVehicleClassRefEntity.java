package org.rta.core.entity.licence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseCommonEntity;

@Entity
@Table(name = "license_vehicle_class_ref")
public class LicenseVehicleClassRefEntity extends BaseCommonEntity implements Serializable {

	private static final long serialVersionUID = 8387838928367969044L;
	
	@Id
	@Column(name = "vehicle_class", length = 50)
    private String vehicleClassCode;
	
	@Column(name = "licence_class_type")
	private String licenceClassType;

	@Column(name = "age_group_cd")
	private String ageGroupCode;
	
	@Column(name = "idp_class", length = 50)
	private String idpClass;
	
	@Column(name = "requires_doctor_cert")
	private Boolean requiresDoctorCert;
	
	@Column(name = "badge_available")
	private Boolean badgeAvailable;
	
	@Column(name = "hazardous")
	private Boolean hazardous;
	
	@Column(name = "validity_period")
	private Integer validityPeriod;
	
	@Column(name = "max_age")
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

