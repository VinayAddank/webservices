package org.rta.core.entity.vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "new_vehicle_rc_migration")
public class VehicleRCMigrationEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "vehicle_rc_id")
    private Long vehicleRcId;

    @Column(name = "cco_user_id", nullable = true)
    private Long ccoUserId;

    @Column(name = "cco_action_status", nullable = true)
    private Integer ccoActionStatus;

    @Column(name = "mvi_user_id", nullable = true)
    private Long mviUserId;

    @Column(name = "mvi_action_status", nullable = true)
    private Integer mviActionStatus;

    @Column(name = "ao_user_id", nullable = true)
    private Long aoUserId;

    @Column(name = "ao_action_status", nullable = true)
    private Integer aoActionStatus;

    @Column(name = "rto_user_id", nullable = true)
    private Long rtoUserId;

    @Column(name = "rto_action_status", nullable = true)
    private Integer rtoActionStatus;
    
    @Column(name = "dealer_id", nullable = true)
    private Long dealerId;

    @Column(name = "dealer_action_status", nullable = true)
    private Integer dealerActionStatus;

    public VehicleRCMigrationEntity() {
     }
    public VehicleRCMigrationEntity(Long vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public Long getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(Long vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public Long getCcoUserId() {
        return ccoUserId;
    }

    public void setCcoUserId(Long ccoUserId) {
        this.ccoUserId = ccoUserId;
    }

    public Integer getCcoActionStatus() {
        return ccoActionStatus;
    }

    public void setCcoActionStatus(Integer ccoActionStatus) {
        this.ccoActionStatus = ccoActionStatus;
    }

    public Long getMviUserId() {
        return mviUserId;
    }

    public void setMviUserId(Long mviUserId) {
        this.mviUserId = mviUserId;
    }

    public Integer getMviActionStatus() {
        return mviActionStatus;
    }

    public void setMviActionStatus(Integer mviActionStatus) {
        this.mviActionStatus = mviActionStatus;
    }

    public Long getAoUserId() {
        return aoUserId;
    }

    public void setAoUserId(Long aoUserId) {
        this.aoUserId = aoUserId;
    }

    public Integer getAoActionStatus() {
        return aoActionStatus;
    }

    public void setAoActionStatus(Integer aoActionStatus) {
        this.aoActionStatus = aoActionStatus;
    }

    public Long getRtoUserId() {
        return rtoUserId;
    }

    public void setRtoUserId(Long rtoUserId) {
        this.rtoUserId = rtoUserId;
    }

    public Integer getRtoActionStatus() {
        return rtoActionStatus;
    }

    public void setRtoActionStatus(Integer rtoActionStatus) {
        this.rtoActionStatus = rtoActionStatus;
    }

    public Long getDealerId() {
        return dealerId;
    }
    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }
    public Integer getDealerActionStatus() {
        return dealerActionStatus;
    }
    public void setDealerActionStatus(Integer dealerActionStatus) {
        this.dealerActionStatus = dealerActionStatus;
    }

}
