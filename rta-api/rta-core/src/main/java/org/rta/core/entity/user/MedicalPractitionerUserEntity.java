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
@Table(name = "medical_practitioner_users")
public class MedicalPractitionerUserEntity extends BaseEntity {

	private static final long serialVersionUID = -5540827268469465425L;

	@Id
    @Column(name = "medical_practitioner_user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_practitioner_user_seq")
    @SequenceGenerator(name = "medical_practitioner_user_seq", sequenceName = "medical_practitioner_user_seq", allocationSize = 1)
    private Long medicalPractitionerUserId;
    
    @Column(name = "name")
    private String name;
	
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @Column(name = "medical_license_number")
    private String medicalLicenseNumber;

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

    public Long getMedicalPractitionerUserId() {
        return medicalPractitionerUserId;
    }

    public void setMedicalPractitionerUserId(Long medicalPractitionerUserId) {
        this.medicalPractitionerUserId = medicalPractitionerUserId;
    }

    public String getMedicalLicenseNumber() {
        return medicalLicenseNumber;
    }

    public void setMedicalLicenseNumber(String medicalLicenseNumber) {
        this.medicalLicenseNumber = medicalLicenseNumber;
    }

    
    
}
