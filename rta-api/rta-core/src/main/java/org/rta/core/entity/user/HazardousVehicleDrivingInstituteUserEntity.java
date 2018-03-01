package org.rta.core.entity.user;

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

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "hazardous_vehicle_driving_institute_users")
public class HazardousVehicleDrivingInstituteUserEntity extends BaseEntity {

	private static final long serialVersionUID = -5540827268469465425L;

	@Id
    @Column(name = "hazardous_vehicle_driving_institute_users_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hazardous_vehicle_driving_institute_users_seq")
    @SequenceGenerator(name = "hazardous_vehicle_driving_institute_users_seq", sequenceName = "hazardous_vehicle_driving_institute_users_seq", allocationSize = 1)
    private Long hazardousVehicleDrivingInstituteUserId;
    
    @Column(name = "name")
    private String name;
	
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

    public Long getHazardousVehicleDrivingInstituteUserId() {
        return hazardousVehicleDrivingInstituteUserId;
    }

    public void setHazardousVehicleDrivingInstituteUserId(Long hazardousVehicleDrivingInstituteUserId) {
        this.hazardousVehicleDrivingInstituteUserId = hazardousVehicleDrivingInstituteUserId;
    }

    
    
}
