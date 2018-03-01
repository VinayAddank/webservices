/**
 * 
 */
package org.rta.core.entity.vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

/**
 * @author arun.verma
 *
 */
@Entity
@Table(name = "rc_lock")
public class RCLockEntity extends BaseEntity {

	private static final long serialVersionUID = 5469556832330887775L;

	@Id
	@Column(name = "rc_lock_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rc_lock_seq")
	@SequenceGenerator(name = "rc_lock_seq", sequenceName = "rc_lock_seq", allocationSize = 1)
	private Long rcLockId;

	@Column(name = "vehicle_rc_id")
	private Long vehicleRcId;

	@Column(name = "pr_number")
	private String prNumber;

	@Column(name = "cur_pr_status")
	private Integer curPrStatus;

	@Column(name = "pre_pr_status")
	private Integer prePrStatus;
	
	@Column(name = "lock_time")
	private Long lockTime;

	public Long getRcLockId() {
		return rcLockId;
	}

	public void setRcLockId(Long rcLockId) {
		this.rcLockId = rcLockId;
	}

	public Long getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(Long vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public String getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}

	public Integer getCurPrStatus() {
		return curPrStatus;
	}

	public void setCurPrStatus(Integer curPrStatus) {
		this.curPrStatus = curPrStatus;
	}

	public Integer getPrePrStatus() {
		return prePrStatus;
	}

	public void setPrePrStatus(Integer prePrStatus) {
		this.prePrStatus = prePrStatus;
	}

	public Long getLockTime() {
		return lockTime;
	}

	public void setLockTime(Long lockTime) {
		this.lockTime = lockTime;
	}

}
