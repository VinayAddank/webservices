package org.rta.core.entity.payment.registfee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "fitness_fee_detail_history")
public class FitnessFeeHistoryEntity extends BaseEntity {

   
   private static final long serialVersionUID = -960972917981389356L;

	@Id
    @Column(name = "fitness_fee_history_Id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fitness_fee_history_seq")
    @SequenceGenerator(name = "fitness_fee_history_seq", sequenceName = "fitness_fee_history_seq", allocationSize = 1)
    private Long fitnessFeeHistoryId;

	@Column(name = "vehicle_rc_id")
    private long vehicleRcId;
	
	@Column(name = "fitness_fee")
    private double fitnessFee;
    
    @Column(name = "fitness_service")
    private double fitnessService;
    
    @Column(name = "total_fitness_fee")
    private double totalFitnessFee;

	public Long getFitnessFeeHistoryId() {
		return fitnessFeeHistoryId;
	}

	public void setFitnessFeeHistoryId(Long fitnessFeeHistoryId) {
		this.fitnessFeeHistoryId = fitnessFeeHistoryId;
	}

	public long getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(long vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
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
}
