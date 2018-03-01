/**
 * 
 */
package org.rta.core.entity.permit;

import java.sql.Timestamp;
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

import org.rta.core.entity.base.SerializableEntity;

/**
 * @author arun.verma
 *
 */

@Entity
@Table(name = "permit_susp_dtls")
public class PermitSuspDtlsEntity extends SerializableEntity {

    private static final long serialVersionUID = 7401663511341910718L;

    @Id
    @Column(name = "permit_susp_dtls_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permit_susp_dtls_seq")
    @SequenceGenerator(name = "permit_susp_dtls_seq", sequenceName = "permit_susp_dtls_seq", allocationSize = 1)
    private Long permitSuspDtlsId;
    
    @Column(name="appl_origination", length=80)
    private String applOrigination;

    @Column(name="application_id", length=80)
    private String applicationId;

    @Column(name="approved_auth", length=80)
    private String approvedAuth;

    @Column(name="cco_username", length=80)
    private String ccoUsername;

    @Column(name="created_by", length=80)
    private String createdBy;

    @Column(name="created_date")
    private Timestamp createdDate;

    @Column(name="module_cd", length=80)
    private String moduleCd;

    @Column(name="mvi_name", length=80)
    private String mviName;

    @Temporal(TemporalType.DATE)
    @Column(name="offmemo_dt")
    private Date offmemoDt;

    @Column(name="offmemo_no", length=80)
    private String offmemoNo;

    @Temporal(TemporalType.DATE)
    @Column(name="order_dt")
    private Date orderDt;

    @Column(name="order_no", length=80)
    private String orderNo;

    @Column(name="permit_no", length=80)
    private String permitNo;

    @Column(name="permit_seq_id")
    private Integer permitSeqId;

    @Temporal(TemporalType.DATE)
    @Column(name="planned_valid_from")
    private Date plannedValidFrom;

    @Temporal(TemporalType.DATE)
    @Column(name="planned_valid_to")
    private Date plannedValidTo;

    @Column(name="punishment_desc", length=200)
    private String punishmentDesc;

    @Column(name="reference_id", length=80)
    private String referenceId;

    @Temporal(TemporalType.DATE)
    @Column(name="revocation_dt")
    private Date revocationDt;

    @Column(name="revoke_reason", length=200)
    private String revokeReason;

    @Column(name="rta_office_code")
    private Integer rtaOfficeCode;

    @Column(name="sc_issued", length=1)
    private String scIssued;

    @Column(name="section_rule", length=80)
    private String sectionRule;

    @Column(name="status_code", length=1)
    private String statusCode;

    @Column(name="status_date")
    private Timestamp statusDate;

    @Column(name="status_remarks", length=200)
    private String statusRemarks;

    @Column(name="status_updated_by", length=80)
    private String statusUpdatedBy;

    @Column(name="status_updated_on")
    private Timestamp statusUpdatedOn;

    @Temporal(TemporalType.DATE)
    @Column(name="status_valid_from")
    private Date statusValidFrom;

    @Temporal(TemporalType.DATE)
    @Column(name="status_valid_to")
    private Date statusValidTo;

    @Column(name="support_ticket_remarks", length=80)
    private String supportTicketRemarks;

    @Column(name="sus_can", length=1)
    private String susCan;

    @Column(name="ticket_details", length=80)
    private String ticketDetails;

    @Column(name="updated_by", length=80)
    private String updatedBy;

    @Column(name="updated_date")
    private Timestamp updatedDate;

    @Column(name="vehicle_regd_no", length=80)
    private String vehicleRegdNo;

    public Long getPermitSuspDtlsId() {
        return permitSuspDtlsId;
    }

    public void setPermitSuspDtlsId(Long permitSuspDtlsId) {
        this.permitSuspDtlsId = permitSuspDtlsId;
    }

    public String getApplOrigination() {
        return this.applOrigination;
    }

    public void setApplOrigination(String applOrigination) {
        this.applOrigination = applOrigination;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApprovedAuth() {
        return this.approvedAuth;
    }

    public void setApprovedAuth(String approvedAuth) {
        this.approvedAuth = approvedAuth;
    }

    public String getCcoUsername() {
        return this.ccoUsername;
    }

    public void setCcoUsername(String ccoUsername) {
        this.ccoUsername = ccoUsername;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getModuleCd() {
        return this.moduleCd;
    }

    public void setModuleCd(String moduleCd) {
        this.moduleCd = moduleCd;
    }

    public String getMviName() {
        return this.mviName;
    }

    public void setMviName(String mviName) {
        this.mviName = mviName;
    }

    public Date getOffmemoDt() {
        return this.offmemoDt;
    }

    public void setOffmemoDt(Date offmemoDt) {
        this.offmemoDt = offmemoDt;
    }

    public String getOffmemoNo() {
        return this.offmemoNo;
    }

    public void setOffmemoNo(String offmemoNo) {
        this.offmemoNo = offmemoNo;
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

    public String getPermitNo() {
        return this.permitNo;
    }

    public void setPermitNo(String permitNo) {
        this.permitNo = permitNo;
    }

    public Integer getPermitSeqId() {
        return this.permitSeqId;
    }

    public void setPermitSeqId(Integer permitSeqId) {
        this.permitSeqId = permitSeqId;
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

    public String getReferenceId() {
        return this.referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Date getRevocationDt() {
        return this.revocationDt;
    }

    public void setRevocationDt(Date revocationDt) {
        this.revocationDt = revocationDt;
    }

    public String getRevokeReason() {
        return this.revokeReason;
    }

    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }

    public Integer getRtaOfficeCode() {
        return this.rtaOfficeCode;
    }

    public void setRtaOfficeCode(Integer rtaOfficeCode) {
        this.rtaOfficeCode = rtaOfficeCode;
    }

    public String getScIssued() {
        return this.scIssued;
    }

    public void setScIssued(String scIssued) {
        this.scIssued = scIssued;
    }

    public String getSectionRule() {
        return this.sectionRule;
    }

    public void setSectionRule(String sectionRule) {
        this.sectionRule = sectionRule;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Timestamp getStatusDate() {
        return this.statusDate;
    }

    public void setStatusDate(Timestamp statusDate) {
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

    public Timestamp getStatusUpdatedOn() {
        return this.statusUpdatedOn;
    }

    public void setStatusUpdatedOn(Timestamp statusUpdatedOn) {
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

    public String getSupportTicketRemarks() {
        return this.supportTicketRemarks;
    }

    public void setSupportTicketRemarks(String supportTicketRemarks) {
        this.supportTicketRemarks = supportTicketRemarks;
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

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getVehicleRegdNo() {
        return this.vehicleRegdNo;
    }

    public void setVehicleRegdNo(String vehicleRegdNo) {
        this.vehicleRegdNo = vehicleRegdNo;
    }

}
