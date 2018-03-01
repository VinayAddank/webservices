package org.rta.core.entity.payment.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "vehicle_quarterly")
public class VehicleQuarterlyEntity extends BaseEntity {

    private static final long serialVersionUID = -1851988472992631408L;

    @Id
    @Column(name = "vehicle_quarterly_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_quarterly_seq")
    @SequenceGenerator(name = "vehicle_quarterly_seq", sequenceName = "vehicle_quarterly_seq", allocationSize = 1)
    private Long vehicle_quarterlyId;

    @Column(name = "vehicle_code")
    private String vehicleCode;

    @Column(name = "vehicle_weight")
    private String vehicleWeight;

    @Column(name = "status")
    private Long status;


    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public String getVehicleWeight() {
        return vehicleWeight;
    }

    public void setVehicleWeight(String vehicleWeight) {
        this.vehicleWeight = vehicleWeight;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getVehicle_quarterlyId() {
        return vehicle_quarterlyId;
    }

    public void setVehicle_quarterlyId(Long vehicle_quarterlyId) {
        this.vehicle_quarterlyId = vehicle_quarterlyId;
    }
}
