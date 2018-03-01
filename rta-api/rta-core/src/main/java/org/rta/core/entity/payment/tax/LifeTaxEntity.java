package org.rta.core.entity.payment.tax;

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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.master.TaxMasterEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name = "life_tax_details")
public class LifeTaxEntity extends BaseEntity {
	
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Id
@NotNull
@Column(name = "life_tax_dtl_id")
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "life_tax_details_seq")
@SequenceGenerator(name = "life_tax_details_seq", sequenceName = "life_tax_details_seq", allocationSize = 1)
private Long lifeTaxDtlId;

@NotNull
@Column(name = "tax_amt")
private double taxAmt;

@Column(name = "tax_percent")
private double taxPercent;

@NotNull
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "vehicle_rc_id")
private VehicleRCEntity vehicleRc;

@Column(name = "tax_valid_upto")
private Long taxValidUpto;

@Column(name = "tax_valid_from")
private Long taxValidFrom;

@Column(name = "second_vehicle_tax_percent")
private double secondVehicleTaxPercent;

@Column(name = "second_vehicle_tax_amt")
private double secondVehicleTaxAmt;

@Column(name = "second_vehicle_tax_on")
private Long secondVehicleTaxOn;

@NotNull
@Column(name = "total_tax")
private double totalTax;

@Column(name = "transaction_id")
private Long transactionId;

@Column(name = "reg_type")
private Integer regType;

@Column(name = "status")
private Integer status;

@Column(name = "exemption_flag")
private Boolean exemptionFlag;

@Column(name = "remarks", length = 255)
private String remarks;

@Column(name = "rta_office_code", length = 50)
private String rtaOfficeCode;

@Column(name = "vehicle_cov", length = 50)
private String vehicleCov;

@Version
@Column(name = "version", length = 50, columnDefinition = "Integer DEFAULT 0")
private Integer version;

public Long getLifeTaxDtlId() {
	return lifeTaxDtlId;
}

public void setLifeTaxDtlId(Long lifeTaxDtlId) {
	this.lifeTaxDtlId = lifeTaxDtlId;
}

public double getTaxAmt() {
	return taxAmt;
}

public void setTaxAmt(double taxAmt) {
	this.taxAmt = taxAmt;
}

public VehicleRCEntity getVehicleRc() {
	return vehicleRc;
}

public void setVehicleRc(VehicleRCEntity vehicleRc) {
	this.vehicleRc = vehicleRc;
}

public Long getTaxValidUpto() {
	return taxValidUpto;
}

public void setTaxValidUpto(Long taxValidUpto) {
	this.taxValidUpto = taxValidUpto;
}

public Long getTaxValidFrom() {
	return taxValidFrom;
}

public void setTaxValidFrom(Long taxValidFrom) {
	this.taxValidFrom = taxValidFrom;
}

public double getSecondVehicleTaxPercent() {
	return secondVehicleTaxPercent;
}

public void setSecondVehicleTaxPercent(double secondVehicleTaxPercent) {
	this.secondVehicleTaxPercent = secondVehicleTaxPercent;
}

public double getSecondVehicleTaxAmt() {
	return secondVehicleTaxAmt;
}

public void setSecondVehicleTaxAmt(double secondVehicleTaxAmt) {
	this.secondVehicleTaxAmt = secondVehicleTaxAmt;
}

public Long getSecondVehicleTaxOn() {
	return secondVehicleTaxOn;
}

public void setSecondVehicleTaxOn(Long secondVehicleTaxOn) {
	this.secondVehicleTaxOn = secondVehicleTaxOn;
}

public double getTotalTax() {
	return totalTax;
}

public void setTotalTax(double totalTax) {
	this.totalTax = totalTax;
}

public Long getTransactionId() {
	return transactionId;
}

public void setTransactionId(Long transactionId) {
	this.transactionId = transactionId;
}

public Integer getRegType() {
	return regType;
}

public void setRegType(Integer regType) {
	this.regType = regType;
}

public Integer getStatus() {
	return status;
}

public void setStatus(Integer status) {
	this.status = status;
}

public Boolean getExemptionFlag() {
	return exemptionFlag;
}

public void setExemptionFlag(Boolean exemptionFlag) {
	this.exemptionFlag = exemptionFlag;
}

public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}

public String getRtaOfficeCode() {
	return rtaOfficeCode;
}

public void setRtaOfficeCode(String rtaOfficeCode) {
	this.rtaOfficeCode = rtaOfficeCode;
}

public String getVehicleCov() {
	return vehicleCov;
}

public void setVehicleCov(String vehicleCov) {
	this.vehicleCov = vehicleCov;
}

public Integer getVersion() {
	return version;
}

public void setVersion(Integer version) {
	this.version = version;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}

public double getTaxPercent() {
	return taxPercent;
}

public void setTaxPercent(double taxPercent) {
	this.taxPercent = taxPercent;
}

}
