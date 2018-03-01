package org.rta.core.entity.payment.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "vehicle_reg_fees_master")
public class VehRegFeesMasterEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -2756481918457884912L;

    @Id
	@Column(name = "vehicle_reg_fees_master_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_reg_fees_master_id_seq")
	@SequenceGenerator(name = "vehicle_reg_fees_master_id_seq", sequenceName = "vehicle_reg_fees_master_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "veh_reg_class_code")
	private String vehRegClassCode;

	@Column(name = "veh_reg_category")
	private String vehRegCategory;

	@Column(name = "reg_fees_active")
    private String regFeesActive;

	@Column(name = "reg_fees_amount")
    private double regFeesAmount;

	@Column(name = "reg_fees_type")
    private String regFeesType;
	
	@Column(name = "status")
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVehRegClassCode() {
		return vehRegClassCode;
	}

	public void setVehRegClassCode(String vehRegClassCode) {
		this.vehRegClassCode = vehRegClassCode;
	}

	public String getVehRegCategory() {
		return vehRegCategory;
	}

	public void setVehRegCategory(String vehRegCategory) {
		this.vehRegCategory = vehRegCategory;
	}

    public String getRegFeesActive() {
		return regFeesActive;
	}

    public void setRegFeesActive(String regFeesActive) {
		this.regFeesActive = regFeesActive;
	}

    public double getRegFeesAmount() {
		return regFeesAmount;
	}

    public void setRegFeesAmount(double regFeesAmount) {
		this.regFeesAmount = regFeesAmount;
	}

    public String getRegFeesType() {
		return regFeesType;
	}

    public void setRegFeesType(String regFeesType) {
		this.regFeesType = regFeesType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
