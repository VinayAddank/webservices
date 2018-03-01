package org.rta.core.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "tax_master")
public class TaxMasterEntity extends BaseEntity {


    private static final long serialVersionUID = -4781627169875041479L;

    @Id
    @Column(name = "tax_master_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_master_seq")
    @SequenceGenerator(name = "tax_master_seq", sequenceName = "tax_master_seq", allocationSize = 1)
    private Long taxMasterId;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "vehicle_No")
    private Integer vehicleNo;

    @Column(name = "tax_percent")
    private double taxPercent;

    @Column(name = "price_to")
    private double priceTo;

    @Column(name = "price_from")
    private double priceFrom;

    public Long getTaxMasterId() {
        return taxMasterId;
    }

    public void setTaxMasterId(Long taxMasterId) {
        this.taxMasterId = taxMasterId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(Integer vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public double getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(double taxPercent) {
        this.taxPercent = taxPercent;
    }

    public double getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(double priceTo) {
        this.priceTo = priceTo;
    }

    public double getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(double priceFrom) {
        this.priceFrom = priceFrom;
    }
}
