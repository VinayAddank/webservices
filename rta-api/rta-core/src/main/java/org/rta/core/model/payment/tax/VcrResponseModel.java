package org.rta.core.model.payment.tax;

public class VcrResponseModel {    
    private double fine;
    private boolean vcrFlag;
    private String pliedAs;
    private long vcrBookedDt;
    private String vehicleSiezed;
   

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

	public boolean getVcrFlag() {
		return vcrFlag;
	}

	public void setVcrFlag(boolean vcrFlag) {
		this.vcrFlag = vcrFlag;
	}

	public String getPliedAs() {
		return pliedAs;
	}

	public void setPliedAs(String pliedAs) {
		this.pliedAs = pliedAs;
	}

	public long getVcrBookedDt() {
		return vcrBookedDt;
	}

	public void setVcrBookedDt(long vcrBookedDt) {
		this.vcrBookedDt = vcrBookedDt;
	}

	public String getVehicleSiezed() {
		return vehicleSiezed;
	}

	public void setVehicleSiezed(String vehicleSiezed) {
		this.vehicleSiezed = vehicleSiezed;
	}

	
}
