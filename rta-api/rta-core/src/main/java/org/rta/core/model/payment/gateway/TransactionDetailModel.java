package org.rta.core.model.payment.gateway;

import java.util.HashMap;
import java.util.Optional;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransactionDetailModel {

	
	private String transactionNo;
	private Double amount;
	private String vehicleRcId;
	private String encryptRequest;
	private String decryptRequest;
	private String status;
	private String message;
	private HashMap<String, String> sbiResponseMap;
	private Long transactionId;
	private double FeeAmt;
	private double serviceCharge;
	private double postalCharge;
	private double taxAmt;
	private double hsrpAmt;
    private double permitAmt;
    private int sbiVerifyStatus;
    private Integer payType;
    private String sbiRefNo;
    private String createdBy;
    private String paymentDate;
    private String serviceCode;
    private int regType;
    private Boolean newApplicantFlag;
    private String remiterName;
    private String districtCode ;
    private String appNo;
    private double greenTaxAmt;
    private double cessFee;
    
 // PayU
 	private String firstName;
 	private String email;
 	private String phoneNumber;
 	private String productInfo;
 	private String key;
 	private String hash;
 	private String salt;
 	private PayUTransactionDetails payUTransactionDetails;
 	// vcr 
 	private double compoundFee;
 	private String prNumber;
    private String pgType;
    private boolean citizenFlag;
    private boolean payURespopnseStatus;
 	
	
    public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getVehicleRcId() {
		return vehicleRcId;
	}
	public void setVehicleRcId(String vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}
	public String getEncryptRequest() {
		return encryptRequest;
	}
	public void setEncryptRequest(String encryptRequest) {
		this.encryptRequest = encryptRequest;
	}
	public String getDecryptRequest() {
		return decryptRequest;
	}
	public void setDecryptRequest(String decryptRequest) {
		this.decryptRequest = decryptRequest;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HashMap<String, String> getSbiResponseMap() {
		return sbiResponseMap;
	}
	public void setSbiResponseMap(HashMap<String, String> sbiResponseMap) {
		this.sbiResponseMap = sbiResponseMap;
	}
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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
	public int getSbiVerifyStatus() {
		return sbiVerifyStatus;
	}
	public void setSbiVerifyStatus(int sbiVerifyStatus) {
		this.sbiVerifyStatus = sbiVerifyStatus;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public String getSbiRefNo() {
		return sbiRefNo;
	}
	public void setSbiRefNo(String sbiRefNo) {
		this.sbiRefNo = sbiRefNo;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public int getRegType() {
		return regType;
	}
	public void setRegType(int regType) {
		this.regType = regType;
	}
	
	public String getRemiterName() {
		return remiterName;
	}
	public void setRemiterName(String remiterName) {
		this.remiterName = remiterName;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	
	public Boolean getNewApplicantFlag() {
		return newApplicantFlag;
	}
	public void setNewApplicantFlag(Boolean newApplicantFlag) {
		this.newApplicantFlag = newApplicantFlag;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public double getGreenTaxAmt() {
		return greenTaxAmt;
	}
	public void setGreenTaxAmt(double greenTaxAmt) {
		this.greenTaxAmt = greenTaxAmt;
	}
	public double getCessFee() {
		return cessFee;
	}
	public void setCessFee(double cessFee) {
		this.cessFee = cessFee;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public PayUTransactionDetails getPayUTransactionDetails() {
		return payUTransactionDetails;
	}
	public void setPayUTransactionDetails(PayUTransactionDetails payUTransactionDetails) {
		this.payUTransactionDetails = payUTransactionDetails;
	}

    public double getCompoundFee() {
		return compoundFee;
	}
	public void setCompoundFee(double compoundFee) {
		this.compoundFee = compoundFee;
	}
	
	public String getPrNumber() {
		return prNumber;
	}
	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}
	public String getPgType() {
		return pgType;
	}
	public void setPgType(String pgType) {
		this.pgType = pgType;
	}
	public boolean getCitizenFlag() {
		return citizenFlag;
	}
	public void setCitizenFlag(boolean citizenFlag) {
		this.citizenFlag = citizenFlag;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public boolean getPayURespopnseStatus() {
		return payURespopnseStatus;
	}
	public void setPayURespopnseStatus(boolean payURespopnseStatus) {
		this.payURespopnseStatus = payURespopnseStatus;
	}
	@Override
	public String toString() {
		return "TransactionDetailModel [transactionNo=" + transactionNo + ", amount=" + amount + ", vehicleRcId="
				+ vehicleRcId + ", encryptRequest=" + encryptRequest + ", decryptRequest=" + decryptRequest
				+ ", status=" + status + ", message=" + message + ", sbiResponseMap=" + sbiResponseMap
				+ ", transactionId=" + transactionId + ", FeeAmt=" + FeeAmt + ", serviceCharge=" + serviceCharge
				+ ", postalCharge=" + postalCharge + ", taxAmt=" + taxAmt + ", hsrpAmt=" + hsrpAmt + ", permitAmt="
				+ permitAmt + ", sbiVerifyStatus=" + sbiVerifyStatus + ", payType=" + payType + ", sbiRefNo=" + sbiRefNo
				+ ", createdBy=" + createdBy + ", paymentDate=" + paymentDate + ", serviceCode=" + serviceCode
				+ ", regType=" + regType + ", newApplicantFlag=" + newApplicantFlag + ", remiterName=" + remiterName
				+ ", districtCode=" + districtCode + ", appNo=" + appNo + ", greenTaxAmt=" + greenTaxAmt + ", cessFee="
				+ cessFee + ", firstName=" + firstName + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", productInfo=" + productInfo + ", key=" + key + ", hash=" + hash + ", payUTransactionDetails="
				+ payUTransactionDetails + ", compoundFee=" + compoundFee + "]";
	}
	


	
	
}
