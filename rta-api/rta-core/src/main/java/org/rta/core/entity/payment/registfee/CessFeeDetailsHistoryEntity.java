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
@Table(name = "cess_fee_details_history")
public class CessFeeDetailsHistoryEntity extends BaseEntity {

	private static final long serialVersionUID = 545020566771803064L;
	
	@Id
	@Column(name = "cess_dtl_hist_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cess_fee_dtl_hist_seq")
	@SequenceGenerator(name = "cess_fee_dtl_hist_seq", sequenceName = "cess_fee_dtl_hist_seq", allocationSize = 1)
	private Long cessFeeDtlHistId;

	@NotNull
    @Column(name = "vehicle_rc_id")
    private Long vehicleRc;

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

	public Long getCessFeeDtlHistId() {
		return cessFeeDtlHistId;
	}

	public void setCessFeeDtlHistId(Long cessFeeDtlHistId) {
		this.cessFeeDtlHistId = cessFeeDtlHistId;
	}

	public Long getVehicleRc() {
		return vehicleRc;
	}

	public void setVehicleRc(Long vehicleRc) {
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
