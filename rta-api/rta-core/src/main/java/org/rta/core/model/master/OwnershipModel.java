package org.rta.core.model.master;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OwnershipModel extends BaseMasterModel {
	
	private String ownershipCode;

	public String getOwnershipCode() {
		return ownershipCode;
	}

	public void setOwnershipCode(String ownershipCode) {
		this.ownershipCode = ownershipCode;
	}



}
