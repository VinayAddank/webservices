package org.rta.core.entity.payment.registfee;

import javax.persistence.CascadeType;
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
@Table(name = "late_fee_details")
public class LateFeeDetailsEntity extends BaseEntity {

    private static final long serialVersionUID = -6565256389973609622L;

    @Id
    @Column(name = "late_fee_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "late_fee_seq")
    @SequenceGenerator(name = "late_fee_seq", sequenceName = "late_fee_seq", allocationSize = 1)
    private Long lateFeeId;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_rc_id" , unique=true)
    private VehicleRCEntity vehicleRc;

    @Column(name = "late_fee_amt")
    private double lateFeeAmt;

    @Column(name = "late_fee_type")
    private Integer lateFeeType;

    @Column(name = "transaction_id")
    private Long transactionId;
    
    @Column(name = "reg_type")
    private Integer regType;
    
    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;

	public Long getLateFeeId() {
		return lateFeeId;
	}

	public void setLateFeeId(Long lateFeeId) {
		this.lateFeeId = lateFeeId;
	}

	public VehicleRCEntity getVehicleRc() {
		return vehicleRc;
	}

	public void setVehicleRc(VehicleRCEntity vehicleRc) {
		this.vehicleRc = vehicleRc;
	}

	public double getLateFeeAmt() {
		return lateFeeAmt;
	}

	public void setLateFeeAmt(double lateFeeAmt) {
		this.lateFeeAmt = lateFeeAmt;
	}

	public Integer getLateFeeType() {
		return lateFeeType;
	}

	public void setLateFeeType(Integer lateFeeType) {
		this.lateFeeType = lateFeeType;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

    
    
}
