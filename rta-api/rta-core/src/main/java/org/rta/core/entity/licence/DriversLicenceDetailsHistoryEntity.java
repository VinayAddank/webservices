package org.rta.core.entity.licence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.rta.core.entity.base.BaseCommonEntity;
import org.rta.core.entity.office.RtaOfficeEntity;


/**
 * The persistent class for the drivers_license_dtls database table.
 * 
 */
@Entity
@Table(name="drivers_license_dtls_history")
public class DriversLicenceDetailsHistoryEntity extends BaseCommonEntity implements Serializable {

    private static final long serialVersionUID = -130642670105807312L;

    @Id
    @Column(name = "drivers_license_hist_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drivers_license_hist_seq")
    @SequenceGenerator(name = "drivers_license_hist_seq", sequenceName = "drivers_license_hist_seq", allocationSize = 1)
    private Long driversLicenseHistId;
    
    @Column(name="licence_holder_id")
	private Long licenceHolderId;

	@Column(name="dl_sequence_id")
	private Integer dlSequenceId;

	@Column(name="app_id")
	private String appId;

	@Column(name="application_id")
	private String applicationId;

	@Column(name="approved_ao")
	private String approvedAo;

	@Column(name="approved_mvi")
	private String approvedMvi;

	@Temporal(TemporalType.DATE)
	@Column(name="badge_issued_date")
	private Date badgeIssuedDate;

	@Column(name="badge_rta_office_code")
	private String badgeRtaOfficeCode;

	@Temporal(TemporalType.DATE)
	@Column(name="date_of_first_issue")
	private Date dateOfFirstIssue;

	@Temporal(TemporalType.DATE)
	@Column(name="dl_issued_date")
	private Date dlIssuedDate;

	@Column(name="dl_no")
	private String dlNo;

	@Column(name="dl_type")
	private String dlType;

	@Column(name="dl_vehicle_class_code")
	private String dlVehicleClassCode;

	@Column(name="driving_school_license_no")
	private String drivingSchoolLicenseNo;

	@Column(name="is_trained")
	private String isTrained;

	@Column(name="llr_no")
	private String llrNo;

	@Column(name="module_cd")
	private String moduleCd;

	private String observation;

	@Column(name="photo_attachment_id")
	private Long photoAttachmentId;

	@Temporal(TemporalType.DATE)
	@Column(name="planned_valid_from")
	private Date plannedValidFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="planned_valid_to")
	private Date plannedValidTo;

	@Column(name="reference_id")
	private String referenceId;

	@Column(name="renewal_flag")
	private String renewalFlag;

	@Column(name="retest_flag")
	private String retestFlag;

	@Column(name="retest_reason")
	private String retestReason;

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
	@Column(name="status_updated_on")
	private Date statusUpdatedOn;

	@Temporal(TemporalType.DATE)
	@Column(name="status_valid_from")
	private Date statusValidFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="status_valid_to")
	private Date statusValidTo;

	@Temporal(TemporalType.DATE)
	@Column(name="test_date")
	private Date testDate;

	@Column(name="test_exempted")
	private String testExempted;

	@Column(name="test_exempted_reason")
	private String testExemptedReason;

	@Column(name="test_id")
	private String testId;

	@Column(name="test_result")
	private String testResult;

	@Column(name="ticket_details")
	private String ticketDetails;

	@Column(name="valid_flg")
	private String validFlg;

	@Temporal(TemporalType.DATE)
	@Column(name="valid_from")
	private Date validFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="valid_to")
	private Date validTo;

	@Column(name="service_code")
	private String serviceCode;

	
	public Long getDriversLicenseHistId() {
		return driversLicenseHistId;
	}

	public void setDriversLicenseHistId(Long driversLicenseHistId) {
		this.driversLicenseHistId = driversLicenseHistId;
	}

	public Long getLicenceHolderId() {
		return licenceHolderId;
	}

	public void setLicenceHolderId(Long licenceHolderId) {
		this.licenceHolderId = licenceHolderId;
	}

	public Integer getDlSequenceId() {
		return dlSequenceId;
	}

	public void setDlSequenceId(Integer dlSequenceId) {
		this.dlSequenceId = dlSequenceId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApprovedAo() {
		return this.approvedAo;
	}

	public void setApprovedAo(String approvedAo) {
		this.approvedAo = approvedAo;
	}

	public String getApprovedMvi() {
		return this.approvedMvi;
	}

	public void setApprovedMvi(String approvedMvi) {
		this.approvedMvi = approvedMvi;
	}

	public Date getBadgeIssuedDate() {
		return this.badgeIssuedDate;
	}

	public void setBadgeIssuedDate(Date badgeIssuedDate) {
		this.badgeIssuedDate = badgeIssuedDate;
	}

	public String getBadgeRtaOfficeCode() {
		return this.badgeRtaOfficeCode;
	}

	public void setBadgeRtaOfficeCode(String badgeRtaOfficeCode) {
		this.badgeRtaOfficeCode = badgeRtaOfficeCode;
	}

	public Date getDateOfFirstIssue() {
		return this.dateOfFirstIssue;
	}

	public void setDateOfFirstIssue(Date dateOfFirstIssue) {
		this.dateOfFirstIssue = dateOfFirstIssue;
	}

	public Date getDlIssuedDate() {
		return this.dlIssuedDate;
	}

	public void setDlIssuedDate(Date dlIssuedDate) {
		this.dlIssuedDate = dlIssuedDate;
	}

	public String getDlNo() {
		return this.dlNo;
	}

	public void setDlNo(String dlNo) {
		this.dlNo = dlNo;
	}

	public String getDlType() {
		return this.dlType;
	}

	public void setDlType(String dlType) {
		this.dlType = dlType;
	}

	public String getDlVehicleClassCode() {
		return this.dlVehicleClassCode;
	}

	public void setDlVehicleClassCode(String dlVehicleClassCode) {
		this.dlVehicleClassCode = dlVehicleClassCode;
	}

	public String getDrivingSchoolLicenseNo() {
		return this.drivingSchoolLicenseNo;
	}

	public void setDrivingSchoolLicenseNo(String drivingSchoolLicenseNo) {
		this.drivingSchoolLicenseNo = drivingSchoolLicenseNo;
	}

	public String getIsTrained() {
		return this.isTrained;
	}

	public void setIsTrained(String isTrained) {
		this.isTrained = isTrained;
	}

	public String getLlrNo() {
		return this.llrNo;
	}

	public void setLlrNo(String llrNo) {
		this.llrNo = llrNo;
	}

	public String getModuleCd() {
		return this.moduleCd;
	}

	public void setModuleCd(String moduleCd) {
		this.moduleCd = moduleCd;
	}

	public String getObservation() {
		return this.observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Long getPhotoAttachmentId() {
		return this.photoAttachmentId;
	}

	public void setPhotoAttachmentId(Long photoAttachmentId) {
		this.photoAttachmentId = photoAttachmentId;
	}

	public Date getPlannedValidFrom() {
		return this.plannedValidFrom;
	}

	public void setPlannedValidFrom(Date plannedValidFrom) {
		this.plannedValidFrom = plannedValidFrom;
	}

	public Date getPlannedValidTo() {
		return this.plannedValidTo;
	}

	public void setPlannedValidTo(Date plannedValidTo) {
		this.plannedValidTo = plannedValidTo;
	}

	public String getReferenceId() {
		return this.referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getRenewalFlag() {
		return this.renewalFlag;
	}

	public void setRenewalFlag(String renewalFlag) {
		this.renewalFlag = renewalFlag;
	}

	public String getRetestFlag() {
		return this.retestFlag;
	}

	public void setRetestFlag(String retestFlag) {
		this.retestFlag = retestFlag;
	}

	public String getRetestReason() {
		return this.retestReason;
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

	public Date getStatusUpdatedOn() {
		return this.statusUpdatedOn;
	}

	public void setStatusUpdatedOn(Date statusUpdatedOn) {
		this.statusUpdatedOn = statusUpdatedOn;
	}

	public Date getStatusValidFrom() {
		return this.statusValidFrom;
	}

	public void setStatusValidFrom(Date statusValidFrom) {
		this.statusValidFrom = statusValidFrom;
	}

	public Date getStatusValidTo() {
		return this.statusValidTo;
	}

	public void setStatusValidTo(Date statusValidTo) {
		this.statusValidTo = statusValidTo;
	}

	public Date getTestDate() {
		return this.testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public String getTestExempted() {
		return this.testExempted;
	}

	public void setTestExempted(String testExempted) {
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

	public String getValidFlg() {
		return this.validFlg;
	}

	public void setValidFlg(String validFlg) {
		this.validFlg = validFlg;
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