package org.rta.core.entity.licence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.rta.core.entity.base.BaseCommonEntity;



/**
 * The persistent class for the licence_print_request database table.
 * 
 */
@Entity
@Table(name="licence_print_request")
public class LicencePrintRequestEntity extends BaseCommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="print_request_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "print_seq")
    @SequenceGenerator(name = "print_seq", sequenceName = "print_seq", allocationSize = 1)
	private Long printRequestId;

	@Temporal(TemporalType.DATE)
	@Column(name="acknowledge_date")
	private Date acknowledgeDate;

	@Column(name="application_id")
	private String applicationId;

	@Temporal(TemporalType.DATE)
	@Column(name="dispatch_date")
	private Date dispatchDate;

	@Column(name="dispatch_tracking_no")
	private String dispatchTrackingNo;

	@Column(name="dispatched_by")
	private String dispatchedBy;

	private String duplicate;

	@Column(name="licence_holder_id")
	private Long licenceHolderId;

	@Column(name="permit_type")
	private String permitType;

	@Temporal(TemporalType.DATE)
	@Column(name="print_date")
	private Date printDate;

	@Column(name="rc_card_employee_id")
	private String rcCardEmployeeId;

	@Column(name="reprint_reason")
	private String reprintReason;

	@Temporal(TemporalType.DATE)
	@Column(name="request_date")
	private Date requestDate;

	@Column(name="requested_by")
	private String requestedBy;

	private String status;

	public Long getPrintRequestId() {
		return this.printRequestId;
	}

	public void setPrintRequestId(Long printRequestId) {
		this.printRequestId = printRequestId;
	}

	public Date getAcknowledgeDate() {
		return this.acknowledgeDate;
	}

	public void setAcknowledgeDate(Date acknowledgeDate) {
		this.acknowledgeDate = acknowledgeDate;
	}

	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public Date getDispatchDate() {
		return this.dispatchDate;
	}

	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}

	public String getDispatchTrackingNo() {
		return this.dispatchTrackingNo;
	}

	public void setDispatchTrackingNo(String dispatchTrackingNo) {
		this.dispatchTrackingNo = dispatchTrackingNo;
	}

	public String getDispatchedBy() {
		return this.dispatchedBy;
	}

	public void setDispatchedBy(String dispatchedBy) {
		this.dispatchedBy = dispatchedBy;
	}

	public String getDuplicate() {
		return this.duplicate;
	}

	public void setDuplicate(String duplicate) {
		this.duplicate = duplicate;
	}

	public Long getLicenceHolderId() {
		return this.licenceHolderId;
	}

	public void setLicenceHolderId(Long licenceHolderId) {
		this.licenceHolderId = licenceHolderId;
	}

	public String getPermitType() {
		return this.permitType;
	}

	public void setPermitType(String permitType) {
		this.permitType = permitType;
	}

	public Date getPrintDate() {
		return this.printDate;
	}

	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	public String getRcCardEmployeeId() {
		return this.rcCardEmployeeId;
	}

	public void setRcCardEmployeeId(String rcCardEmployeeId) {
		this.rcCardEmployeeId = rcCardEmployeeId;
	}

	public String getReprintReason() {
		return this.reprintReason;
	}

	public void setReprintReason(String reprintReason) {
		this.reprintReason = reprintReason;
	}

	public Date getRequestDate() {
		return this.requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getRequestedBy() {
		return this.requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}