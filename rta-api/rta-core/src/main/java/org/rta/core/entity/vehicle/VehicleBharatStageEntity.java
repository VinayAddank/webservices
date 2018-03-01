package org.rta.core.entity.vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.SerializableEntity;

@Entity
@Table(name = "vehicle_bharat_stage")
public class VehicleBharatStageEntity extends SerializableEntity{

    private static final long serialVersionUID = -3927682172896053248L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bharat_seq")
    @SequenceGenerator(name = "bharat_seq", sequenceName = "bharat_seq", allocationSize = 1)
    private Long id;
   
    @NotNull
    @Column(name = "chassis_number", unique = true)
    private String chassisNumber;

    @NotNull
    @Column(name = "engine_number", length=99)
    private String engineNumber;

    @NotNull
    @Column(name = "policy_start_date")
    private String policyStartDate;
    
    @NotNull
    @Column(name = "is_migrated")
    private Boolean isMigrated;
    
    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getPolicyStartDate() {
        return policyStartDate;
    }

    public void setPolicyStartDate(String policyStartDate) {
        this.policyStartDate = policyStartDate;
    }

    public Boolean getIsMigrated() {
        return isMigrated;
    }

    public void setIsMigrated(Boolean isMigrated) {
        this.isMigrated = isMigrated;
    }
}
