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
@Table(name = "late_fee_details_history")
public class LateFeeDetailsHistoryEntity extends BaseEntity{
	
	@Id
    @Column(name = "late_fee_hist_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "late_fee_details_history_seq")
    @SequenceGenerator(name = "late_fee_details_history_seq", sequenceName = "late_fee_details_history_seq", allocationSize = 1)
    private Long lateFeeHistId;

	@Column(name = "late_fee_amt")
	private double lateFeeamt;
	
	@Column(name = "late_fee_type")
	private Integer lateFeeType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRcId;
	
	@Column(name = "transaction_id")
	private Long transactionId;

	@Column(name = "status")
	private Integer status;
	
	@Column(name = "reg_type")
	private Integer regType;
	
	@NotNull
	@Column(name = "version", length = 50, columnDefinition = "Integer DEFAULT 0")
	private Integer version;

	public Long getLateFeeHistId() {
		return lateFeeHistId;
	}

	public void setLateFeeHistId(Long lateFeeHistId) {
		this.lateFeeHistId = lateFeeHistId;
	}

	public double getLateFeeamt() {
		return lateFeeamt;
	}

	public void setLateFeeamt(double lateFeeamt) {
		this.lateFeeamt = lateFeeamt;
	}

	public Integer getLateFeeType() {
		return lateFeeType;
	}

	public void setLateFeeType(Integer lateFeeType) {
		this.lateFeeType = lateFeeType;
	}


	public VehicleRCEntity getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	

}
