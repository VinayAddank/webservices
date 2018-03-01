package org.rta.core.model.payment.registfee;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FeeModel {

	private Long feeDtlId;
	private long applicationId;
	private String applicationFee;
	private String postalCharge;
	private String smartCardFee;
	private String applicationServiceCharge;
	private String fitnessFee;
	private String permitFee;
	private String OtherPermitFee;
	private String fitnessServiceCharge;
	private String permitServiceCharge;
	private String totalFee;
	
	
	public Long getFeeDtlId() {
		return feeDtlId;
	}
	public void setFeeDtlId(Long feeDtlId) {
		this.feeDtlId = feeDtlId;
	}
	public long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}
	public String getApplicationFee() {
		return applicationFee;
	}
	public void setApplicationFee(String applicationFee) {
		this.applicationFee = applicationFee;
	}
	public String getPostalCharge() {
		return postalCharge;
	}
	public void setPostalCharge(String postalCharge) {
		this.postalCharge = postalCharge;
	}
	public String getSmartCardFee() {
		return smartCardFee;
	}
	public void setSmartCardFee(String smartCardFee) {
		this.smartCardFee = smartCardFee;
	}
	public String getApplicationServiceCharge() {
		return applicationServiceCharge;
	}
	public void setApplicationServiceCharge(String applicationServiceCharge) {
		this.applicationServiceCharge = applicationServiceCharge;
	}
	public String getFitnessFee() {
		return fitnessFee;
	}
	public void setFitnessFee(String fitnessFee) {
		this.fitnessFee = fitnessFee;
	}
	public String getPermitFee() {
		return permitFee;
	}
	public void setPermitFee(String permitFee) {
		this.permitFee = permitFee;
	}
	public String getOtherPermitFee() {
		return OtherPermitFee;
	}
	public void setOtherPermitFee(String otherPermitFee) {
		OtherPermitFee = otherPermitFee;
	}
	public String getFitnessServiceCharge() {
		return fitnessServiceCharge;
	}
	public void setFitnessServiceCharge(String fitnessServiceCharge) {
		this.fitnessServiceCharge = fitnessServiceCharge;
	}
	public String getPermitServiceCharge() {
		return permitServiceCharge;
	}
	public void setPermitServiceCharge(String permitServiceCharge) {
		this.permitServiceCharge = permitServiceCharge;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	
	
}	