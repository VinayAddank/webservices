package org.rta.core.model.payment.registfee;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HSRPModel {

	private double hsrpAmount ;

	public double getHsrpAmount() {
		return hsrpAmount;
	}

	public void setHsrpAmount(double hsrpAmount) {
		this.hsrpAmount = hsrpAmount;
	}
	
}
