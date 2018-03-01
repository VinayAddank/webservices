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
@Table(name = "driving_institute_users")
public class DrivingInstituteUserEntity extends BaseEntity {

	private static final long serialVersionUID = -5540827268469465425L;

	@Id
    @Column(name = "driving_institute_users_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driving_institute_users_seq")
    @SequenceGenerator(name = "driving_institute_users_seq", sequenceName = "driving_institute_users_seq", allocationSize = 1)
    private Long drivingInstituteUsersId;
    
    @Column(name = "name")
    private String name;
	
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @Column(name = "no_of_cars")
    private Integer noOfCars;
    
    @Column(name = "no_of_driving_instructors")
    private Integer noOfDrivingInstructors;

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

    public Long getDrivingInstituteUsersId() {
        return drivingInstituteUsersId;
    }

    public void setDrivingInstituteUsersId(Long drivingInstituteUsersId) {
        this.drivingInstituteUsersId = drivingInstituteUsersId;
    }

    public Integer getNoOfCars() {
        return noOfCars;
    }

    public void setNoOfCars(Integer noOfCars) {
        this.noOfCars = noOfCars;
    }

    public Integer getNoOfDrivingInstructors() {
        return noOfDrivingInstructors;
    }

    public void setNoOfDrivingInstructors(Integer noOfDrivingInstructors) {
        this.noOfDrivingInstructors = noOfDrivingInstructors;
    }

}
