package org.rta.core.entity.licence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseCommonEntity;


/**
 * The persistent class for the Vehicle_Training_period database table.
 * 
 */
@Entity
@Table(name="vehicle_training_period")
public class VehicleTrainingPeriodEntity extends BaseCommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="vehicle_class_code")
	private String vehicleClassCode;

	@Column(name="first_training_period")
	private Integer firstTrainingPeriod;
	
	@Column(name="renewal_training_period")
	private Integer renewalTrainingPeriod;

	public String getVehicleClassCode() {
		return vehicleClassCode;
	}

	public void setVehicleClassCode(String vehicleClassCode) {
		this.vehicleClassCode = vehicleClassCode;
	}

	public Integer getFirstTrainingPeriod() {
		return firstTrainingPeriod;
	}

	public void setFirstTrainingPeriod(Integer firstTrainingPeriod) {
		this.firstTrainingPeriod = firstTrainingPeriod;
	}

	public Integer getRenewalTrainingPeriod() {
		return renewalTrainingPeriod;
	}

	public void setRenewalTrainingPeriod(Integer renewalTrainingPeriod) {
		this.renewalTrainingPeriod = renewalTrainingPeriod;
	}  
}