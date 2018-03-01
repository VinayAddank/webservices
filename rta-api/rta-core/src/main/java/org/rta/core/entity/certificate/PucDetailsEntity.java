package org.rta.core.entity.certificate;

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
import org.rta.core.entity.vehicle.VehicleRCEntity;

/**
 *	@Author sohan.maurya created on Dec 15, 2016.
 */

@Entity
@Table(name = "vehicle_puc")
public class PucDetailsEntity extends BaseEntity{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "puc_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_puc_seq")
    @SequenceGenerator(name = "vehicle_puc_seq", sequenceName = "vehicle_puc_seq", allocationSize = 1)
    private Long pucDetailsId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_rc_id")
    private VehicleRCEntity vehicleRcId;

    @Column(name = "puc_issue_date")
    private Long issueDate;

    @Column(name = "puc_expiry_date")
    private Long expiryDate;

    @Column(name = "puc_co")
    private Float co;

    @Column(name = "puc_hc")
    private Float hc;

    @Column(name = "puc_k_avg")
    private Float kAvg;

    @Column(name = "puc_hsu")
    private Float hsu;

    @Column(name = "status")
    private Boolean status;


    public Long getPucDetailsId() {
        return pucDetailsId;
    }

    public void setPucDetailsId(Long pucDetailsId) {
        this.pucDetailsId = pucDetailsId;
    }

    public VehicleRCEntity getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public Long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Long issueDate) {
        this.issueDate = issueDate;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Float getCo() {
        return co;
    }

    public void setCo(Float co) {
        this.co = co;
    }

    public Float getHc() {
        return hc;
    }

    public void setHc(Float hc) {
        this.hc = hc;
    }

    public Float getkAvg() {
        return kAvg;
    }

    public void setkAvg(Float kAvg) {
        this.kAvg = kAvg;
    }

    public Float getHsu() {
        return hsu;
    }

    public void setHsu(Float hsu) {
        this.hsu = hsu;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    

}
