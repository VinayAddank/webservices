package org.rta.core.entity.certificate;

import java.sql.Timestamp;

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
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.SerializableEntity;
import org.rta.core.entity.master.PermitTypeEntity;

/**
 *	@Author sohan.maurya created on Nov 9, 2016.
 */
@Entity
@Table(name = "permit_vehicle_class_mapping")
public class PermitVehicleClassMappingEntity extends SerializableEntity {

    private static final long serialVersionUID = 4076891539649349488L;

    @Id
    @Column(name = "permit_vclass_mapping_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permit_vclass_mapping_seq")
    @SequenceGenerator(name = "permit_vclass_mapping_seq", sequenceName = "permit_vclass_mapping_seq", allocationSize = 1)
    private Long permitVClassMapId;
    
    @Column(name="created_by", length=80)
    private String createdBy;

    @Column(name="created_on")
    private Timestamp createdOn;

    @Column(name="is_temp_permit_allowed")
    private Boolean isTempPermitAllowed;

    @Column(name="modified_by", length=80)
    private String modifiedBy;

    @Column(name="modified_on")
    private Timestamp modifiedOn;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permit_type", referencedColumnName = "code")
    private PermitTypeEntity permitType;

    private Integer status;

    @Column(name="vehicle_class_code", length=80)
    private String vehicleClassCode;

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Long getPermitVClassMapId() {
        return permitVClassMapId;
    }

    public void setPermitVClassMapId(Long permitVClassMapId) {
        this.permitVClassMapId = permitVClassMapId;
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

    public Boolean getIsTempPermitAllowed() {
        return this.isTempPermitAllowed;
    }

    public void setIsTempPermitAllowed(Boolean isTempPermitAllowed) {
        this.isTempPermitAllowed = isTempPermitAllowed;
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

    public PermitTypeEntity getPermitType() {
        return permitType;
    }

    public void setPermitType(PermitTypeEntity permitType) {
        this.permitType = permitType;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVehicleClassCode() {
        return this.vehicleClassCode;
    }

    public void setVehicleClassCode(String vehicleClassCode) {
        this.vehicleClassCode = vehicleClassCode;
    }
}
