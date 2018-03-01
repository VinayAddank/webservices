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
@Table(name = "alteration_agency_users")
public class AlterationAgencyUserEntity extends BaseEntity {

	private static final long serialVersionUID = 2474360901678312621L;

	@Id
    @Column(name = "alteration_agency_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alteration_agency_seq")
    @SequenceGenerator(name = "alteration_agency_seq", sequenceName = "alteration_agency_seq", allocationSize = 1)
    private Long alterationAgencyId;
    
    @Column(name = "name")
    private String name;
	
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @Column(name = "class_of_vehicle")
	private String classOfVehicle;

	public Long getAlterationAgencyId() {
		return alterationAgencyId;
	}

	public void setAlterationAgencyId(Long alterationAgencyId) {
		this.alterationAgencyId = alterationAgencyId;
	}

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

	public String getClassOfVehicle() {
		return classOfVehicle;
	}

	public void setClassOfVehicle(String classOfVehicle) {
		this.classOfVehicle = classOfVehicle;
	}
	
}
