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
@Table(name = "rta_vehicle_tax")
public class RTAVehicleTaxEntity extends BaseEntity {

    private static final long serialVersionUID = 6103348767467217072L;

    @Id
    @Column(name = "rta_vehicle_tax_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rta_vehicle_tax_seq")
    @SequenceGenerator(name = "rta_vehicle_tax_seq", sequenceName = "rta_vehicle_tax_seq", allocationSize = 1)
    private Long rtaVehicleTaxId;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "vehicle_code")
    private String vehicleCode;

    @Column(name = "state")
    private String state;

    @Column(name = "fr_ulw")
    private double frULW;

    @Column(name = "to_ulw")
    private double toULW;

    @Column(name = "to_date")
    private String toDate;

    @Column(name = "tax_amount")
    private double taxAmount;

    @Column(name = "ownership_type")
    private String ownershipType;
    
    @Column(name = "status")
	private Integer status;
    

    public Long getRtaVehicleTaxId() {
        return rtaVehicleTaxId;
    }

    public void setRtaVehicleTaxId(Long rtaVehicleTaxId) {
        this.rtaVehicleTaxId = rtaVehicleTaxId;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getFrULW() {
        return frULW;
    }

    public void setFrULW(double frULW) {
        this.frULW = frULW;
    }

    public double getToULW() {
        return toULW;
    }

    public void setToULW(double toULW) {
        this.toULW = toULW;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(String ownershipType) {
        this.ownershipType = ownershipType;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
