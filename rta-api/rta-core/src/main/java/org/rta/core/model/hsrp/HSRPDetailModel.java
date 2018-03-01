package org.rta.core.model.hsrp;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class HSRPDetailModel {

	private Long hsrp_detail_Id;
	private Long vehicleRcId;
	private double amount;
	private String transactionNo;
	private String authRefNo;
	private String embossingDate;
	private String frontLaserCode;
	private String rearLaserCode;
	private String plateAvailDate;
	private String affixationDate;
	private String affixationCenterCode;
	private String transactionStatus;
	private String paymentReceivedDate;
	private String orderNo;
	private String orderDate;
	private String hsrpAvailabilityDate;
	private Long createdOn;

	public Long getHsrp_detail_Id() {
		return hsrp_detail_Id;
	}

	public void setHsrp_detail_Id(Long hsrp_detail_Id) {
		this.hsrp_detail_Id = hsrp_detail_Id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getAuthRefNo() {
		return authRefNo;
	}

	public void setAuthRefNo(String authRefNo) {
		this.authRefNo = authRefNo;
	}

	public String getEmbossingDate() {
		return embossingDate;
	}

	public void setEmbossingDate(String embossingDate) {
		this.embossingDate = embossingDate;
	}

	public String getFrontLaserCode() {
		return frontLaserCode;
	}

	public void setFrontLaserCode(String frontLaserCode) {
		this.frontLaserCode = frontLaserCode;
	}

	public String getRearLaserCode() {
		return rearLaserCode;
	}

	public void setRearLaserCode(String rearLaserCode) {
		this.rearLaserCode = rearLaserCode;
	}

	public String getPlateAvailDate() {
		return plateAvailDate;
	}

	public Long getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(Long vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public void setPlateAvailDate(String plateAvailDate) {
		this.plateAvailDate = plateAvailDate;
	}

	public String getAffixationDate() {
		return affixationDate;
	}

	public void setAffixationDate(String affixationDate) {
		this.affixationDate = affixationDate;
	}

	public String getAffixationCenterCode() {
		return affixationCenterCode;
	}

	public void setAffixationCenterCode(String affixationCenterCode) {
		this.affixationCenterCode = affixationCenterCode;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getPaymentReceivedDate() {
		return paymentReceivedDate;
	}

	public void setPaymentReceivedDate(String paymentReceivedDate) {
		this.paymentReceivedDate = paymentReceivedDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getHsrpAvailabilityDate() {
		return hsrpAvailabilityDate;
	}

	public void setHsrpAvailabilityDate(String hsrpAvailabilityDate) {
		this.hsrpAvailabilityDate = hsrpAvailabilityDate;
	}

	public Long getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Long createdOn) {
		this.createdOn = createdOn;
	}

}
