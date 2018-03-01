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
@Table(name = "vehicle_current_tax_details")
public class VehicleCurrentTaxEntity extends BaseEntity{

	private static final long serialVersionUID = -3753219744248061691L;

	@Id
	@NotNull
	@Column(name = "vehicle_tax_dtl_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_current_tax_details_seq")
	@SequenceGenerator(name = "vehicle_current_tax_details_seq", sequenceName = "vehicle_current_tax_details_seq", allocationSize = 1)
	private Long vehicleTaxDtlId;

	@NotNull
	@Column(name = "tax_type_id")
	private Long taxTypeId;

	@NotNull
	@Column(name = "tax_amt")
	private double taxAmt;

	@Column(name = "tax_valid_upto")
	private Long taxValidUpto;

	@Column(name = "tax_valid_from")
	private Long taxValidFrom;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRc;

	@Column(name = "vehicle_cov", length = 50)
	private String vehicleCov;

	@Column(name = "status")
	private Integer status;

	@Column(name = "stoppage_flag")
	private Boolean stoppageFlag;

	@Column(name = "exemption_flag")
	private Boolean exemptionFlag;

	@Column(name = "remarks", length = 255)
	private String remarks;

	@Column(name = "vehicle_reg_no", length = 50)
	private String vehicleRegNo;

	@Version
	@Column(name = "version", length = 50, columnDefinition = "Integer DEFAULT 0")
	private Integer version;

	public Long getVehicleTaxDtlId() {
		return vehicleTaxDtlId;
	}

	public void setVehicleTaxDtlId(Long vehicleTaxDtlId) {
		this.vehicleTaxDtlId = vehicleTaxDtlId;
	}

	public Long getTaxTypeId() {
		return taxTypeId;
	}

	public void setTaxTypeId(Long taxTypeId) {
		this.taxTypeId = taxTypeId;
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

	public VehicleRCEntity getVehicleRc() {
		return vehicleRc;
	}

	public void setVehicleRc(VehicleRCEntity vehicleRc) {
		this.vehicleRc = vehicleRc;
	}

	public String getVehicleCov() {
		return vehicleCov;
	}

	public void setVehicleCov(String vehicleCov) {
		this.vehicleCov = vehicleCov;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getStoppageFlag() {
		return stoppageFlag;
	}

	public void setStoppageFlag(Boolean stoppageFlag) {
		this.stoppageFlag = stoppageFlag;
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

	public double getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}
}
