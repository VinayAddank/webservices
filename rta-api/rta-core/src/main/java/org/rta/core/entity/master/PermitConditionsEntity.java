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

@Entity
@Table(name = "permit_conditions_master")
public class PermitConditionsEntity extends SerializableEntity {

    private static final long serialVersionUID = 2125963970433137917L;
    
    @Id
    @Column(name="permit_cond_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permit_conditions_seq")
    @SequenceGenerator(name = "permit_conditions_seq", sequenceName = "permit_conditions_seq", allocationSize = 1)
    private Integer permitCondId;
    
    @Column(name="created_by", length=80)
    private String createdBy;

    @Column(name="created_on")
    private Timestamp createdOn;

    @Column(name="modified_by", length=80)
    private String modifiedBy;

    @Column(name="modified_on")
    private Timestamp modifiedOn;

    @Column(name="permit_cond_desc", length=800)
    private String permitCondDesc;

    @Column(name="permit_type" , length = 50)
    private String permitType;

    @Column(name="rta_office_id")
    private Integer rtaOfficeId;

    @Column(name="status_code")
    private Integer statusCode;

    @Column(name="vehicle_class_Code", length=80)
    private String vehicleClassCode;
    
    @Column(name = "code", length=50)
    private String code;

    @Column(name = "temp_permit_type_code", length = 50)
    private String tempPermitTypeCode;
    
    public Integer getPermitCondId() {
        return permitCondId;
    }

    public void setPermitCondId(Integer permitCondId) {
        this.permitCondId = permitCondId;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getPermitCondDesc() {
        return permitCondDesc;
    }

    public void setPermitCondDesc(String permitCondDesc) {
        this.permitCondDesc = permitCondDesc;
    }

    public String getPermitType() {
        return permitType;
    }

    public void setPermitType(String permitType) {
        this.permitType = permitType;
    }

    public Integer getRtaOfficeId() {
        return rtaOfficeId;
    }

    public void setRtaOfficeId(Integer rtaOfficeId) {
        this.rtaOfficeId = rtaOfficeId;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getVehicleClassCode() {
        return vehicleClassCode;
    }

    public void setVehicleClassCode(String vehicleClassCode) {
        this.vehicleClassCode = vehicleClassCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTempPermitTypeId() {
        return tempPermitTypeCode;
    }

    public void setTempPermitTypeId(String tempPermitTypeCode) {
        this.tempPermitTypeCode = tempPermitTypeCode;
    }

}
