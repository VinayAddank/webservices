package org.rta.core.model.vehicle.cms;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class VehicleClassDescModel {

	private String vehicleCode;
	private String vehicleDesc;
	private Integer alterationCategory;

	public String getVehicleCode() {
		return vehicleCode;
	}

	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}

	public String getVehicleDesc() {
		return vehicleDesc;
	}

	public void setVehicleDesc(String vehicleDesc) {
		this.vehicleDesc = vehicleDesc;
	}

	public Integer getAlterationCategory() {
		return alterationCategory;
	}

	public void setAlterationCategory(Integer alterationCategory) {
		this.alterationCategory = alterationCategory;
	}
	
}
