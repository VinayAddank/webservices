package org.rta.core.entity.payment.gateway;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name = "transaction_detail")
public class TransactionDetailEntity extends BaseEntity {

	private static final long serialVersionUID = 3962338293823130468L;

	@Id
	@Column(name = "transaction_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tran_dtl_seq")
	@SequenceGenerator(name = "tran_dtl_seq", sequenceName = "tran_dtl_seq", allocationSize = 1)
	private Long transactionId;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRcId;

	@NotNull
	@Column(name = "transaction_no")
	private String transactionNo;

	@Column(name = "pay_amount")
	private double payAmount;

	@Column(name = "payment_type")
	private Integer paymentType;

	@Column(name = "payment_time")
	private Long paymentTime;

	@Column(name = "account_type")
	private String accountType;

	@NotNull
	@Column(name = "status")
	private Integer status;

	@Column(name = "sbi_ref_no")
	private String sbiRefNo;

	@Column(name = "status_message")
	private String statusMessage;

	@Column(name = "fee_amt")
	private double FeeAmt;

	@Column(name = "service_charge")
	private double serviceCharge;

	@Column(name = "postal_charge")
	private double postalCharge;

	@Column(name = "tax_amt")
	private double taxAmt;

	@Column(name = "hsrp_amt")
	private double hsrpAmt;

	@Column(name = "permit_amt")
    private double permitAmt;
	
	@Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;
	
	@Column(name = "cess_fee" )
	private double cessFee;

	@Column(name = "pg_type")
	private String pgType;
	
    public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public VehicleRCEntity getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	
	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Long getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Long paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSbiRefNo() {
		return sbiRefNo;
	}

	public void setSbiRefNo(String sbiRefNo) {
		this.sbiRefNo = sbiRefNo;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public double getFeeAmt() {
		return FeeAmt;
	}

	public void setFeeAmt(double feeAmt) {
		FeeAmt = feeAmt;
	}

	public double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public double getPostalCharge() {
		return postalCharge;
	}

	public void setPostalCharge(double postalCharge) {
		this.postalCharge = postalCharge;
	}

	public double getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}

	public double getHsrpAmt() {
		return hsrpAmt;
	}

	public void setHsrpAmt(double hsrpAmt) {
		this.hsrpAmt = hsrpAmt;
	}

    public double getPermitAmt() {
        return permitAmt;
    }

    public void setPermitAmt(double permitAmt) {
        this.permitAmt = permitAmt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

	public double getCessFee() {
		return cessFee;
	}

	public void setCessFee(double cessFee) {
		this.cessFee = cessFee;
	}
	
	public String getPgType() {
		return pgType;
	}

	public void setPgType(String pgType) {
		this.pgType = pgType;
	}
    
}
