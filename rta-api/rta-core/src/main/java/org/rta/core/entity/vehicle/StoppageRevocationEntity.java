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
@Table(name = "stoppage_revocation")
public class StoppageRevocationEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(name = "stoppage_revocation_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stoppage_revocation_seq")
	@SequenceGenerator(name = "stoppage_revocation_seq", sequenceName = "stoppage_revocation_seq", allocationSize = 1)
	private Long stoppageRevocationId;

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
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRcId;
	
	@Column(name = "approved_by", length = 50)
	private String approvedBy;
	
	@Column(name = "approved_on")
	private Long approvedOn;

	public Long getStoppageRevocationId() {
		return stoppageRevocationId;
	}

	public void setStoppageRevocationId(Long stoppageRevocationId) {
		this.stoppageRevocationId = stoppageRevocationId;
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

	public VehicleRCEntity getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Long approvedOn) {
		this.approvedOn = approvedOn;
	}
	

}
