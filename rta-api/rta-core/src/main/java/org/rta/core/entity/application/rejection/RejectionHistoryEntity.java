package org.rta.core.entity.application.rejection;

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
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

/**
 * this entity use for all type of rejection related like document, application
 * 
 * @Author sohan.maurya created on Aug 1, 2016.
 */

@Entity
@Table(name = "rejection_history")
public class RejectionHistoryEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "rejection_history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rejection_hist_gen")
    @SequenceGenerator(name = "rejection_hist_gen", sequenceName = "rejection_history_sequence", allocationSize = 1,
            initialValue = 1)
    private Long rejectionHistId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_rc_id", nullable = false)
    private VehicleRCEntity vehicleRcId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "attachment_details_id", nullable = true)
    private AttachmentDetailsEntity attachmentDetailsId;

    @Column(name = "user_type")
    private Integer userType;

    @Column(name = "title")
    private String title;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    private Integer status;
    
    @Column(name = "iteration")
    private Integer iteration;


    public Long getRejectionHistId() {
        return rejectionHistId;
    }

    public void setRejectionHistId(Long rejectionHistId) {
        this.rejectionHistId = rejectionHistId;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public AttachmentDetailsEntity getAttachmentDetailsId() {
        return attachmentDetailsId;
    }

    public void setAttachmentDetailsId(AttachmentDetailsEntity attachmentDetailsId) {
        this.attachmentDetailsId = attachmentDetailsId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public VehicleRCEntity getVehicleRCId() {
        return vehicleRcId;
    }

    public void setVehicleRCId(VehicleRCEntity vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public Integer getIteration() {
        return iteration;
    }

    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }
}
