package org.rta.core.model.finance;

import org.rta.core.model.user.AddressModel;

public class FinanceBranchModel {
	private Long branchId;
	private Long headFinancerId;
	private String branchName;
	private Integer activeStatus;
	AddressModel branchAddress;
	
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public Long getHeadFinancerId() {
		return headFinancerId;
	}
	public void setHeadFinancerId(Long headFinancerId) {
		this.headFinancerId = headFinancerId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Integer getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}
	public AddressModel getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(AddressModel branchAddress) {
		this.branchAddress = branchAddress;
	}
	
	
}
