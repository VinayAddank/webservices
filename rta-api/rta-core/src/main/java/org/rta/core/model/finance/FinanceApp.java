package org.rta.core.model.finance;

import org.rta.core.model.user.AddressModel;

public class FinanceApp {

	private String tokenId;
	private Long requestedDate;
	private Long appNo;
	private String appStatus;
	private String firstName;
	private String lastName;
	private String fatherName;
	private String classType;
	private Integer serviceTypeId;
	private String serviceCode;
	private String serviceTypeLabel;
	private String financerName;
	private Long rtaOfficeId;
	private String rtaOfficeCode;
	private String rtaOfficeName;
	private AddressModel address;
	private String makersClass;
	private Double quotationPrice;

	
	public Double getQuotationPrice() {
		return quotationPrice;
	}

	public void setQuotationPrice(Double quotationPrice) {
		this.quotationPrice = quotationPrice;
	}

	public String getMakersClass() {
		return makersClass;
	}

	public void setMakersClass(String makersClass) {
		this.makersClass = makersClass;
	}

	public Long getAppNo() {
		return appNo;
	}

	public void setAppNo(Long appNo) {
		this.appNo = appNo;
	}

	public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Long getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Long requestedDate) {
		this.requestedDate = requestedDate;
	}

	public Integer getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public String getServiceTypeLabel() {
		return serviceTypeLabel;
	}

	public void setServiceTypeLabel(String serviceTypeLabel) {
		this.serviceTypeLabel = serviceTypeLabel;
	}

	public AddressModel getAddress() {
		return address;
	}

	public void setAddress(AddressModel address) {
		this.address = address;
	}

	public String getFinancerName() {
		return financerName;
	}

	public void setFinancerName(String financerName) {
		this.financerName = financerName;
	}

	public Long getRtaOfficeId() {
		return rtaOfficeId;
	}

	public void setRtaOfficeId(Long rtaOfficeId) {
		this.rtaOfficeId = rtaOfficeId;
	}

	public String getRtaOfficeName() {
		return rtaOfficeName;
	}

	public void setRtaOfficeName(String rtaOfficeName) {
		this.rtaOfficeName = rtaOfficeName;
	}

	public String getRtaOfficeCode() {
		return rtaOfficeCode;
	}

	public void setRtaOfficeCode(String rtaOfficeCode) {
		this.rtaOfficeCode = rtaOfficeCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	
	

}
