package org.rta.core.entity.insurance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.master.InsuranceTypeEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

/**
 * @author arun.verma
 *
 */

@Entity
@Table(name = "insurance_details")
public class InsuranceDetailsEntity extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = -3218515588920476253L;
    @Id
    @Column(name = "insurance_dtl_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurance_dtl_seq")
    @SequenceGenerator(name = "insurance_dtl_seq", sequenceName = "insurance_dtl_seq", allocationSize = 1)
    private Long insuranceDtlId;

    @NotNull
    @Column(name = "mode")
    private String mode;

    @NotNull
    @Column(name = "provider")
    private String provider;

    @NotNull
    @Column(name = "policy_no", unique = true)
    private String policyNo;

    @NotNull
    @Column(name = "start_date")
    private Long startDate;

    @NotNull
    @Column(name = "end_date")
    private Long endDate;

    @NotNull
    @Column(name = "tenure")
    private Integer tenure;

    @NotNull
    @Column(name = "status")
    private Integer Status;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "vehicle_rc_id")
    private VehicleRCEntity vehicleRcEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "insurance_id")
    private InsuranceTypeEntity insuranceTypeEntity;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_dtl_id")
    private VehicleDetailsEntity vehicleDetailsEntity;
    
    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;
   
    public Long getInsuranceDtlId() {
        return insuranceDtlId;
    }

    public void setInsuranceDtlId(Long insuranceDtlId) {
        this.insuranceDtlId = insuranceDtlId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public VehicleRCEntity getVehicleRcEntity() {
        return vehicleRcEntity;
    }

    public void setVehicleRcEntity(VehicleRCEntity vehicleRcEntity) {
        this.vehicleRcEntity = vehicleRcEntity;
    }

    public InsuranceTypeEntity getInsuranceTypeEntity() {
        return insuranceTypeEntity;
    }

    public void setInsuranceTypeEntity(InsuranceTypeEntity insuranceTypeEntity) {
        this.insuranceTypeEntity = insuranceTypeEntity;
    }

    public VehicleDetailsEntity getVehicleDetailsEntity() {
        return vehicleDetailsEntity;
    }

    public void setVehicleDetailsEntity(VehicleDetailsEntity vehicleDetailsEntity) {
        this.vehicleDetailsEntity = vehicleDetailsEntity;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    
}
