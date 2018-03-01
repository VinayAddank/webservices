package org.rta.core.entity.application;

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
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name = "vehicle_rc_history")
public class VehicleRCHistoryEntity extends BaseEntity {
    
    private static final long serialVersionUID = -1381879977885606532L;
    
    @Id
    @Column(name = "vehicle_rc_history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_rc_history_seq")
    @SequenceGenerator(name = "vehicle_rc_history_seq", sequenceName = "vehicle_rc_history_seq", allocationSize = 1)
    private Long vehicleRcHistoryId;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_rc_id")
    private VehicleRCEntity vehicleRc;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    
    @Column(name = "rta_employee_type")
    private Integer rtaEmployeeType;
    
    @Column(name = "status")
    private Integer status;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "comment")
    private String comment;
    
    @Column(name = "iteration")
    private Integer iteration;

    public Long getVehicleRcHistoryId() {
        return vehicleRcHistoryId;
    }

    public void setVehicleRcHistoryId(Long vehicleRcHistoryId) {
        this.vehicleRcHistoryId = vehicleRcHistoryId;
    }

    public VehicleRCEntity getVehicleRc() {
        return vehicleRc;
    }

    public void setVehicleRc(VehicleRCEntity vehicleRc) {
        this.vehicleRc = vehicleRc;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity rtaEmployee) {
        this.userEntity = rtaEmployee;
    }

    public Integer getRtaEmployeeType() {
        return rtaEmployeeType;
    }

    public void setRtaEmployeeType(Integer rtaEmployeeType) {
        this.rtaEmployeeType = rtaEmployeeType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getIteration() {
        return iteration;
    }

    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }
    
}
