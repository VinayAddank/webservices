package org.rta.core.model.payment.tax;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CessFeeModel {

	private double cessFee;

	public double getCessFee() {
		return cessFee;
	}

	public void setCessFee(double cessFee) {
		this.cessFee = cessFee;
	}
	
	
}
