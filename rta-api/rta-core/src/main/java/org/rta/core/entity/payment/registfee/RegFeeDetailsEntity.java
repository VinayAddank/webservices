package org.rta.core.entity.payment.registfee;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;


@Entity
@Table(name = "reg_fee_details")
public class RegFeeDetailsEntity extends BaseEntity {

    private static final long serialVersionUID = -6565256389973609622L;

    @Id
    @Column(name = "reg_fee_dtl_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reg_fee_dtl_seq")
    @SequenceGenerator(name = "reg_fee_dtl_seq", sequenceName = "reg_fee_dtl_seq", allocationSize = 1)
    private Long regFeeDtlId;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_rc_id" , unique=true)
    private VehicleRCEntity vehicleRc;

    @Column(name = "tr_fee" )
    private double trFee;

    @Column(name = "tr_service_charge")
    private double trServiceCharge;

    @Column(name = "pr_fee")
    private double prFee;

    @Column(name = "hpa_fee")
    private double hpaFee;

    @Column(name = "pr_service_charge")
    private double prServiceCharge;

    @Column(name = "smart_card_fee")
    private double smartCardFee;

    @Column(name = "special_number_fee")
    private double specialNumberFee;
    
    @Column(name = "imported_fee")
    private double importedFee;

    @Column(name = "postal_charge")
    private double postalCharge;

    @Column(name = "total_fee")
    private double totalFee;

    @Column(name = "transaction_id")
    private Long transactionId;
    
    @Column(name = "reg_type")
    private Integer regType;
    
    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;

	public Long getRegFeeDtlId() {
		return regFeeDtlId;
	}

	public void setRegFeeDtlId(Long regFeeDtlId) {
		this.regFeeDtlId = regFeeDtlId;
	}

	public VehicleRCEntity getVehicleRc() {
		return vehicleRc;
	}

	public void setVehicleRc(VehicleRCEntity vehicleRc) {
		this.vehicleRc = vehicleRc;
	}

	public double getTrFee() {
		return trFee;
	}

	public void setTrFee(double trFee) {
		this.trFee = trFee;
	}

	public double getTrServiceCharge() {
		return trServiceCharge;
	}

	public void setTrServiceCharge(double trServiceCharge) {
		this.trServiceCharge = trServiceCharge;
	}

	public double getPrFee() {
		return prFee;
	}

	public void setPrFee(double prFee) {
		this.prFee = prFee;
	}

	public double getHpaFee() {
		return hpaFee;
	}

	public void setHpaFee(double hpaFee) {
		this.hpaFee = hpaFee;
	}

	public double getPrServiceCharge() {
		return prServiceCharge;
	}

	public void setPrServiceCharge(double prServiceCharge) {
		this.prServiceCharge = prServiceCharge;
	}

	public double getSmartCardFee() {
		return smartCardFee;
	}

	public void setSmartCardFee(double smartCardFee) {
		this.smartCardFee = smartCardFee;
	}

	public double getSpecialNumberFee() {
		return specialNumberFee;
	}

	public void setSpecialNumberFee(double specialNumberFee) {
		this.specialNumberFee = specialNumberFee;
	}

	public double getImportedFee() {
		return importedFee;
	}

	public void setImportedFee(double importedFee) {
		this.importedFee = importedFee;
	}

	public double getPostalCharge() {
		return postalCharge;
	}

	public void setPostalCharge(double postalCharge) {
		this.postalCharge = postalCharge;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
    
    
}
