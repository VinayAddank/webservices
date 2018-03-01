package org.rta.core.model.payment.registfee;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FeeRuleModel {

	private int regCategory;
	private String vehicleClassCategory;
	private double trFee;
	private double trService;
	private double hpaFee;
	private double cessFee;
	private double totalTrFee;
	private double prFee;
	private double prService;
	private double postalFee;
	private double cardFee;
	private double totalPrFee;
	private double fitnessFee;
	private double fitnessService;
	private double totalFitnessFee;
    private String permitType;
	private double permitFee;
	private double permitService;
	private double totalPermitFee;
	private double otherPermitFee;
	private long gvw;
	private int ownerType;
	private boolean isFinance;
	private int seatingCapacity ;
	private double totalFee;
	private String serviceCode;
	private String licenseCategory;
	private String classOfVehicleLicense;
	private double licenseAppFee;
	private double licenseServiceCharge;
	private int specialNoFlag;
	private double specialNumberFee;
	private double hsrpAmount;
	private String vehicleClass;


	public int getRegCategory() {
		return regCategory;
	}

	public void setRegCategory(int regCategory) {
		this.regCategory = regCategory;
	}

	public String getVehicleClassCategory() {
		return vehicleClassCategory;
	}

	public void setVehicleClassCategory(String vehicleClassCategory) {
		this.vehicleClassCategory = vehicleClassCategory;
	}

		public double getTrFee() {
		return trFee;
	}

	public void setTrFee(double trFee) {
		this.trFee = trFee;
	}

	public double getTrService() {
		return trService;
	}

	public void setTrService(double trService) {
		this.trService = trService;
	}

	public double getHpaFee() {
		return hpaFee;
	}

	public void setHpaFee(double hpaFee) {
		this.hpaFee = hpaFee;
	}

	public double getCessFee() {
		return cessFee;
	}

	public void setCessFee(double cessFee) {
		this.cessFee = cessFee;
	}

	public double getTotalTrFee() {
		return totalTrFee;
	}

	public void setTotalTrFee(double totalTrFee) {
		this.totalTrFee = totalTrFee;
	}

	public double getPrFee() {
		return prFee;
	}

	public void setPrFee(double prFee) {
		this.prFee = prFee;
	}

	public double getPrService() {
		return prService;
	}

	public void setPrService(double prService) {
		this.prService = prService;
	}

	public double getPostalFee() {
		return postalFee;
	}

	public void setPostalFee(double postalFee) {
		this.postalFee = postalFee;
	}

	public double getTotalPrFee() {
		return totalPrFee;
	}

	public void setTotalPrFee(double totalPrFee) {
		this.totalPrFee = totalPrFee;
	}

	public double getFitnessFee() {
		return fitnessFee;
	}

	public void setFitnessFee(double fitnessFee) {
		this.fitnessFee = fitnessFee;
	}

	public double getFitnessService() {
		return fitnessService;
	}

	public void setFitnessService(double fitnessService) {
		this.fitnessService = fitnessService;
	}

	public double getTotalFitnessFee() {
		return totalFitnessFee;
	}

	public void setTotalFitnessFee(double totalFitnessFee) {
		this.totalFitnessFee = totalFitnessFee;
	}

    public String getPermitType() {
		return permitType;
	}

    public void setPermitType(String permitType) {
		this.permitType = permitType;
	}

	public double getPermitFee() {
		return permitFee;
	}

	public void setPermitFee(double permitFee) {
		this.permitFee = permitFee;
	}

	public double getPermitService() {
		return permitService;
	}

	public void setPermitService(double permitService) {
		this.permitService = permitService;
	}

	public double getTotalPermitFee() {
		return totalPermitFee;
	}

	public void setTotalPermitFee(double totalPermitFee) {
		this.totalPermitFee = totalPermitFee;
	}

	public double getOtherPermitFee() {
		return otherPermitFee;
	}

	public void setOtherPermitFee(double otherPermitFee) {
		this.otherPermitFee = otherPermitFee;
	}

	public long getGvw() {
		return gvw;
	}

	public void setGvw(long gvw) {
		this.gvw = gvw;
	}

	public int getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(int ownerType) {
		this.ownerType = ownerType;
	}

	public boolean getIsFinance() {
		return isFinance;
	}

	public void setIsFinance(boolean isFinance) {
		this.isFinance = isFinance;
	}

	public double getCardFee() {
		return cardFee;
	}

	public void setCardFee(double cardFee) {
		this.cardFee = cardFee;
	}

	
	public void setFinance(boolean isFinance) {
		this.isFinance = isFinance;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(int seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getLicenseCategory() {
		return licenseCategory;
	}

	public void setLicenseCategory(String licenseCategory) {
		this.licenseCategory = licenseCategory;
	}

	public String getClassOfVehicleLicense() {
		return classOfVehicleLicense;
	}

	public void setClassOfVehicleLicense(String classOfVehicleLicense) {
		this.classOfVehicleLicense = classOfVehicleLicense;
	}

	public double getLicenseAppFee() {
		return licenseAppFee;
	}

	public void setLicenseAppFee(double licenseAppFee) {
		this.licenseAppFee = licenseAppFee;
	}

	public double getLicenseServiceCharge() {
		return licenseServiceCharge;
	}

	public void setLicenseServiceCharge(double licenseServiceCharge) {
		this.licenseServiceCharge = licenseServiceCharge;
	}

	public int getSpecialNoFlag() {
		return specialNoFlag;
	}

	public void setSpecialNoFlag(int specialNoFlag) {
		this.specialNoFlag = specialNoFlag;
	}

	public double getSpecialNumberFee() {
		return specialNumberFee;
	}

	public void setSpecialNumberFee(double specialNumberFee) {
		this.specialNumberFee = specialNumberFee;
	}

	public double getHsrpAmount() {
		return hsrpAmount;
	}

	public void setHsrpAmount(double hsrpAmount) {
		this.hsrpAmount = hsrpAmount;
	}

	public String getVehicleClass() {
		return vehicleClass;
	}

	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}

	

	
}
