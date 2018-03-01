package org.rta.core.model.finance;

import org.rta.core.model.user.AddressModel;

public class FinancerModel {
	private String financerName;
    private Long financerId;
    private String status;
    private AddressModel financierAddress;
    private String sanctionLetterUrl;
  
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSanctionLetterUrl() {
		return sanctionLetterUrl;
	}
	public void setSanctionLetterUrl(String sanctionLetterUrl) {
		this.sanctionLetterUrl = sanctionLetterUrl;
	}
	public AddressModel getFinancierAddress() {
		return financierAddress;
	}
	public void setFinancierAddress(AddressModel financierAddress) {
		this.financierAddress = financierAddress;
	}
	public String getFinancerName() {
		return financerName;
	}
	public void setFinancerName(String financerName) {
		this.financerName = financerName;
	}

    public Long getFinancerId() {
		return financerId;
	}

    public void setFinancerId(Long financerId) {
		this.financerId = financerId;
	}
	

}
