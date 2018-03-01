package org.rta.core.entity.vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name = "stoppage_application")
public class StoppageApplicationEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8797946293065788968L;

	@Id
	@NotNull
	@Column(name = "stoppage_app_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stoppage_application_seq")
	@SequenceGenerator(name = "stoppage_application_seq", sequenceName = "stoppage_application_seq", allocationSize = 1)
	private Long stoppageAppId;

	@Column(name = "stoppage_no", length = 50)
	private String stoppageNo;
	
	@Column(name = "inspected_by", length = 50)
	private String inspectedBy;

	@Column(name = "inspected_dt")
	private Long inspectedDt;
	 
	@Column(name = "regn_no", length = 50)
	private String regnNo;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRcId;
	
	@Column(name = "rc_validity")
	private Long rcValidity;
	
	@Column(name = "fc_validity")
	private Long fcValidity;
	
	@Column(name = "permit_validity")
	private Long permitValidity;
	
	@Column(name = "tax_validity")
	private Long taxValidity;
	
	@Column(name = "tax_amt")
	private double taxAmt;
	
	@Column(name = "stoppage_on")
	private Long stoppageOn;
	
	@Column(name = "stoppage_reason", length = 255)
	private String stoppageReason;	

	@Column(name = "status")
	private Integer status;
	
	@Column(name = "stoppage_area", length = 255)
	private String stoppageArea;
	
	
	@Column(name = "stoppage_addr1", length = 255)
	private String stoppageAddr1;
	
	@Column(name = "stoppage_addr2", length = 255)
	private String stoppageAddr2;
	
	@Column(name = "stoppage_addr3", length = 255)
	private String stoppageAddr3;
	
	@Column(name = "city", length = 255)
	private String city;
	
	@Column(name = "district_name", length = 255)
	private String districtName;
	
	@Column(name = "pincode", length = 6)
	private Long pincode;
	
	@Column(name = "state_name", length = 255)
	private String stateName;
	
	@Column(name = "approved", length = 1)
	private Character approved;
	
	@Column(name = "approved_by", length = 50)
	private String approvedBy;
	
	@Column(name = "approved_on")
	private Long approvedOn;
	
	@Column(name = "valid_flag", length = 1)
	private Character validFlag;
	
	@Column(name = "assigned_mvi", length = 50)
	private String assignedMvi;
	
	@Column(name = "mvi_remarks", length = 255)
	private String mviRemarks;
	
	@Column(name = "office_code", length = 50)
	private String officeCode;
	
	@Column(name = "vehicle_cov", length = 50)
	private String vehicleCov;
	
	@Column(name = "remarks", length = 255)
	private String remarks;
	
	@Column(name = "pms_remarks", length = 255)
	private String pmsRemarks;
	
	@Column(name = "tax_exem_months")
	private Integer taxExemMonths;

	public Long getStoppageAppId() {
		return stoppageAppId;
	}

	public void setStoppageAppId(Long stoppageAppId) {
		this.stoppageAppId = stoppageAppId;
	}

	public String getStoppageNo() {
		return stoppageNo;
	}

	public void setStoppageNo(String stoppageNo) {
		this.stoppageNo = stoppageNo;
	}

	public String getInspectedBy() {
		return inspectedBy;
	}

	public void setInspectedBy(String inspectedBy) {
		this.inspectedBy = inspectedBy;
	}

	public Long getInspectedDt() {
		return inspectedDt;
	}

	public void setInspectedDt(Long inspectedDt) {
		this.inspectedDt = inspectedDt;
	}

	public String getRegnNo() {
		return regnNo;
	}

	public void setRegnNo(String regnNo) {
		this.regnNo = regnNo;
	}

	public VehicleRCEntity getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public Long getRcValidity() {
		return rcValidity;
	}

	public void setRcValidity(Long rcValidity) {
		this.rcValidity = rcValidity;
	}

	public Long getFcValidity() {
		return fcValidity;
	}

	public void setFcValidity(Long fcValidity) {
		this.fcValidity = fcValidity;
	}

	public Long getPermitValidity() {
		return permitValidity;
	}

	public void setPermitValidity(Long permitValidity) {
		this.permitValidity = permitValidity;
	}

	public Long getTaxValidity() {
		return taxValidity;
	}

	public void setTaxValidity(Long taxValidity) {
		this.taxValidity = taxValidity;
	}

	public double getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}

	public Long getStoppageOn() {
		return stoppageOn;
	}

	public void setStoppageOn(Long stoppageOn) {
		this.stoppageOn = stoppageOn;
	}

	public String getStoppageReason() {
		return stoppageReason;
	}

	public void setStoppageReason(String stoppageReason) {
		this.stoppageReason = stoppageReason;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStoppageArea() {
		return stoppageArea;
	}

	public void setStoppageArea(String stoppageArea) {
		this.stoppageArea = stoppageArea;
	}

	public String getStoppageAddr1() {
		return stoppageAddr1;
	}

	public void setStoppageAddr1(String stoppageAddr1) {
		this.stoppageAddr1 = stoppageAddr1;
	}

	public String getStoppageAddr2() {
		return stoppageAddr2;
	}

	public void setStoppageAddr2(String stoppageAddr2) {
		this.stoppageAddr2 = stoppageAddr2;
	}

	public String getStoppageAddr3() {
		return stoppageAddr3;
	}

	public void setStoppageAddr3(String stoppageAddr3) {
		this.stoppageAddr3 = stoppageAddr3;
	}

	public Character getApproved() {
		return approved;
	}

	public void setApproved(Character approved) {
		this.approved = approved;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Long getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Long approvedOn) {
		this.approvedOn = approvedOn;
	}

	public Character getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Character validFlag) {
		this.validFlag = validFlag;
	}

	public String getAssignedMvi() {
		return assignedMvi;
	}

	public void setAssignedMvi(String assignedMvi) {
		this.assignedMvi = assignedMvi;
	}

	public String getMviRemarks() {
		return mviRemarks;
	}

	public void setMviRemarks(String mviRemarks) {
		this.mviRemarks = mviRemarks;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getVehicleCov() {
		return vehicleCov;
	}

	public void setVehicleCov(String vehicleCov) {
		this.vehicleCov = vehicleCov;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPmsRemarks() {
		return pmsRemarks;
	}

	public void setPmsRemarks(String pmsRemarks) {
		this.pmsRemarks = pmsRemarks;
	}

	public Integer getTaxExemMonths() {
		return taxExemMonths;
	}

	public void setTaxExemMonths(Integer taxExemMonths) {
		this.taxExemMonths = taxExemMonths;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}
