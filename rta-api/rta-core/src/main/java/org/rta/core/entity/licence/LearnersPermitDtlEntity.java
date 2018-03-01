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
import org.rta.core.entity.user.UserEntity;


/**
 * The persistent class for the learners_permit_dtls database table.
 * 
 */
@Entity
@Table(name="learners_permit_dtls")
public class LearnersPermitDtlEntity extends BaseCommonEntity implements Serializable {
	    
    private static final long serialVersionUID = -8430490709765159015L;

    @EmbeddedId
	private LearnersPermitDtlPK id;

	@Column(name="application_id")
	private String applicationId;

	@Column(name="application_origination")
	private String applicationOrigination;

//	@Column(name="approved_ao")
//	private String approvedAo;
//
//	@Column(name="approved_mvi")
//	private String approvedMvi;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ao_user_id", nullable = true)
    private UserEntity aoUserId;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "mvi_user_id", nullable = true)
    private UserEntity mviUserId;

	@Temporal(TemporalType.DATE)
	@Column(name="date_of_first_issue")
	private Date dateOfFirstIssue;

	@Temporal(TemporalType.DATE)
	@Column(name="llr_issuedt")
	private Date llrIssuedt;

	@Column(name="llr_no")
	private String llrNo;

	@Column(name="llr_no_type")
	private String llrNoType;

	@Column(name="parent_consent_aadhaar_no")
	private String parentConsentAadhaarNo;
	
	@Column(name="photo_attachment_id")
	private Long photoAttachmentId;

	@Column(name="reference_id")
	private String referenceId;

	@Column(name="retest_flag")
	private String retestFlag;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rta_office_code")
    private RtaOfficeEntity rtaOfficeId;

	@Column(name="sign_attachment_id")
	private Long signAttachmentId;

	@Column(name="status_code")
	private String statusCode;

	@Temporal(TemporalType.DATE)
	@Column(name="status_date")
	private Date statusDate;

	@Column(name="status_remarks")
	private String statusRemarks;

	@Column(name="status_updated_by")
	private String statusUpdatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="test_date")
	private Date testDate;

	@Column(name="test_exempted")
	private char testExempted;

	@Column(name="test_exempted_reason")
	private String testExemptedReason;

	@Column(name="test_id")
	private String testId;

	@Column(name="test_result")
	private String testResult;

	@Column(name="ticket_details")
	private String ticketDetails;

	@Temporal(TemporalType.DATE)
	@Column(name="valid_from")
	private Date validFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="valid_to")
	private Date validTo;

	@Column(name="medical_fitness_type")	
	private String medicalFitnessType;
	
	@Column(name="medical_practioner_code")
	private String medicalPractionerCode;
	
	public LearnersPermitDtlPK getId() {
		return id;
	}
	
	public void setId(LearnersPermitDtlPK id) {
		this.id = id;
	}

	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationOrigination() {
		return this.applicationOrigination;
	}

	public void setApplicationOrigination(String applicationOrigination) {
		this.applicationOrigination = applicationOrigination;
	}


	public Date getDateOfFirstIssue() {
		return this.dateOfFirstIssue;
	}

	public void setDateOfFirstIssue(Date dateOfFirstIssue) {
		this.dateOfFirstIssue = dateOfFirstIssue;
	}

	public Date getLlrIssuedt() {
		return this.llrIssuedt;
	}

	public void setLlrIssuedt(Date llrIssuedt) {
		this.llrIssuedt = llrIssuedt;
	}

	public String getLlrNo() {
		return this.llrNo;
	}

	public void setLlrNo(String llrNo) {
		this.llrNo = llrNo;
	}

	public String getLlrNoType() {
		return this.llrNoType;
	}

	public void setLlrNoType(String llrNoType) {
		this.llrNoType = llrNoType;
	}

	public String getParentConsentAadhaarNo() {
		return this.parentConsentAadhaarNo;
	}

	public void setParentConsentAadhaarNo(String parentConsentAadhaarNo) {
		this.parentConsentAadhaarNo = parentConsentAadhaarNo;
	}

	public Long getPhotoAttachmentId() {
		return this.photoAttachmentId;
	}

	public void setPhotoAttachmentId(Long photoAttachmentId) {
		this.photoAttachmentId = photoAttachmentId;
	}

	public String getReferenceId() {
		return this.referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getRetestFlag() {
		return this.retestFlag;
	}

	public void setRetestFlag(String retestFlag) {
		this.retestFlag = retestFlag;
	}

	public RtaOfficeEntity getRtaOfficeId() {
		return rtaOfficeId;
	}

	public void setRtaOfficeId(RtaOfficeEntity rtaOfficeId) {
		this.rtaOfficeId = rtaOfficeId;
	}

	public Long getSignAttachmentId() {
		return this.signAttachmentId;
	}

	public void setSignAttachmentId(Long signAttachmentId) {
		this.signAttachmentId = signAttachmentId;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Date getStatusDate() {
		return this.statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public String getStatusRemarks() {
		return this.statusRemarks;
	}

	public void setStatusRemarks(String statusRemarks) {
		this.statusRemarks = statusRemarks;
	}

	public String getStatusUpdatedBy() {
		return this.statusUpdatedBy;
	}

	public void setStatusUpdatedBy(String statusUpdatedBy) {
		this.statusUpdatedBy = statusUpdatedBy;
	}

	public Date getTestDate() {
		return this.testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public char getTestExempted() {
		return this.testExempted;
	}

	public void setTestExempted(char testExempted) {
		this.testExempted = testExempted;
	}

	public String getTestExemptedReason() {
		return this.testExemptedReason;
	}

	public void setTestExemptedReason(String testExemptedReason) {
		this.testExemptedReason = testExemptedReason;
	}

	public String getTestId() {
		return this.testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getTestResult() {
		return this.testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
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

	public String getMedicalFitnessType() {
		return medicalFitnessType;
	}

	public void setMedicalFitnessType(String medicalFitnessType) {
		this.medicalFitnessType = medicalFitnessType;
	}

	public String getMedicalPractionerCode() {
		return medicalPractionerCode;
	}

	public void setMedicalPractionerCode(String medicalPractionerCode) {
		this.medicalPractionerCode = medicalPractionerCode;
	}

	public UserEntity getAoUserId() {
		return aoUserId;
	}

	public void setAoUserId(UserEntity aoUserId) {
		this.aoUserId = aoUserId;
	}

	public UserEntity getMviUserId() {
		return mviUserId;
	}

	public void setMviUserId(UserEntity mviUserId) {
		this.mviUserId = mviUserId;
	}
	
}