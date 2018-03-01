package org.rta.core.entity.vehicle.cms;

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

import org.rta.core.entity.base.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "vehicle_class_desc")
public class VehicleClassDescriptionEntity extends BaseEntity {

	@Id
	@Column(name = "vehicle_class_desc_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_class_desc_id_seq")
	@SequenceGenerator(name = "vehicle_class_desc_id_seq", sequenceName = "vehicle_class_desc_id_seq", allocationSize = 1)
	private Long id;

	@Column(name = "code")
	private String code;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_class_id")
	private VehicleClassEntity vehicleClassId;

	@Column(name = "description")
	private String description;

	@Column(name = "alteration_category")
    private Integer alterationCategory;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public VehicleClassEntity getVehicleClassId() {
		return vehicleClassId;
	}

	public void setVehicleClassIds(VehicleClassEntity vehicleClassId) {
		this.vehicleClassId = vehicleClassId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public Integer getAlterationCategory() {
        return alterationCategory;
    }

    public void setAlterationCategory(Integer alterationCategory) {
        this.alterationCategory = alterationCategory;
    }

}
