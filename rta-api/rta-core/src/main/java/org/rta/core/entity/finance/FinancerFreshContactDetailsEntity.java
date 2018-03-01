package org.rta.core.entity.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "financer_fresh_contact_details")
public class FinancerFreshContactDetailsEntity extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financer_fresh_contact_details_seq")
	@SequenceGenerator(name = "financer_fresh_contact_details_seq", sequenceName = "financer_fresh_contact_details_seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "vehicle_rc_id")
	private Long vehicleRcId;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "is_updated" ,columnDefinition = "boolean default false")
	private Boolean isUpdated;
	
	@Transient
	private Long financierId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(Long vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	public Long getFinancierId() {
		return financierId;
	}

	public void setFinancierId(Long financierId) {
		this.financierId = financierId;
	}
	
	
}
