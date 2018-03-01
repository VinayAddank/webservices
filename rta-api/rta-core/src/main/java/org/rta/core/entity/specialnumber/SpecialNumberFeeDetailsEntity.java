package org.rta.core.entity.specialnumber;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "special_number_fee_details")
public class SpecialNumberFeeDetailsEntity extends BaseEntity{
	private static final long serialVersionUID = -1960110118288601855L;

    @Id
    @Column(name = "special_number_fee_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "special_number_fee")
    @SequenceGenerator(name = "special_number_fee", sequenceName = "special_number_fee", allocationSize = 1)
    private Long id;
    
    
    @Column(name = "dd_number")
    private String ddNumber;

    @Column(name = "transaction_id")
    private String transactionId;

	@Column(name = "amount")
    private Double amount;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "pr_number")
    private String permanentRegistration;
    
    @Column(name = "vehical_rc_id")
    private Long vehicleRcId;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDdNumber() {
		return ddNumber;
	}

	public void setDdNumber(String ddNumber) {
		this.ddNumber = ddNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPermanentRegistration() {
		return permanentRegistration;
	}

	public void setPermanentRegistration(String permanentRegistration) {
		this.permanentRegistration = permanentRegistration;
	}

	public Long getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(Long vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}
    
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
