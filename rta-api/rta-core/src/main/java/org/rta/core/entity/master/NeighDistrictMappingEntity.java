package org.rta.core.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.rta.core.entity.base.SerializableEntity;

@Entity
@Table(name = "neigh_district_mapping")
public class NeighDistrictMappingEntity extends SerializableEntity{
	
	private static final long serialVersionUID = -9213917512481952404L;

	@Id
	@Column(name = "district_id")
    private Long districtId;

    @Column(name = "neigh_district_id")
    private Long neighDistrictId;

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getNeighDistrictId() {
		return neighDistrictId;
	}

	public void setNeighDistrictId(Long neighDistrictId) {
		this.neighDistrictId = neighDistrictId;
	}

}
