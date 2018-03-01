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
@Table(name = "rta_service_charge")
public class RTAServiceChargeEntity extends BaseEntity {
    private static final long serialVersionUID = -1248092585036463269L;

    @Id
	@Column(name = "rta_service_charge_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rta_service_charge_id_seq")
	@SequenceGenerator(name = "rta_service_charge_id_seq", sequenceName = "rta_service_charge_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "module_code")
	private String ModuleCode;

	@Column(name = "transaction_id")
	private String transactionId;

    @Column(name = "vehicle_code")
    private String vehicleCode;

	@Column(name = "amount")
    private double amount;

	@Column(name = "status")
	private Integer status;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModuleCode() {
		return ModuleCode;
	}

	public void setModuleCode(String moduleCode) {
		ModuleCode = moduleCode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

    public double getAmount() {
		return amount;
	}

    public void setAmount(double amount) {
		this.amount = amount;
	}

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
