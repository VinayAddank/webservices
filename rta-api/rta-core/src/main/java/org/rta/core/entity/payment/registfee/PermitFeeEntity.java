package org.rta.core.entity.payment.registfee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "permit_fee_detail")
public class PermitFeeEntity extends BaseEntity {

	private static final long serialVersionUID = -1740199017087365704L;

	@Id
    @Column(name = "permit_fee_Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permit_fee_seq")
    @SequenceGenerator(name = "permit_fee_seq", sequenceName = "permit_fee_seq", allocationSize = 1)
    private Long permitFeeId;

	@Column(name = "vehicle_rc_id")
    private long vehicleRcId;
	
	@Column(name = "permit_fee")
    private double permitFee;
    
    @Column(name = "permit_service")
    private double permitService;
    
    @Column(name = "total_permit_fee")
    private double totalpermitFee;
    
    @Column(name = "other_permit_fee")
    private double otherPermitFee;
    
    @Column(name = "permit_type_code")
    private String permitTypeCode;
    
    @Column(name = "status")
    private Integer status;

	public Long getPermitFeeId() {
		return permitFeeId;
	}

	public void setPermitFeeId(Long permitFeeId) {
		this.permitFeeId = permitFeeId;
	}

	public long getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(long vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public double getPermitFee() {
		return permitFee;
	}

	public void setPermitFee(double permitFee) {
		this.permitFee = permitFee;
	}

	public double getPermitService() {
		return permitService;
	}

	public void setPermitService(double permitService) {
		this.permitService = permitService;
	}

	public double getTotalpermitFee() {
		return totalpermitFee;
	}

	public void setTotalpermitFee(double totalpermitFee) {
		this.totalpermitFee = totalpermitFee;
	}

	public double getOtherPermitFee() {
		return otherPermitFee;
	}

	public void setOtherPermitFee(double otherPermitFee) {
		this.otherPermitFee = otherPermitFee;
	}

	public String getPermitTypeCode() {
		return permitTypeCode;
	}

	public void setPermitTypeCode(String permitTypeCode) {
		this.permitTypeCode = permitTypeCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	    
    
}
