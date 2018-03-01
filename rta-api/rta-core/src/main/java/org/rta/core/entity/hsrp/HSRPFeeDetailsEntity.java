package org.rta.core.entity.hsrp;

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
@Table(name = "hsrp_fee_details")
public class HSRPFeeDetailsEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8883223940043663959L;

	@Id
	@Column(name = "hsrp_fee_dtl_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hsrp_fee_dtl_seq")
	@SequenceGenerator(name = "hsrp_fee_dtl_seq", sequenceName = "hsrp_fee_dtl_seq", allocationSize = 1)
	private Long hsrpFeeDtlId;

	@NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_rc_id" , unique=true)
    private VehicleRCEntity vehicleRc;

	@NotNull
	@Column(name = "hsrp_fee")
	private double HSRPFee;
	
	@Column(name = "transaction_id")
    private Long transactionId;
    
    @Column(name = "reg_type")
    private Integer regType;
    
    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;

	public Long getHsrpFeeDtlId() {
		return hsrpFeeDtlId;
	}

	public void setHsrpFeeDtlId(Long hsrpFeeDtlId) {
		this.hsrpFeeDtlId = hsrpFeeDtlId;
	}

	public VehicleRCEntity getVehicleRc() {
		return vehicleRc;
	}

	public void setVehicleRc(VehicleRCEntity vehicleRc) {
		this.vehicleRc = vehicleRc;
	}

	public double getHSRPFee() {
		return HSRPFee;
	}

	public void setHSRPFee(double hSRPFee) {
		HSRPFee = hSRPFee;
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
