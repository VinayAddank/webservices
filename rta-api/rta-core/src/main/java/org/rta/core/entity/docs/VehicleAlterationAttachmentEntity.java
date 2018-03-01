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
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.master.DocTypesEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

/**
 * @Author sohan.maurya created on Jan 4, 2017.
 *
 * 
 * this entity is use for store url and get documents, and these are vehicle related
 * documents.. uploaded by bodybuilder on other server
 */

@Entity
@Table(name = "vehicle_alteration_attachment")
public class VehicleAlterationAttachmentEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -5536121067047542761L;
    
    @Id
    @Column(name = "vehicle_alter_attachment_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_alter_attachment_gen")
    @SequenceGenerator(name = "vehicle_alter_attachment_gen", sequenceName = "vehicle_alter_attachment_sequence",  allocationSize = 1)
    private Long attachmentDlId;

    @NotNull
    @Column(name = "file_name", length = 200)
    private String fileName;

    @NotNull
    @Column(name = "source")
    private String source;

    @NotNull
    @Column(name = "status", length = 2)
    private Integer status;

    @NotNull
    @Column(name = "attachment_title", length = 200)
    private String attachmentTitle;

    @NotNull
    @Column(name = "attachment_from")
    private Integer attachmentFrom;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_master_id")
    private DocTypesEntity docTypes;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_rc_id")
    private VehicleRCEntity vehicle;

    @Column(name = "user_role", length = 3)
    private Integer userRole;

    public Long getAttachmentDlId() {
        return attachmentDlId;
    }

    public void setAttachmentDlId(Long attachmentDlId) {
        this.attachmentDlId = attachmentDlId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAttachmentTitle() {
        return attachmentTitle;
    }

    public void setAttachmentTitle(String attachmentTitle) {
        this.attachmentTitle = attachmentTitle;
    }

    public Integer getAttachmentFrom() {
        return attachmentFrom;
    }

    public void setAttachmentFrom(Integer attachmentFrom) {
        this.attachmentFrom = attachmentFrom;
    }

    public DocTypesEntity getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(DocTypesEntity docTypes) {
        this.docTypes = docTypes;
    }

    public VehicleRCEntity getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleRCEntity vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

}
