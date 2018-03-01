package org.rta.core.entity.docs;

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

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.master.DocTypesEntity;

/**
 * @Author sohan.maurya created on Aug 24, 2016.
 */

@Entity
@Table(name = "doc_permission")
public class DocPermissionEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "doc_permission_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_permission_gen")
    @SequenceGenerator(name = "doc_permission_gen", sequenceName = "doc_permission_seq", allocationSize = 1)
    private Integer docPermissionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doc_types_id", nullable = false)
    private DocTypesEntity docTypesEntity;

    // use 100 for all vehicle_class
    @Column(name = "vehicle_class")
    private Integer vehicleClass;

    // @ManyToOne(fetch = FetchType.LAZY, optional = true)
    // @JoinColumn(name = "registration_category_id", nullable = false)
    // private RegistrationCategoryEntity registrationCategoryEntity;

    // use 100 for all vehicle_class
    @Column(name = "registration_category_type")
    private Integer registrationCategoryType;

    // @ManyToOne(fetch = FetchType.LAZY, optional = true)
    // @JoinColumn(name = "ownership_type_id", nullable = true)
    // private OwnershipTypeEntity ownershipTypeEntity;

    // use 100 for all vehicle_class
    @Column(name = "ownership_type")
    private Integer ownershipType;

    @Column(name = "is_mandatory")
    private Boolean isMandatory;

    public Integer getDocPermissionId() {
        return docPermissionId;
    }

    public void setDocPermissionId(Integer docPermissionId) {
        this.docPermissionId = docPermissionId;
    }

    public DocTypesEntity getDocTypesEntity() {
        return docTypesEntity;
    }

    public void setDocTypesEntity(DocTypesEntity docTypesEntity) {
        this.docTypesEntity = docTypesEntity;
    }

    public Integer getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(Integer vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public Integer getRegistrationCategoryType() {
        return registrationCategoryType;
    }

    public void setRegistrationCategoryType(Integer registrationCategoryType) {
        this.registrationCategoryType = registrationCategoryType;
    }

    public Integer getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(Integer ownershipType) {
        this.ownershipType = ownershipType;
    }

    public Boolean getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

}
