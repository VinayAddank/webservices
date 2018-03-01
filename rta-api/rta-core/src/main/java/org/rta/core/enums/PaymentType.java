package org.rta.core.enums;

import org.rta.core.utils.ObjectsUtil;

/**
 * @Author sandeep.yadav
 */

public enum PaymentType {

	PAYTAX(1, "payTax"), SECONDVEHICLETAXPAY(2, "Secondvehicletaxpay"), PAYTAXVERIFICATION(3,
			"PayTaxVerification"), SECONDVEHICLETAXVERIFICATION(4, "SecondVehicleTaxVerification"), FRESH_RC_FINANCE(5, "FRESH RC FINANCE");

	private Integer id;
	private String label;

	private PaymentType() {
	}

	private PaymentType(Integer id, String label) {
		this.id = id;
		this.label = label;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public static PaymentType getPaymentType(Integer value) {
		if (ObjectsUtil.isNull(value)) {
			return null;
		}
		if (value == PAYTAX.getId()) {
			return PAYTAX;
		} else if (value == PAYTAXVERIFICATION.getId()) {
			return PAYTAXVERIFICATION;
		} else if (value == SECONDVEHICLETAXPAY.getId()) {
			return SECONDVEHICLETAXPAY;
		} else if (value == SECONDVEHICLETAXVERIFICATION.getId()) {
			return SECONDVEHICLETAXVERIFICATION;
		}else if (value == FRESH_RC_FINANCE.getId()) {
			return FRESH_RC_FINANCE;
		}
		return null;
	}

}
