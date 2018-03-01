package org.rta.core.model.finance;

import org.rta.core.model.vehicle.VehicleDetailsModel;

public class FreshRcAppStatusDetailsModel {

	private FinanceModel financeModel;
	private VehicleDetailsModel vehicleDetailsModel;
	private FreshRcModel freshRcModel;
	
	public FinanceModel getFinanceModel() {
		return financeModel;
	}
	public void setFinanceModel(FinanceModel financeModel) {
		this.financeModel = financeModel;
	}
	public VehicleDetailsModel getVehicleDetailsModel() {
		return vehicleDetailsModel;
	}
	public void setVehicleDetailsModel(VehicleDetailsModel vehicleDetailsModel) {
		this.vehicleDetailsModel = vehicleDetailsModel;
	}
	public FreshRcModel getFreshRcModel() {
		return freshRcModel;
	}
	public void setFreshRcModel(FreshRcModel freshRcModel) {
		this.freshRcModel = freshRcModel;
	}
	
}
