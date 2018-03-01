package org.rta.core.model.payment.tax;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaxRuleModel {

	private int regCategory;
	private String taxType;
	private double tax;
	private String ownerType;
	private boolean isPHCertificate;
	private boolean isPHDriverLicense;
	private String vehicleClass;
	private double invoiceAmount;
	private int noOfVehicle;
	private String fuelType;
	private double taxAmount;
	private int monthType;
	private String vehicleClassCategory;
	private int seatingCapacity;
	private long gvw;
	private long ulw;
	private double hsrpAmount;
	private boolean isDisabled;
	private boolean isInvalidCarriage;
	private String serviceCode;
	private double cessFee;
	private double hpaFee ;
	private boolean iSuzo;
	private double totalAmt;
	private String permitType ;
	private boolean greenTax;
	private double greenTaxAmt;
	private String stateCode;
	private Double quarterAmt;
	private boolean isPermitValid;
	private boolean isCessFeeValid;	
	private long taxValidUpto;
	private String permitSubType;
	
	public int getRegCategory() {
		return regCategory;
	}
	public void setRegCategory(int regCategory) {
		this.regCategory = regCategory;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public String getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	public boolean getIsPHCertificate() {
		return isPHCertificate;
	}
	public void setIsPHCertificate(boolean isPHCertificate) {
		this.isPHCertificate = isPHCertificate;
	}
	public boolean getIsPHDriverLicense() {
		return isPHDriverLicense;
	}
	public void setIsPHDriverLicense(boolean isPHDriverLicense) {
		this.isPHDriverLicense = isPHDriverLicense;
	}
	public String getVehicleClass() {
		return vehicleClass;
	}
	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}
	public double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public int getMonthType() {
		return monthType;
	}
	public void setMonthType(int monthType) {
		this.monthType = monthType;
	}
	public String getVehicleClassCategory() {
		return vehicleClassCategory;
	}
	public void setVehicleClassCategory(String vehicleClassCategory) {
		this.vehicleClassCategory = vehicleClassCategory;
	}
	public int getSeatingCapacity() {
		return seatingCapacity;
	}
	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}
	public long getGvw() {
		return gvw;
	}
	public void setGvw(long gvw) {
		this.gvw = gvw;
	}
	public long getUlw() {
		return ulw;
	}
	public void setUlw(long ulw) {
		this.ulw = ulw;
	}
	public boolean getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
	public boolean getIsInvalidCarriage() {
		return isInvalidCarriage;
	}
	public void setIsInvalidCarriage(boolean isInvalidCarriage) {
		this.isInvalidCarriage = isInvalidCarriage;
	}
	public String getPermitType() {
		return permitType;
	}
	public void setPermitType(String permitType) {
		this.permitType = permitType;
	}

	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public double getCessFee() {
		return cessFee;
	}
	public void setCessFee(double cessFee) {
		this.cessFee = cessFee;
	}
	public double getHpaFee() {
		return hpaFee;
	}
	public void setHpaFee(double hpaFee) {
		this.hpaFee = hpaFee;
	}
	public boolean getISuzo() {
		return iSuzo;
	}
	public void setISuzo(boolean iSuzo) {
		this.iSuzo = iSuzo;
	}
	public double getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}
	public double getHsrpAmount() {
		return hsrpAmount;
	}
	public void setHsrpAmount(double hsrpAmount) {
		this.hsrpAmount = hsrpAmount;
	}
	public boolean isGreenTax() {
		return greenTax;
	}
	public void setGreenTax(boolean greenTax) {
		this.greenTax = greenTax;
	}
	public double getGreenTaxAmt() {
		return greenTaxAmt;
	}
	public void setGreenTaxAmt(double greenTaxAmt) {
		this.greenTaxAmt = greenTaxAmt;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public int getNoOfVehicle() {
		return noOfVehicle;
	}
	public void setNoOfVehicle(int noOfVehicle) {
		this.noOfVehicle = noOfVehicle;
	}
	public long getTaxValidUpto() {
		return taxValidUpto;
	}

	public void setTaxValidUpto(long taxValidUpto) {
		this.taxValidUpto = taxValidUpto;
	}
	public Double getQuarterAmt() {
		return quarterAmt;
	}
	public void setQuarterAmt(Double quarterAmt) {
		this.quarterAmt = quarterAmt;
	}
	public boolean isPermitValid() {
		return isPermitValid;
	}
	public void setPermitValid(boolean isPermitValid) {
		this.isPermitValid = isPermitValid;
	}
	public boolean getIsCessFeeValid() {
		return isCessFeeValid;
	}
	public void setIsCessFeeValid(boolean isCessFeeValid) {
		this.isCessFeeValid = isCessFeeValid;
	}
	public String getPermitSubType() {
		return permitSubType;
	}
	public void setPermitSubType(String permitSubType) {
		this.permitSubType = permitSubType;
	}
	

}
