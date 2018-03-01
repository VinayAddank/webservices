/**
 * 
 */
package org.rta.core.entity.master;

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
@Table(name = "permit_goods_master")
public class PermitGoodsMasterEntity extends SerializableEntity {

    private static final long serialVersionUID = -6347155668540244942L;

    @Id
    @Column(name="permit_goods_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permit_goods_master_seq")
    @SequenceGenerator(name = "permit_goods_master_seq", sequenceName = "permit_goods_master_seq", allocationSize = 1)
    private Integer permitGoodsId;
    
    @Column(name="created_by", length=80)
    private String createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name="created_on")
    private Date createdOn;

    @Column(name="modified_by", length=80)
    private String modifiedBy;

    @Temporal(TemporalType.DATE)
    @Column(name="modified_on")
    private Date modifiedOn;

    @Column(name="per_goods_desc", length=80)
    private String perGoodsDesc;

    @Column(name="permit_type")
    private String permitType;

    @Column(length=80)
    private String remarks;

    @Column(name = "status")
    private Boolean status;
    
    @Column(name = "temp_permit_type_code", length = 50)
    private String tempPermitTypeCode;

    @Column(name="vehicle_class_code", length=80)
    private String vehicleClassCode;
    
    @Column(name = "code")
    private String code;

    public Integer getPermitGoodsId() {
        return permitGoodsId;
    }

    public void setPermitGoodsId(Integer permitGoodsId) {
        this.permitGoodsId = permitGoodsId;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getPerGoodsDesc() {
        return perGoodsDesc;
    }

    public void setPerGoodsDesc(String perGoodsDesc) {
        this.perGoodsDesc = perGoodsDesc;
    }

    public String getPermitType() {
        return permitType;
    }

    public void setPermitType(String permitType) {
        this.permitType = permitType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
    
    public String getTempPermitTypeCode() {
        return tempPermitTypeCode;
    }

    public void setTempPermitTypeCode(String tempPermitTypeCode) {
        this.tempPermitTypeCode = tempPermitTypeCode;
    }
}
