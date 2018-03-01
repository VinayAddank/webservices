package org.rta.core.entity.vehicle;

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

@Entity
@Table(name = "vehicle_duplicate_registration")
public class DuplicateRegistrationEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4919922119054976494L;
	
	@Id
	@Column(name = "vehicle_dup_reg_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_duplicate_registration_seq")
	@SequenceGenerator(name = "vehicle_duplicate_registration_seq", sequenceName = "vehicle_duplicate_registration_seq", allocationSize = 1)
	private Long vehicleDupRegId;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRcId;
	
	
	@Column(name = "duplicate_reason")
	private String duplicateReason;
	
	@Column(name = "comments")
	private String comments;

	public Long getVehicleDupRegId() {
		return vehicleDupRegId;
	}
	
	public void setVehicleDupRegId(Long vehicleDupRegId) {
		this.vehicleDupRegId = vehicleDupRegId;
	}

	public VehicleRCEntity getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public String getDuplicateReason() {
		return duplicateReason;
	}

	public void setDuplicateReason(String duplicateReason) {
		this.duplicateReason = duplicateReason;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
