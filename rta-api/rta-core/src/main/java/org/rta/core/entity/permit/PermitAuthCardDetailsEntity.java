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
@Table(name = "permit_auth_card_details")
public class PermitAuthCardDetailsEntity extends SerializableEntity implements Cloneable{

    private static final long serialVersionUID = 9214214264791056140L;

    @Id
    @Column(name = "permit_auth_card_details_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permit_auth_card_seq")
    @SequenceGenerator(name = "permit_auth_card_seq", sequenceName = "permit_auth_card_seq", allocationSize = 1)
    private Long permitAuthCardDetailsId;
    
    @Column(name="appl_origination", length=80)
    private String applOrigination;

    @Column(name="auth_card_id", length=80)
    private String authCardId;

    @Column(name="auth_card_issued_by", length=80)
    private String authCardIssuedBy;

    @Column(name="auth_card_remarks", length=255)
    private String authCardRemarks;

    @Column(name="auth_card_seq")
    private Integer authCardSeq;

    @Temporal(TemporalType.DATE)
    @Column(name="auth_card_vfdt")
    private Date authCardVfdt;

    @Temporal(TemporalType.DATE)
    @Column(name="auth_card_vtdt")
    private Date authCardVtdt;

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

    @Column(name="rta_office_id")
    private Long rtaOfficeId;

    @Column(name="status")
    private Integer status;

    @Column(name="support_ticket_remarks", length=80)
    private String supportTicketRemarks;

    public Long getPermitAuthCardDetailsId() {
        return permitAuthCardDetailsId;
    }

    public void setPermitAuthCardDetailsId(Long permitAuthCardDetailsId) {
        this.permitAuthCardDetailsId = permitAuthCardDetailsId;
    }

    public String getApplOrigination() {
        return this.applOrigination;
    }

    public void setApplOrigination(String applOrigination) {
        this.applOrigination = applOrigination;
    }

    public String getAuthCardId() {
        return this.authCardId;
    }

    public void setAuthCardId(String authCardId) {
        this.authCardId = authCardId;
    }

    public String getAuthCardIssuedBy() {
        return this.authCardIssuedBy;
    }

    public void setAuthCardIssuedBy(String authCardIssuedBy) {
        this.authCardIssuedBy = authCardIssuedBy;
    }

    public String getAuthCardRemarks() {
        return this.authCardRemarks;
    }

    public void setAuthCardRemarks(String authCardRemarks) {
        this.authCardRemarks = authCardRemarks;
    }

    public Integer getAuthCardSeq() {
        return this.authCardSeq;
    }

    public void setAuthCardSeq(Integer authCardSeq) {
        this.authCardSeq = authCardSeq;
    }

    public Date getAuthCardVfdt() {
        return this.authCardVfdt;
    }

    public void setAuthCardVfdt(Date authCardVfdt) {
        this.authCardVfdt = authCardVfdt;
    }

    public Date getAuthCardVtdt() {
        return this.authCardVtdt;
    }

    public void setAuthCardVtdt(Date authCardVtdt) {
        this.authCardVtdt = authCardVtdt;
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

    public Long getRtaOfficeId() {
        return this.rtaOfficeId;
    }

    public void setRtaOfficeId(Long rtaOfficeId) {
        this.rtaOfficeId = rtaOfficeId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSupportTicketRemarks() {
        return this.supportTicketRemarks;
    }

    public void setSupportTicketRemarks(String supportTicketRemarks) {
        this.supportTicketRemarks = supportTicketRemarks;
    }
    
    public Object cloneable() throws CloneNotSupportedException {
        return super.clone();
    }
}
