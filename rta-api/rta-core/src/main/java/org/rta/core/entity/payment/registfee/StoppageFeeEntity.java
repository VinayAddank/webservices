package org.rta.core.entity.payment.registfee;

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
@Table(name = "stoppage_fee")
public class StoppageFeeEntity extends BaseEntity{
	
	@Id
	@NotNull
	@Column(name = "stoppage_fee_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stoppage_fee_seq")
	@SequenceGenerator(name = "stoppage_fee_seq", sequenceName = "stoppage_fee_seq", allocationSize = 1)
	private Long stoppageFeeId;

	@Column(name = "stoppage_no", length = 50)
	private String stoppageNo;

	@Column(name = "inspected_by", length = 50)
	private String inspectedBy;
	
	@Column(name = "inspected_dt")
	private Long inspectedDt;
	
	@Column(name = "revocation_dt")
	private Long revocationDt;
	
	@Column(name = "regn_no", length = 50)
	private String regnNo;
	
	@Column(name = "tax_amt")
	private double taxAmt;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRcId;
	
	@Column(name = "quarter_from_dt")
	private Long quarterFromDt;
	
	@Column(name = "quarter_end_dt")
	private Long quarterEndDt;

	public Long getStoppageFeeId() {
		return stoppageFeeId;
	}

	public void setStoppageFeeId(Long stoppageFeeId) {
		this.stoppageFeeId = stoppageFeeId;
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

	public Long getRevocationDt() {
		return revocationDt;
	}

	public void setRevocationDt(Long revocationDt) {
		this.revocationDt = revocationDt;
	}

	public String getRegnNo() {
		return regnNo;
	}

	public void setRegnNo(String regnNo) {
		this.regnNo = regnNo;
	}

	public double getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}

	public VehicleRCEntity getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public Long getQuarterFromDt() {
		return quarterFromDt;
	}

	public void setQuarterFromDt(Long quarterFromDt) {
		this.quarterFromDt = quarterFromDt;
	}

	public Long getQuarterEndDt() {
		return quarterEndDt;
	}

	public void setQuarterEndDt(Long quarterEndDt) {
		this.quarterEndDt = quarterEndDt;
	}
	
}
