package org.rta.core.model.payment.tax;

public class VcrTaxModel {
	private double tax; 
	private String taxType;
	
	private String  penality;
	private String penalityReason;
	private String vcr;
	private String vcrReason;
	public String getPenality() {
		return penality;
	}
	public void setPenality(String penality) {
		this.penality = penality;
	}
	public String getPenalityReason() {
		return penalityReason;
	}
	public void setPenalityReason(String penalityReason) {
		this.penalityReason = penalityReason;
	}
	public String getVcr() {
		return vcr;
	}
	public void setVcr(String vcr) {
		this.vcr = vcr;
	}
	public String getVcrReason() {
		return vcrReason;
	}
	public void setVcrReason(String vcrReason) {
		this.vcrReason = vcrReason;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

}
