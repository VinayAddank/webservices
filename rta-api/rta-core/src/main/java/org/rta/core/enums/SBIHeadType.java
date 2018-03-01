package org.rta.core.enums;

import org.rta.core.utils.ObjectsUtil;

/**
 * @Author sandeep.yadav
 */

public enum SBIHeadType {

	FEE(1, "Fee"), LIFETAX(2, "LifeTax"), QUATERLYTAX(3, "QuaterlyTax"), SERVICEFEE(4, "ServiceFee"), POSTALFEE(5,
            "PostalFee"), HSRP(6, "HSRP"), PERMITFEE(7, "PERMITFEE"), GREENTAX(8, "GreenTax"), CESSFEE(9, "CessFee"), COMPOUNDINGFEE(10, "CompoundingFee");

	private Integer id;
	private String label;

	private SBIHeadType() {
	}

	private SBIHeadType(Integer id, String label) {
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

	public static SBIHeadType getSBIHeadType(String value) {
		if (ObjectsUtil.isNull(value)) {
			return null;
		}
		if (value.equalsIgnoreCase(FEE.getLabel())) {
			return FEE;
		} else if (value.equalsIgnoreCase(LIFETAX.getLabel())) {
			return LIFETAX;
		} else if (value.equalsIgnoreCase(QUATERLYTAX.getLabel())) {
			return QUATERLYTAX;
		} else if (value.equalsIgnoreCase(SERVICEFEE.getLabel())) {
			return SERVICEFEE;
		} else if (value.equalsIgnoreCase(POSTALFEE.getLabel())) {
			return POSTALFEE;
		} else if (value.equalsIgnoreCase(HSRP.getLabel())) {
			return HSRP;
        } else if (value.equalsIgnoreCase(PERMITFEE.getLabel())) {
            return PERMITFEE;
        }else if (value.equalsIgnoreCase(GREENTAX.getLabel())) {
            return GREENTAX;
        }else if (value.equalsIgnoreCase(CESSFEE.getLabel())) {
            return CESSFEE;
        }
        else if (value.equalsIgnoreCase(COMPOUNDINGFEE.getLabel())) {
            return COMPOUNDINGFEE;
        }
		return null;
	}

}
