/**
 * 
 */
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
 * @author arun.verma
 *
 */
@Entity
@Table(name = "permit_route_conditions_master")
public class PermitRouteConditionsMasterEntity extends SerializableEntity {

    private static final long serialVersionUID = 8039389526232076557L;

    @Id
    @Column(name = "permit_route_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permit_route_master_seq")
    @SequenceGenerator(name = "permit_route_master_seq", sequenceName = "permit_route_master_seq", allocationSize = 1)
    private Long permitRouteId;
    @Column(name = "code")
    private String code;
    @Column(name = "per_route_desc", length = 220)
    private String perRouteDesc;
    @Column(name = "permit_type")
    private String permitType;
    @Column(name = "vehicle_class_Code", length = 80)
    private String vehicleClassCode;
    @Column(name = "remarks", length = 80)
    private String remarks;
    @Column(name = "status")
    private Boolean status;
    
    @Column(name = "temp_permit_type_Code", length = 50)
    private String tempPermitTypeCode;
    @Column(name="created_by", length=80)
    private String createdBy;
    @Column(name="created_on")
    private Timestamp createdOn;
    @Column(name="modified_by", length=80)
    private String modifiedBy;
    @Column(name="modified_on")
    private Timestamp modifiedOn;
    
    public Long getPermitRouteId() {
        return permitRouteId;
    }

    public void setPermitRouteId(Long permitRouteId) {
        this.permitRouteId = permitRouteId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPerRouteDesc() {
        return perRouteDesc;
    }

    public void setPerRouteDesc(String perRouteDesc) {
        this.perRouteDesc = perRouteDesc;
    }

    public String getPermitType() {
        return permitType;
    }

    public void setPermitType(String permitType) {
        this.permitType = permitType;
    }

    public String getVehicleClassCode() {
        return vehicleClassCode;
    }

    public void setVehicleClassCode(String vehicleClassCode) {
        this.vehicleClassCode = vehicleClassCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTempPermitTypeCode() {
        return tempPermitTypeCode;
    }

    public void setTempPermitTypeCode(String tempPermitTypeCode) {
        this.tempPermitTypeCode = tempPermitTypeCode;
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
}
