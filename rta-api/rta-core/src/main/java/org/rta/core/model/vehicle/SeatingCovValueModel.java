package org.rta.core.model.vehicle;

public class SeatingCovValueModel {

	private String vehicleSubClass;
	private int maxSeat;
	private int minSeat;
	private boolean isValid;

	public String getVehicleSubClass() {
		return vehicleSubClass;
	}

	public void setVehicleSubClass(String vehicleSubClass) {
		this.vehicleSubClass = vehicleSubClass;
	}

	public int getMaxSeat() {
		return maxSeat;
	}

	public void setMaxSeat(int maxSeat) {
		this.maxSeat = maxSeat;
	}

	public int getMinSeat() {
		return minSeat;
	}

	public void setMinSeat(int minSeat) {
		this.minSeat = minSeat;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
}
