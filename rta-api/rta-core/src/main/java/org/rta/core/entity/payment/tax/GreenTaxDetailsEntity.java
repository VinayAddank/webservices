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
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name = "green_tax_details")
public class GreenTaxDetailsEntity extends BaseEntity {
	
	private static final long serialVersionUID = 8952423378863237663L;

	@Id
	@NotNull
	@Column(name = "green_tax_dtl_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "green_tax_seq")
	@SequenceGenerator(name = "green_tax_seq", sequenceName = "green_tax_seq", allocationSize = 1)
	private Long greenTaxDtlId;
	
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRc;
	
	@NotNull
	@Column(name = "green_tax_amt")
	private double greenTaxAmt;
	
	@Column(name = "green_tax_valid_to")
	private Long greenTaxValidTo;
	
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

	public Long getGreenTaxDtlId() {
		return greenTaxDtlId;
	}

	public void setGreenTaxDtlId(Long greenTaxDtlId) {
		this.greenTaxDtlId = greenTaxDtlId;
	}

	public VehicleRCEntity getVehicleRc() {
		return vehicleRc;
	}

	public void setVehicleRc(VehicleRCEntity vehicleRc) {
		this.vehicleRc = vehicleRc;
	}

	public double getGreenTaxAmt() {
		return greenTaxAmt;
	}

	public void setGreenTaxAmt(double greenTaxAmt) {
		this.greenTaxAmt = greenTaxAmt;
	}

	public Long getGreenTaxValidTo() {
		return greenTaxValidTo;
	}

	public void setGreenTaxValidTo(Long greenTaxValidTo) {
		this.greenTaxValidTo = greenTaxValidTo;
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
	
	
	
	}
