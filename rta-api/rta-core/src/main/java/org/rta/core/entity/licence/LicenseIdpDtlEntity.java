package org.rta.core.entity.licence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.rta.core.entity.base.BaseCommonEntity;
import org.rta.core.entity.office.RtaOfficeEntity;


/**
 * The persistent class for the license_idp_dtls database table.
 * 
 */
@Entity
@Table(name="license_idp_dtls")
public class LicenseIdpDtlEntity extends BaseCommonEntity implements Serializable {
	
    private static final long serialVersionUID = -6594061365107647396L;

    @EmbeddedId
	private LicenseIdpDtlPK id;

	@Column(name="application_no")
	private String applicationNo;

	@Column(name="approved_by")
	private String approvedBy;

	@Column(name="idp_license_no")
	private String idpLicenseNo;

	@Column(name="idp_vehicle_class_code")
	private String idpVehicleClassCode;

	@Temporal(TemporalType.DATE)
	@Column(name="issue_date")
	private Date issueDate;

	@Column(name="passport_no")
	private String passportNo;

	@Temporal(TemporalType.DATE)
	@Column(name="passport_valid_to")
	private Date passportValidTo;

	@Column(name="photo_attachment_id")
	private Long photoAttachmentId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rta_office_code")
	private RtaOfficeEntity rtaOfficeId;

	private Integer status;

	@Column(name="status_remarks")
	private String statusRemarks;

	@Column(name="ticket_details")
	private String ticketDetails;

	@Temporal(TemporalType.DATE)
	@Column(name="valid_from")
	private Date validFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="valid_to")
	private Date validTo;

	public LicenseIdpDtlPK getId() {
		return this.id;
	}

	public void setId(LicenseIdpDtlPK id) {
		this.id = id;
	}

	public String getApplicationNo() {
		return this.applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getIdpLicenseNo() {
		return this.idpLicenseNo;
	}

	public void setIdpLicenseNo(String idpLicenseNo) {
		this.idpLicenseNo = idpLicenseNo;
	}

	public String getIdpVehicleClassCode() {
		return this.idpVehicleClassCode;
	}

	public void setIdpVehicleClassCode(String idpVehicleClassCode) {
		this.idpVehicleClassCode = idpVehicleClassCode;
	}

	public Date getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getPassportNo() {
		return this.passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public Date getPassportValidTo() {
		return this.passportValidTo;
	}

	public void setPassportValidTo(Date passportValidTo) {
		this.passportValidTo = passportValidTo;
	}

	public Long getPhotoAttachmentId() {
		return this.photoAttachmentId;
	}

	public void setPhotoAttachmentId(Long photoAttachmentId) {
		this.photoAttachmentId = photoAttachmentId;
	}

    public RtaOfficeEntity getRtaOfficeId() {
		return rtaOfficeId;
	}

	public void setRtaOfficeId(RtaOfficeEntity rtaOfficeId) {
		this.rtaOfficeId = rtaOfficeId;
	}

	public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getStatusRemarks() {
		return this.statusRemarks;
	}

	public void setStatusRemarks(String statusRemarks) {
		this.statusRemarks = statusRemarks;
	}

	public String getTicketDetails() {
		return this.ticketDetails;
	}

	public void setTicketDetails(String ticketDetails) {
		this.ticketDetails = ticketDetails;
	}

	public Date getValidFrom() {
		return this.validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return this.validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

}