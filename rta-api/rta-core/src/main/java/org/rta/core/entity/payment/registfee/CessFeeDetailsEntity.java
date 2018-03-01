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
@Table(name = "cess_fee_details")
public class CessFeeDetailsEntity extends BaseEntity {

	private static final long serialVersionUID = 5450205667718030640L;
	
	@Id
	@Column(name = "cess_fee_dtl_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cess_fee_dtl_seq")
	@SequenceGenerator(name = "cess_fee_dtl_seq", sequenceName = "cess_fee_dtl_seq", allocationSize = 1)
	private Long cessFeeDtlId;

	@NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_rc_id" , unique=true)
    private VehicleRCEntity vehicleRc;

	@NotNull
	@Column(name = "cess_fee")
	private double cessFee;
	
	@Column(name = "cess_fee_valid_upto")
	private Long cessFeeValidUpto;
	
	@Column(name = "transaction_id")
    private Long transactionId;
    
    @Column(name = "reg_type")
    private Integer regType;
    
    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;

	public Long getCessFeeDtlId() {
		return cessFeeDtlId;
	}

	public void setCessFeeDtlId(Long cessFeeDtlId) {
		this.cessFeeDtlId = cessFeeDtlId;
	}

	public VehicleRCEntity getVehicleRc() {
		return vehicleRc;
	}

	public void setVehicleRc(VehicleRCEntity vehicleRc) {
		this.vehicleRc = vehicleRc;
	}

	public double getCessFee() {
		return cessFee;
	}

	public void setCessFee(double cessFee) {
		this.cessFee = cessFee;
	}

	public Long getCessFeeValidUpto() {
		return cessFeeValidUpto;
	}

	public void setCessFeeValidUpto(Long cessFeeValidUpto) {
		this.cessFeeValidUpto = cessFeeValidUpto;
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
