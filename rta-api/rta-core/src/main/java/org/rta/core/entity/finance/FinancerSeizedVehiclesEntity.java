package org.rta.core.entity.finance;

import javax.persistence.CascadeType;
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
@Table(name="financer_seized_vehicles")
public class FinancerSeizedVehiclesEntity extends BaseEntity {

    private static final long serialVersionUID = -4584294392799285709L;

    @Id
    @Column(name = "financer_siezed_vehicles_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financer_seized_vehicles_seq")
    @SequenceGenerator(name = "financer_seized_vehicles_seq", sequenceName = "financer_seized_vehicles_seq", allocationSize = 1)
    private Long financerSiezedId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_rc_id")
    private VehicleRCEntity vehicleRcId;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @Column(name = "status")
    private Integer status;

    public Long getFinancerSiezedId() {
        return financerSiezedId;
    }

    public void setFinancerSiezedId(Long financerSiezedId) {
        this.financerSiezedId = financerSiezedId;
    }

    public VehicleRCEntity getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
}
