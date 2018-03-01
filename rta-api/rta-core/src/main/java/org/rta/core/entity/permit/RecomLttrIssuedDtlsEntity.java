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
@Table(name = "recom_lttr_issued_dtls")
public class RecomLttrIssuedDtlsEntity extends SerializableEntity {

    private static final long serialVersionUID = -7660724991936019241L;

    @Id
    @Column(name = "recom_lttr_issued_dtls_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recom_lttr_issued_dtls_seq")
    @SequenceGenerator(name = "recom_lttr_issued_dtls_seq", sequenceName = "recom_lttr_issued_dtls_seq",
            allocationSize = 1)
    private String recomLttrIssuedDtlsId;
    
    @Column(name="appl_origination", length=80)
    private String applOrigination;

    @Column(name="created_by", length=80)
    private String createdBy;

    @Column(name="created_on")
    private Timestamp createdOn;

    @Column(name="modified_by", length=80)
    private String modifiedBy;

    @Column(name="modified_on")
    private Timestamp modifiedOn;

    @Column(name="permit_no", length=80)
    private String permitNo;

    @Column(name="permit_sequence_id")
    private Integer permitSequenceId;

    @Column(name="recom_issu_plc", length=80)
    private String recomIssuPlc;

    @Column(name="recom_issued_by", length=80)
    private String recomIssuedBy;

    @Temporal(TemporalType.DATE)
    @Column(name="recom_lttr_dt")
    private Date recomLttrDt;

    @Column(name="recom_lttr_id", length=80)
    private String recomLttrId;

    @Temporal(TemporalType.DATE)
    @Column(name="recom_lttr_vfdt")
    private Date recomLttrVfdt;

    @Temporal(TemporalType.DATE)
    @Column(name="recom_lttr_vtdt")
    private Date recomLttrVtdt;

    @Column(name="recom_state", length=80)
    private String recomState;

    @Column(length=80)
    private String remarks;

    @Column(name="status_code")
    private Integer statusCode;

    @Column(name="support_ticket_remarks", length=80)
    private String supportTicketRemarks;

    public String getRecomLttrIssuedDtlsId() {
        return recomLttrIssuedDtlsId;
    }

    public void setRecomLttrIssuedDtlsId(String recomLttrIssuedDtlsId) {
        this.recomLttrIssuedDtlsId = recomLttrIssuedDtlsId;
    }

    public String getApplOrigination() {
        return this.applOrigination;
    }

    public void setApplOrigination(String applOrigination) {
        this.applOrigination = applOrigination;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedOn() {
        return this.modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getPermitNo() {
        return this.permitNo;
    }

    public void setPermitNo(String permitNo) {
        this.permitNo = permitNo;
    }

    public Integer getPermitSequenceId() {
        return this.permitSequenceId;
    }

    public void setPermitSequenceId(Integer permitSequenceId) {
        this.permitSequenceId = permitSequenceId;
    }

    public String getRecomIssuPlc() {
        return this.recomIssuPlc;
    }

    public void setRecomIssuPlc(String recomIssuPlc) {
        this.recomIssuPlc = recomIssuPlc;
    }

    public String getRecomIssuedBy() {
        return this.recomIssuedBy;
    }

    public void setRecomIssuedBy(String recomIssuedBy) {
        this.recomIssuedBy = recomIssuedBy;
    }

    public Date getRecomLttrDt() {
        return this.recomLttrDt;
    }

    public void setRecomLttrDt(Date recomLttrDt) {
        this.recomLttrDt = recomLttrDt;
    }

    public String getRecomLttrId() {
        return this.recomLttrId;
    }

    public void setRecomLttrId(String recomLttrId) {
        this.recomLttrId = recomLttrId;
    }

    public Date getRecomLttrVfdt() {
        return this.recomLttrVfdt;
    }

    public void setRecomLttrVfdt(Date recomLttrVfdt) {
        this.recomLttrVfdt = recomLttrVfdt;
    }

    public Date getRecomLttrVtdt() {
        return this.recomLttrVtdt;
    }

    public void setRecomLttrVtdt(Date recomLttrVtdt) {
        this.recomLttrVtdt = recomLttrVtdt;
    }

    public String getRecomState() {
        return this.recomState;
    }

    public void setRecomState(String recomState) {
        this.recomState = recomState;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getSupportTicketRemarks() {
        return this.supportTicketRemarks;
    }

    public void setSupportTicketRemarks(String supportTicketRemarks) {
        this.supportTicketRemarks = supportTicketRemarks;
    }
}
