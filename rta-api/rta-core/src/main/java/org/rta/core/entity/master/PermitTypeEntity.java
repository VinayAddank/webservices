package org.rta.core.entity.master;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.SerializableEntity;

/**
 *	@Author sohan.maurya created on Nov 7, 2016.
 */
@Entity
@Table(name = "permit_type_master")
public class PermitTypeEntity extends SerializableEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permit_type_sequence")
    @SequenceGenerator(name = "permit_type_sequence", sequenceName = "permit_type_sequence", allocationSize = 1)
    @Column(name="permit_type_id")
    private Integer permitTypeId;

    @Column(name="auth_card_req")
    private Boolean authCardReq;

    @Column(name="created_by", length=80)
    private String createdBy;

    @Column(name="created_on")
    private Timestamp createdOn;

    @Column(name="is_temp_permit")
    private Boolean isTempPermit;

    @Column(name="modified_by", length=80)
    private String modifiedBy;

    @Column(name="modified_on")
    private Timestamp modifiedOn;

    @Column(name="code", length=80)
    private String code;

    @Column(name="name", length=255)
    private String name;

    @Column(name="status")
    private Boolean status;

    @Column(name="validity_period")
    private Integer validityPeriod;

    public Boolean getAuthCardReq() {
        return this.authCardReq;
    }

    public void setAuthCardReq(Boolean authCardReq) {
        this.authCardReq = authCardReq;
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

    public Boolean getIsTempPermit() {
        return this.isTempPermit;
    }

    public void setIsTempPermit(Boolean isTempPermit) {
        this.isTempPermit = isTempPermit;
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPermitTypeId() {
        return this.permitTypeId;
    }

    public void setPermitTypeId(Integer permitTypeId) {
        this.permitTypeId = permitTypeId;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getValidityPeriod() {
        return this.validityPeriod;
    }

    public void setValidityPeriod(Integer validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
}
