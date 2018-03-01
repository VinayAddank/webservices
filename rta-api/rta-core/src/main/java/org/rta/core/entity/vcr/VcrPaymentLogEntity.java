package org.rta.core.entity.vcr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "vcr_payment_logs")
public class VcrPaymentLogEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vcrpayment_log_seq")
	@SequenceGenerator(name = "vcrpayment_log_seq", sequenceName = "vcrpayment_log_seq", allocationSize = 1)
	private Long logId;
	
	@Column(name = "vcr_number")
	private String vcrNo;
	
	@Column(name = "Regn_no")
	private String regnNo;
	
	@Column(name = "challan_no")
	private String challanNo;
	@Column(name = "transaction_no")
	private String transactionNo;
	@Column(name = "transaction_amount")
	private String transactionAmount;
	@Column(name = "transaction_dt")
	private String transactionDt;
	@Column(name = "payment_dt")
	private String paymentDt;
	
	@Column(name = "vcr_api_status_flag")
	private String vcrApistatusFlag;

	public String getVcrApistatusFlag() {
		return vcrApistatusFlag;
	}

	public void setVcrApistatusFlag(String vcrApistatusFlag) {
		this.vcrApistatusFlag = vcrApistatusFlag;
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getVcrNo() {
		return vcrNo;
	}

	public void setVcrNo(String vcrNo) {
		this.vcrNo = vcrNo;
	}

	public String getRegnNo() {
		return regnNo;
	}

	public void setRegnNo(String regnNo) {
		this.regnNo = regnNo;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionDt() {
		return transactionDt;
	}

	public void setTransactionDt(String transactionDt) {
		this.transactionDt = transactionDt;
	}

	public String getPaymentDt() {
		return paymentDt;
	}

	public void setPaymentDt(String paymentDt) {
		this.paymentDt = paymentDt;
	}

}
