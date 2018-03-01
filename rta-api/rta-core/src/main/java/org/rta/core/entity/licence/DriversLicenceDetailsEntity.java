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
 * The persistent class for the drivers_license_dtls database table.
 * 
 */
@Entity
@Table(name = "drivers_license_dtls")
public class DriversLicenceDetailsEntity extends BaseCommonEntity implements Serializable {

	private static final long serialVersionUID = -130642670105807312L;

	@EmbeddedId
	private DriversLicenseDtlPK id;

	@Column(name = "app_id")
	private String appId;

	@Column(name = "application_id")
	private String applicationId;

	// @Column(name="approved_ao")
	// private String approvedAo;
	//
	// @Column(name="approved_mvi")
	// private String approvedMvi;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ao_user_id", nullable = true)
	private UserEntity aoUserId;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "mvi_user_id", nullable = true)
	private UserEntity mviUserId;

	@Column(name = "dl_badge_no")
	private String dlBadgeNo;

	@Temporal(TemporalType.DATE)
	@Column(name = "badge_issued_date")
	private Date badgeIssuedDate;

	@Column(name = "badge_rta_office_code")
	private String badgeRtaOfficeCode;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_first_issue")
	private Date dateOfFirstIssue;

	@Temporal(TemporalType.DATE)
	@Column(name = "dl_issued_date")
	private Date dlIssuedDate;

	@Column(name = "dl_no")
	private String dlNo;

	@Column(name = "dl_type")
	private String dlType;

	@Column(name = "dl_vehicle_class_code")
	private String dlVehicleClassCode;

	@Column(name = "driving_school_license_no")
	private String drivingSchoolLicenseNo;

	@Column(name = "is_trained")
	private String isTrained;

	@Column(name = "llr_no")
	private String llrNo;

	@Column(name = "module_cd")
	private String moduleCd;

	private String observation;

	@Column(name = "photo_attachment_id")
	private Long photoAttachmentId;

	@Temporal(TemporalType.DATE)
	@Column(name = "planned_valid_from")
	private Date plannedValidFrom;

	@Temporal(TemporalType.DATE)
	@Column(name = "planned_valid_to")
	private Date plannedValidTo;

	@Column(name = "reference_id")
	private String referenceId;

	@Column(name = "renewal_flag")
	private String renewalFlag;

	@Column(name = "retest_flag")
	private String retestFlag;

	@Column(name = "retest_reason")
	private String retestReason;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rta_office_code")
	private RtaOfficeEntity rtaOfficeId;

	@Column(name = "sign_attachment_id")
	private Long signAttachmentId;

	@Column(name = "status_code")
	private String statusCode;

	@Temporal(TemporalType.DATE)
	@Column(name = "status_date")
	private Date statusDate;

	@Column(name = "status_remarks")
	private String statusRemarks;

	@Column(name = "status_updated_by")
	private String statusUpdatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "status_updated_on")
	private Date statusUpdatedOn;

	@Temporal(TemporalType.DATE)
	@Column(name = "status_valid_from")
	private Date statusValidFrom;

	@Temporal(TemporalType.DATE)
	@Column(name = "status_valid_to")
	private Date statusValidTo;

	@Temporal(TemporalType.DATE)
	@Column(name = "test_date")
	private Date testDate;

	@Column(name = "test_exempted")
	private String testExempted;

	@Column(name = "test_exempted_reason")
	private String testExemptedReason;

	@Column(name = "test_id")
	private String testId;

	@Column(name = "test_result")
	private String testResult;

	@Column(name = "ticket_details")
	private String ticketDetails;

	@Column(name = "valid_flg")
	private String validFlg;

	@Temporal(TemporalType.DATE)
	@Column(name = "valid_from")
	private Date validFrom;

	@Temporal(TemporalType.DATE)
	@Column(name = "valid_to")
	private Date validTo;

	public DriversLicenseDtlPK getId() {
		return id;
	}

	public void setId(DriversLicenseDtlPK id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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

	public String getDlBadgeNo() {
		return dlBadgeNo;
	}

	public void setDlBadgeNo(String dlBadgeNo) {
		this.dlBadgeNo = dlBadgeNo;
	}

	public Date getBadgeIssuedDate() {
		return badgeIssuedDate;
	}

	public void setBadgeIssuedDate(Date badgeIssuedDate) {
		this.badgeIssuedDate = badgeIssuedDate;
	}

	public String getBadgeRtaOfficeCode() {
		return badgeRtaOfficeCode;
	}

	public void setBadgeRtaOfficeCode(String badgeRtaOfficeCode) {
		this.badgeRtaOfficeCode = badgeRtaOfficeCode;
	}

	public Date getDateOfFirstIssue() {
		return dateOfFirstIssue;
	}

	public void setDateOfFirstIssue(Date dateOfFirstIssue) {
		this.dateOfFirstIssue = dateOfFirstIssue;
	}

	public Date getDlIssuedDate() {
		return dlIssuedDate;
	}

	public void setDlIssuedDate(Date dlIssuedDate) {
		this.dlIssuedDate = dlIssuedDate;
	}

	public String getDlNo() {
		return dlNo;
	}

	public void setDlNo(String dlNo) {
		this.dlNo = dlNo;
	}

	public String getDlType() {
		return dlType;
	}

	public void setDlType(String dlType) {
		this.dlType = dlType;
	}

	public String getDlVehicleClassCode() {
		return dlVehicleClassCode;
	}

	public void setDlVehicleClassCode(String dlVehicleClassCode) {
		this.dlVehicleClassCode = dlVehicleClassCode;
	}

	public String getDrivingSchoolLicenseNo() {
		return drivingSchoolLicenseNo;
	}

	public void setDrivingSchoolLicenseNo(String drivingSchoolLicenseNo) {
		this.drivingSchoolLicenseNo = drivingSchoolLicenseNo;
	}

	public String getIsTrained() {
		return isTrained;
	}

	public void setIsTrained(String isTrained) {
		this.isTrained = isTrained;
	}

	public String getLlrNo() {
		return llrNo;
	}

	public void setLlrNo(String llrNo) {
		this.llrNo = llrNo;
	}

	public String getModuleCd() {
		return moduleCd;
	}

	public void setModuleCd(String moduleCd) {
		this.moduleCd = moduleCd;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Long getPhotoAttachmentId() {
		return photoAttachmentId;
	}

	public void setPhotoAttachmentId(Long photoAttachmentId) {
		this.photoAttachmentId = photoAttachmentId;
	}

	public Date getPlannedValidFrom() {
		return plannedValidFrom;
	}

	public void setPlannedValidFrom(Date plannedValidFrom) {
		this.plannedValidFrom = plannedValidFrom;
	}

	public Date getPlannedValidTo() {
		return plannedValidTo;
	}

	public void setPlannedValidTo(Date plannedValidTo) {
		this.plannedValidTo = plannedValidTo;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getRenewalFlag() {
		return renewalFlag;
	}

	public void setRenewalFlag(String renewalFlag) {
		this.renewalFlag = renewalFlag;
	}

	public String getRetestFlag() {
		return retestFlag;
	}

	public void setRetestFlag(String retestFlag) {
		this.retestFlag = retestFlag;
	}

	public String getRetestReason() {
		return retestReason;
	}

	public void setRetestReason(String retestReason) {
		this.retestReason = retestReason;
	}

	public RtaOfficeEntity getRtaOfficeId() {
		return rtaOfficeId;
	}

	public void setRtaOfficeId(RtaOfficeEntity rtaOfficeId) {
		this.rtaOfficeId = rtaOfficeId;
	}

	public Long getSignAttachmentId() {
		return signAttachmentId;
	}

	public void setSignAttachmentId(Long signAttachmentId) {
		this.signAttachmentId = signAttachmentId;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public String getStatusRemarks() {
		return statusRemarks;
	}

	public void setStatusRemarks(String statusRemarks) {
		this.statusRemarks = statusRemarks;
	}

	public String getStatusUpdatedBy() {
		return statusUpdatedBy;
	}

	public void setStatusUpdatedBy(String statusUpdatedBy) {
		this.statusUpdatedBy = statusUpdatedBy;
	}

	public Date getStatusUpdatedOn() {
		return statusUpdatedOn;
	}

	public void setStatusUpdatedOn(Date statusUpdatedOn) {
		this.statusUpdatedOn = statusUpdatedOn;
	}

	public Date getStatusValidFrom() {
		return statusValidFrom;
	}

	public void setStatusValidFrom(Date statusValidFrom) {
		this.statusValidFrom = statusValidFrom;
	}

	public Date getStatusValidTo() {
		return statusValidTo;
	}

	public void setStatusValidTo(Date statusValidTo) {
		this.statusValidTo = statusValidTo;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public String getTestExempted() {
		return testExempted;
	}

	public void setTestExempted(String testExempted) {
		this.testExempted = testExempted;
	}

	public String getTestExemptedReason() {
		return testExemptedReason;
	}

	public void setTestExemptedReason(String testExemptedReason) {
		this.testExemptedReason = testExemptedReason;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getTicketDetails() {
		return ticketDetails;
	}

	public void setTicketDetails(String ticketDetails) {
		this.ticketDetails = ticketDetails;
	}

	public String getValidFlg() {
		return validFlg;
	}

	public void setValidFlg(String validFlg) {
		this.validFlg = validFlg;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

}