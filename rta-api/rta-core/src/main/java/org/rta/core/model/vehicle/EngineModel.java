package org.rta.core.model.vehicle;

import org.rta.core.model.base.BaseModel;

public class EngineModel extends BaseModel {

    private static final long serialVersionUID = -2012603806221607117L;
    private String chassisNumber;
    private String engineNumber;
    private String fuelUsed;

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getFuelUsed() {
        return fuelUsed;
    }

    public void setFuelUsed(String fuelUsed) {
        this.fuelUsed = fuelUsed;
    }


}
