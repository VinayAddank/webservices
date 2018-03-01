package org.rta.core.entity.payment.registfee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;


	@Entity
	@Table(name = "reg_fee_detail_history")
	public class RegFeeDetailHistoryEntity extends BaseEntity {

    private static final long serialVersionUID = -6826107400282225422L;

	@Id
    @Column(name = "reg_fee_hist_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reg_fee_hist_seq")
    @SequenceGenerator(name = "reg_fee_hist_seq", sequenceName = "reg_fee_hist_seq", allocationSize = 1)
    private Long regFeeDtlHistId;

    @NotNull
    @Column(name = "vehicle_rc_id")
    private Long vehicleRcId;

    @Column(name = "reg_fee")
    private Double regFee;

    @Column(name = "postal_charge")
    private double postalCharge;

    @Column(name = "smart_card_fee")
    private double smartCardFee;

    @Column(name = "service_charge")
    private double serviceCharge;

    @Column(name = "imported_fee")
    private double importedFee;

    @Column(name = "total_reg_fee")
    private Double totalRegFee;

    @Column(name = "special_number_fee")
    private double specialNumberFee;
    
    @Column(name = "fitness_id")
    private Long fitnessId;

    @Column(name = "permit_id")
    private Long permitId;

	public Long getRegFeeDtlHistId() {
		return regFeeDtlHistId;
	}

	public void setRegFeeDtlHistId(Long regFeeDtlHistId) {
		this.regFeeDtlHistId = regFeeDtlHistId;
	}

	public Long getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(Long vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public Double getRegFee() {
		return regFee;
	}

	public void setRegFee(Double regFee) {
		this.regFee = regFee;
	}

	public double getPostalCharge() {
		return postalCharge;
	}

	public void setPostalCharge(double postalCharge) {
		this.postalCharge = postalCharge;
	}

	public double getSmartCardFee() {
		return smartCardFee;
	}

	public void setSmartCardFee(double smartCardFee) {
		this.smartCardFee = smartCardFee;
	}

	public double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public double getImportedFee() {
		return importedFee;
	}

	public void setImportedFee(double importedFee) {
		this.importedFee = importedFee;
	}

	public Double getTotalRegFee() {
		return totalRegFee;
	}

	public void setTotalRegFee(Double totalRegFee) {
		this.totalRegFee = totalRegFee;
	}

	public double getSpecialNumberFee() {
		return specialNumberFee;
	}

	public void setSpecialNumberFee(double specialNumberFee) {
		this.specialNumberFee = specialNumberFee;
	}

	public Long getFitnessId() {
		return fitnessId;
	}

	public void setFitnessId(Long fitnessId) {
		this.fitnessId = fitnessId;
	}

	public Long getPermitId() {
		return permitId;
	}

	public void setPermitId(Long permitId) {
		this.permitId = permitId;
	}

    
}
