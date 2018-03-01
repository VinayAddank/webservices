package org.rta.core.entity.finance;

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
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name = "financeDetailsEntity")
public class FinanceDetailsEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rta_financeDetails_seq")
    @SequenceGenerator(name = "rta_financeDetails_seq", sequenceName = "rta_financeDetails_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "vehicle_rc_id")
    private VehicleRCEntity vehicleRcId;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "date_of_agreement")
    private Long dateOfAgreement;

    @NotNull
    @Column(name = "financer_mode")
    private Integer financerMode;

    @Column(name = "financer_m_id")
    private Long financerMId;


    @Column(name = "finance_status")
    private Integer financeApprStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "state_entity")
    private StateEntity stateEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mandal_entity")
    private MandalEntity mandalEntity;

    @NotNull
    @Column(name = "city")
    private String city;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "district_entity")
    private DistrictEntity districtEntity;

    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleRCEntity getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDateOfAgreement() {
        return dateOfAgreement;
    }

    public void setDateOfAgreement(Long dateOfAgreement) {
        this.dateOfAgreement = dateOfAgreement;
    }

    public Integer getFinancerMode() {
        return financerMode;
    }

    public void setFinancerMode(Integer financerMode) {
        this.financerMode = financerMode;
    }

    public Long getFinancerMId() {
        return financerMId;
    }

    public void setFinancerMId(Long financerMId) {
        this.financerMId = financerMId;
    }

    public Integer getFinanceApprStatus() {
        return financeApprStatus;
    }

    public void setFinanceApprStatus(Integer financeApprStatus) {
        this.financeApprStatus = financeApprStatus;
    }

    public StateEntity getStateEntity() {
        return stateEntity;
    }

    public void setStateEntity(StateEntity stateEntity) {
        this.stateEntity = stateEntity;
    }

    public MandalEntity getMandalEntity() {
        return mandalEntity;
    }

    public void setMandalEntity(MandalEntity mandalEntity) {
        this.mandalEntity = mandalEntity;
    }

    public DistrictEntity getDistrictEntity() {
        return districtEntity;
    }

    public void setDistrictEntity(DistrictEntity districtEntity) {
        this.districtEntity = districtEntity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
