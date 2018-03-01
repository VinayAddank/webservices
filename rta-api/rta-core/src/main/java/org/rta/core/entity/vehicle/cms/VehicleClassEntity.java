package org.rta.core.entity.vehicle.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "vehicle_class")
public class VehicleClassEntity extends BaseEntity {
	@Id
	@Column(name = "vehicle_class_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_class_id_seq")
	@SequenceGenerator(name = "vehicle_class_id_seq", sequenceName = "vehicle_class_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "vehicle_category")
	private String vehicleCateg;

	@Column(name = "vehicle_class_code")
	private String vehicleClassCode;

	public String getVehicleCateg() {
		return vehicleCateg;
	}

	public void setVehicleCateg(String vehicleCateg) {
		this.vehicleCateg = vehicleCateg;
	}

	public String getVehicleClassCode() {
		return vehicleClassCode;
	}

	public void setVehicleClassCode(String vehicleClassCode) {
		this.vehicleClassCode = vehicleClassCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
