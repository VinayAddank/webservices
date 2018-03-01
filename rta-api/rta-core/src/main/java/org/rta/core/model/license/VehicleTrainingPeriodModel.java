package org.rta.core.model.license;

public class VehicleTrainingPeriodModel {
	private String vehicleClassCode;
	private Integer firstTrainingPeriod;
	private Integer renewalTrainingPeriod;
	public String getVehicleClassCode() {
		return vehicleClassCode;
	}
	public void setVehicleClassCode(String vehicleClassCode) {
		this.vehicleClassCode = vehicleClassCode;
	}
	public Integer getFirstTrainingPeriod() {
		return firstTrainingPeriod;
	}
	public void setFirstTrainingPeriod(Integer firstTrainingPeriod) {
		this.firstTrainingPeriod = firstTrainingPeriod;
	}
	public Integer getRenewalTrainingPeriod() {
		return renewalTrainingPeriod;
	}
	public void setRenewalTrainingPeriod(Integer renewalTrainingPeriod) {
		this.renewalTrainingPeriod = renewalTrainingPeriod;
	}
}
