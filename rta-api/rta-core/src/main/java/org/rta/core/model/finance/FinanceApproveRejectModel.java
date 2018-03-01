package org.rta.core.model.finance;

import org.rta.core.enums.Status;

public class FinanceApproveRejectModel {
	
		private String vehicleRcId;
	    private Integer financerMode; 
	    private Long financerId; 
	    private Integer serviceType;
	    private String comment;
	    private String attachmentsURL;
	    private Status financeStatus;
	    private String firstApproverName;
	    private String firstApproverAadhar;
	    private String secondApproverName;
	    private String secondApproverAadhar;
	    private String aggrementURL;
	    Status status;
	    private String financerToken;
	    
	    
		public String getFinancerToken() {
			return financerToken;
		}
		public void setFinancerToken(String finnacerToken) {
			this.financerToken = finnacerToken;
		}
		public Status getStatus() {
			return status;
		}
		public void setStatus(Status status) {
			this.status = status;
		}
		public String getFirstApproverName() {
			return firstApproverName;
		}
		public void setFirstApproverName(String firstApproverName) {
			this.firstApproverName = firstApproverName;
		}
		public String getFirstApproverAadhar() {
			return firstApproverAadhar;
		}
		public void setFirstApproverAadhar(String firstApproverAadhar) {
			this.firstApproverAadhar = firstApproverAadhar;
		}
		public String getSecondApproverName() {
			return secondApproverName;
		}
		public void setSecondApproverName(String secondApproverName) {
			this.secondApproverName = secondApproverName;
		}
		public String getSecondApproverAadhar() {
			return secondApproverAadhar;
		}
		public void setSecondApproverAadhar(String secondApproverAadhar) {
			this.secondApproverAadhar = secondApproverAadhar;
		}
		public String getAggrementURL() {
			return aggrementURL;
		}
		public void setAggrementURL(String aggrementURL) {
			this.aggrementURL = aggrementURL;
		}
		public Status getFinanceStatus() {
			return financeStatus;
		}
		public void setFinanceStatus(Status financeStatus) {
			this.financeStatus = financeStatus;
		}
		public String getVehicleRcId() {
			return vehicleRcId;
		}
		public void setVehicleRcId(String vehicleRcId) {
			this.vehicleRcId = vehicleRcId;
		}
		public Integer getFinancerMode() {
			return financerMode;
		}
		public void setFinancerMode(Integer financerMode) {
			this.financerMode = financerMode;
		}
		public Long getFinancerId() {
			return financerId;
		}
		public void setFinancerId(Long financerId) {
			this.financerId = financerId;
		}
		public Integer getServiceType() {
			return serviceType;
		}
		public void setServiceType(Integer serviceType) {
			this.serviceType = serviceType;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public String getAttachmentsURL() {
			return attachmentsURL;
		}
		public void setAttachmentsURL(String attachmentsURL) {
			this.attachmentsURL = attachmentsURL;
		}
	    
	    

}
