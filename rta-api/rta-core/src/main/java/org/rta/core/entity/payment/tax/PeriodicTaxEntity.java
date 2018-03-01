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
import org.rta.core.entity.master.TaxTypeEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name = "periodic_tax_details")
public class PeriodicTaxEntity extends BaseEntity{

	private static final long serialVersionUID = -7161458982686954098L;

	@Id
	@NotNull
	@Column(name = "tax_dtl_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "periodic_tax_details_seq")
	@SequenceGenerator(name = "periodic_tax_details_seq", sequenceName = "periodic_tax_details_seq", allocationSize = 1)
	private Long taxDtlId;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tax_type_id")
	private TaxTypeEntity taxTypeId;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRc;

	@Column(name = "tax_valid_upto")
	private Long taxValidUpto;

	@Column(name = "tax_valid_from")
	private Long taxValidFrom;

	@NotNull
	@Column(name = "tax_amt")
	private double taxAmt;	

	@Column(name = "tax_arrears")
	private double taxArrears;

	@Column(name = "penalty")
	private double penalty;

	@Column(name = "penalty_arrears")
	private double penaltyArrears;

	@Column(name = "service_charge")
	private double serviceCharge;

	@Column(name = "vehicle_cov", length = 50)
	private String vehicleCov;

	@Column(name = "permit_type", length = 50)
	private String permitType;

	@Column(name = "permit_sub_type", length = 50)
	private String permitSubType;

	@NotNull
	@Column(name = "total_amt")
	private double totalAmt;

	@Column(name = "transaction_id")
	private Long transactionId;

	@Column(name = "status")
	private Integer status;

	@Column(name = "reg_type")
	private Integer regType;

	@Column(name = "exemption_flag")
	private Boolean exemptionFlag;

	@Column(name = "remarks", length = 255)
	private String remarks;

	@Column(name = "rta_office_code", length = 50)
	private String rtaOfficeCode;

	@Column(name = "vehicle_reg_no", length = 50)
	private String vehicleRegNo;

	@Version
	@Column(name = "version", length = 50, columnDefinition = "Integer DEFAULT 0")
	private Integer version;

	public Long getTaxDtlId() {
		return taxDtlId;
	}

	public void setTaxDtlId(Long taxDtlId) {
		this.taxDtlId = taxDtlId;
	}

	public TaxTypeEntity getTaxTypeId() {
		return taxTypeId;
	}

	public void setTaxTypeId(TaxTypeEntity taxTypeId) {
		this.taxTypeId = taxTypeId;
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

	public double getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}

	public double getTaxArrears() {
		return taxArrears;
	}

	public void setTaxArrears(double taxArrears) {
		this.taxArrears = taxArrears;
	}

	public double getPenalty() {
		return penalty;
	}

	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}

	public double getPenaltyArrears() {
		return penaltyArrears;
	}

	public void setPenaltyArrears(double penaltyArrears) {
		this.penaltyArrears = penaltyArrears;
	}

	public double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public String getVehicleCov() {
		return vehicleCov;
	}

	public void setVehicleCov(String vehicleCov) {
		this.vehicleCov = vehicleCov;
	}

	public String getPermitType() {
		return permitType;
	}

	public void setPermitType(String permitType) {
		this.permitType = permitType;
	}

	public String getPermitSubType() {
		return permitSubType;
	}

	public void setPermitSubType(String permitSubType) {
		this.permitSubType = permitSubType;
	}

	public double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
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

	public String getVehicleRegNo() {
		return vehicleRegNo;
	}

	public void setVehicleRegNo(String vehicleRegNo) {
		this.vehicleRegNo = vehicleRegNo;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
}
