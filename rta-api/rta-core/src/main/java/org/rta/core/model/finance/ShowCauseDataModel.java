package org.rta.core.model.finance;

import org.rta.core.model.customer.CustomerDetailsRequestModel;
import org.rta.core.model.office.RTAOfficeModel;
import org.rta.core.model.vehicle.VehicleDetailsModel;

public class ShowCauseDataModel {
	private RTAOfficeModel rtaOfficeModel;
	private CustomerDetailsRequestModel customerDetails;
	private FinancerModel financerDetail;
	private VehicleDetailsModel vehicleDetails;

	public RTAOfficeModel getRtaOfficeModel() {
		return rtaOfficeModel;
	}

	public void setRtaOfficeModel(RTAOfficeModel rtaOfficeModel) {
		this.rtaOfficeModel = rtaOfficeModel;
	}

	public CustomerDetailsRequestModel getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetailsRequestModel customerDetails) {
		this.customerDetails = customerDetails;
	}

	public FinancerModel getFinancerDetail() {
		return financerDetail;
	}

	public void setFinancerDetail(FinancerModel financerDetail) {
		this.financerDetail = financerDetail;
	}

	public VehicleDetailsModel getVehicleDetails() {
		return vehicleDetails;
	}

	public void setVehicleDetails(VehicleDetailsModel vehicleDetails) {
		this.vehicleDetails = vehicleDetails;
	}

}
