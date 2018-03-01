package org.rta.core.model.license;

import java.util.Date;


public class LicencePrintRequestModel {

	private Long printRequestId;
	private Date acknowledgeDate;
	private String applicationId;
	private Date dispatchDate;
	private String dispatchTrackingNo;
	private String dispatchedBy;
	private String duplicate;
	private Long licenceHolderId;
	private String permitType;
	private Date printDate;
	private String rcCardEmployeeId;
	private String reprintReason;
	private Date requestDate;
	private String requestedBy;
	private String status;
	public Long getPrintRequestId() {
		return printRequestId;
	}
	public void setPrintRequestId(Long printRequestId) {
		this.printRequestId = printRequestId;
	}
	public Date getAcknowledgeDate() {
		return acknowledgeDate;
	}
	public void setAcknowledgeDate(Date acknowledgeDate) {
		this.acknowledgeDate = acknowledgeDate;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public Date getDispatchDate() {
		return dispatchDate;
	}
	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	public String getDispatchTrackingNo() {
		return dispatchTrackingNo;
	}
	public void setDispatchTrackingNo(String dispatchTrackingNo) {
		this.dispatchTrackingNo = dispatchTrackingNo;
	}
	public String getDispatchedBy() {
		return dispatchedBy;
	}
	public void setDispatchedBy(String dispatchedBy) {
		this.dispatchedBy = dispatchedBy;
	}
	public String getDuplicate() {
		return duplicate;
	}
	public void setDuplicate(String duplicate) {
		this.duplicate = duplicate;
	}
	public Long getLicenceHolderId() {
		return licenceHolderId;
	}
	public void setLicenceHolderId(Long licenceHolderId) {
		this.licenceHolderId = licenceHolderId;
	}
	public String getPermitType() {
		return permitType;
	}
	public void setPermitType(String permitType) {
		this.permitType = permitType;
	}
	public Date getPrintDate() {
		return printDate;
	}
	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}
	public String getRcCardEmployeeId() {
		return rcCardEmployeeId;
	}
	public void setRcCardEmployeeId(String rcCardEmployeeId) {
		this.rcCardEmployeeId = rcCardEmployeeId;
	}
	public String getReprintReason() {
		return reprintReason;
	}
	public void setReprintReason(String reprintReason) {
		this.reprintReason = reprintReason;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}