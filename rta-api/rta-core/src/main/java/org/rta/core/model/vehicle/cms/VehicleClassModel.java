package org.rta.core.model.vehicle.cms;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class VehicleClassModel {

	private String vehicleClassCode;
	private String vehicleCategory;
	private String vehicleClassText;

	public String getVehicleClassCode() {
		return vehicleClassCode;
	}

	public void setVehicleClassCode(String vehicleClassCode) {
		this.vehicleClassCode = vehicleClassCode;
	}

	public String getVehicleCategory() {
		return vehicleCategory;
	}

	public void setVehicleCategory(String vehicleCategory) {
		this.vehicleCategory = vehicleCategory;
	}

    public String getVehicleClassText() {
        return vehicleClassText;
    }

    public void setVehicleClassText(String vehicleClassText) {
        this.vehicleClassText = vehicleClassText;
    }

}
