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
@Table(name = "fitness_fee_details")
public class FitnessFeeDetailsEntity extends BaseEntity {

   
   private static final long serialVersionUID = -960972917981389356L;

	@Id
    @Column(name = "fitness_fee_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fitness_fee_seq")
    @SequenceGenerator(name = "fitness_fee_seq", sequenceName = "fitness_fee_seq", allocationSize = 1)
    private Long fitnessFeeId;

	@NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_rc_id" , unique=true)
    private VehicleRCEntity vehicleRc;
	
	@Column(name = "fitness_fee")
    private double fitnessFee;
    
    @Column(name = "fitness_service")
    private double fitnessService;
    
    @Column(name = "total_fitness_fee")
    private double totalFitnessFee;
    
    @Column(name = "transaction_id")
    private Long transactionId;
    
    @Column(name = "reg_type")
    private Integer regType;
    
    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;

	public Long getFitnessFeeId() {
		return fitnessFeeId;
	}

	public void setFitnessFeeId(Long fitnessFeeId) {
		this.fitnessFeeId = fitnessFeeId;
	}

	public VehicleRCEntity getVehicleRc() {
		return vehicleRc;
	}

	public void setVehicleRc(VehicleRCEntity vehicleRc) {
		this.vehicleRc = vehicleRc;
	}

	public double getFitnessFee() {
		return fitnessFee;
	}

	public void setFitnessFee(double fitnessFee) {
		this.fitnessFee = fitnessFee;
	}

	public double getFitnessService() {
		return fitnessService;
	}

	public void setFitnessService(double fitnessService) {
		this.fitnessService = fitnessService;
	}

	public double getTotalFitnessFee() {
		return totalFitnessFee;
	}

	public void setTotalFitnessFee(double totalFitnessFee) {
		this.totalFitnessFee = totalFitnessFee;
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
