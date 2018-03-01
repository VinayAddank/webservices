package org.rta.core.entity.licence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.rta.core.entity.base.BaseCommonEntity;



/**
 * The persistent class for the susp_drivers_license_dtls database table.
 * 
 */
@Entity
@Table(name="susp_drivers_license_dtls")
public class SuspDriversLicenseDetailEntity extends BaseCommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SuspDriversLicenseDtlPK id;

	@Column(name="application_id")
	private String applicationId;

	@Column(name="approved_ao")
	private String approvedAo;

	@Column(name="approved_mvi")
	private String approvedMvi;

	@Column(name="dl_no")
	private String dlNo;

	@Column(name="lic_status")
	private String licStatus;

	@Column(name="module_cd")
	private String moduleCd;

	@Column(name="office_memono")
	private String officeMemono;

	@Temporal(TemporalType.DATE)
	@Column(name="offmemo_dt")
	private Date offmemoDt;

	@Temporal(TemporalType.DATE)
	@Column(name="order_dt")
	private Date orderDt;

	@Column(name="order_no")
	private String orderNo;

	@Temporal(TemporalType.DATE)
	@Column(name="planned_valid_from")
	private Date plannedValidFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="planned_valid_to")
	private Date plannedValidTo;

	@Column(name="punishment_desc")
	private String punishmentDesc;

	@Temporal(TemporalType.DATE)
	@Column(name="reference_dt")
	private Date referenceDt;

	@Column(name="reference_id")
	private String referenceId;

	@Temporal(TemporalType.DATE)
	@Column(name="rev_dt")
	private Date revDt;

	private String revokereason;

	@Column(name="rta_office_code")
	private Long rtaOfficeCode;

	private String scissued;

	@Column(name="section_rule")
	private String sectionRule;

	private String source;

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

	@Column(name="sus_can")
	private String susCan;

	@Column(name="ticket_details")
	private String ticketDetails;

	public SuspDriversLicenseDtlPK getId() {
		return this.id;
	}

	public void setId(SuspDriversLicenseDtlPK id) {
		this.id = id;
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

	public String getDlNo() {
		return this.dlNo;
	}

	public void setDlNo(String dlNo) {
		this.dlNo = dlNo;
	}

	public String getLicStatus() {
		return this.licStatus;
	}

	public void setLicStatus(String licStatus) {
		this.licStatus = licStatus;
	}

	public String getModuleCd() {
		return this.moduleCd;
	}

	public void setModuleCd(String moduleCd) {
		this.moduleCd = moduleCd;
	}

	public String getOfficeMemono() {
		return this.officeMemono;
	}

	public void setOfficeMemono(String officeMemono) {
		this.officeMemono = officeMemono;
	}

	public Date getOffmemoDt() {
		return this.offmemoDt;
	}

	public void setOffmemoDt(Date offmemoDt) {
		this.offmemoDt = offmemoDt;
	}

	public Date getOrderDt() {
		return this.orderDt;
	}

	public void setOrderDt(Date orderDt) {
		this.orderDt = orderDt;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getPunishmentDesc() {
		return this.punishmentDesc;
	}

	public void setPunishmentDesc(String punishmentDesc) {
		this.punishmentDesc = punishmentDesc;
	}

	public Date getReferenceDt() {
		return this.referenceDt;
	}

	public void setReferenceDt(Date referenceDt) {
		this.referenceDt = referenceDt;
	}

	public String getReferenceId() {
		return this.referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public Date getRevDt() {
		return this.revDt;
	}

	public void setRevDt(Date revDt) {
		this.revDt = revDt;
	}

	public String getRevokereason() {
		return this.revokereason;
	}

	public void setRevokereason(String revokereason) {
		this.revokereason = revokereason;
	}

	public Long getRtaOfficeCode() {
		return this.rtaOfficeCode;
	}

	public void setRtaOfficeCode(Long rtaOfficeCode) {
		this.rtaOfficeCode = rtaOfficeCode;
	}

	public String getScissued() {
		return this.scissued;
	}

	public void setScissued(String scissued) {
		this.scissued = scissued;
	}

	public String getSectionRule() {
		return this.sectionRule;
	}

	public void setSectionRule(String sectionRule) {
		this.sectionRule = sectionRule;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public String getSusCan() {
		return this.susCan;
	}

	public void setSusCan(String susCan) {
		this.susCan = susCan;
	}

	public String getTicketDetails() {
		return this.ticketDetails;
	}

	public void setTicketDetails(String ticketDetails) {
		this.ticketDetails = ticketDetails;
	}

}