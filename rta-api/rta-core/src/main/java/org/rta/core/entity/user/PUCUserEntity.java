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
@Table(name = "puc_users")
public class PUCUserEntity extends BaseEntity {

	private static final long serialVersionUID = -5540827268469465425L;

	@Id
    @Column(name = "puc_user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "puc_users_seq")
    @SequenceGenerator(name = "puc_users_seq", sequenceName = "puc_users_seq", allocationSize = 1)
    private Long pucUserId;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "class_of_vehicle")
	private String classOfVehicle;
	
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

	public Long getPucUserId() {
		return pucUserId;
	}

	public void setPucUserId(Long pucUserId) {
		this.pucUserId = pucUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassOfVehicle() {
		return classOfVehicle;
	}

	public void setClassOfVehicle(String classOfVehicle) {
		this.classOfVehicle = classOfVehicle;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
    
}
