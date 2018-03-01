package org.rta.core.entity.vcr;

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
@Table(name = "vcr_transaction_details")
public class VcrTransactionDetailEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name = "vcr_transaction_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vcr_tran_dtl_seq")
	@SequenceGenerator(name = "vcr_tran_dtl_seq", sequenceName = "vcr_tran_dtl_seq", allocationSize = 1)
	private Long VcrTransactionId;
	

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRcId;
	
	@NotNull
	@Column(name = "vcr_transaction_no")
	private String vcrTransactionNo;
	
	@NotNull
	@Column(name = "pr_number")
	private String prNumber;
	
	@NotNull
	@Column(name = "vcr_challan_no")
	private String vcrChallanNo;
	
	@Column(name = "pay_amount")
	private double payAmount;
	
	@Column(name = "compound_fee")
	private Integer compoundFee;
	
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

	/*@Column(name = "permit_amt")
    private double permitAmt;*/
	
	@Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;
	
	/*@Column(name = "cess_fee" , columnDefinition = "Integer DEFAULT 0")
	private double cessFee;*/

	public Long getVcrTransactionId() {
		return VcrTransactionId;
	}

	public void setVcrTransactionId(Long vcrTransactionId) {
		VcrTransactionId = vcrTransactionId;
	}

	public VehicleRCEntity getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public String getVcrTransactionNo() {
		return vcrTransactionNo;
	}

	public void setVcrTransactionNo(String vcrTransactionNo) {
		this.vcrTransactionNo = vcrTransactionNo;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getCompoundFee() {
		return compoundFee;
	}

	public void setCompoundFee(Integer compoundFee) {
		this.compoundFee = compoundFee;
	}

	public String getVcrChallanNo() {
		return vcrChallanNo;
	}

	public void setVcrChallanNo(String vcrChallanNo) {
		this.vcrChallanNo = vcrChallanNo;
	}

	public String getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}
	
	

}
