package org.rta.registration.pan.model;

public class InsertVcrModel {
	private String vCRNo;
	private String regnNo;
	private String challanNo;
	private String transactionNo;
	private String transactionAmount;
	private String transactionDt;
	private String paymentDt;
	public String getvCRNo() {
		return vCRNo;
	}
	public void setvCRNo(String vCRNo) {
		this.vCRNo = vCRNo;
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
