package org.rta.core.entity.vehicle;
/**
 * This entity is used to keep all released pr numbers because of cancellation of RC or Vehicle Alteration or Re-assignment etc.
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "vehicle_pr_release")
public class VehiclePRReleaseEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -945384440732175448L;
	
	@Id
    @Column(name = "vehicle_pr_release_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_pr_release_seq")
    @SequenceGenerator(name = "vehicle_pr_release_seq", sequenceName = "vehicle_pr_release_seq", allocationSize = 1)
	private Long prReleaseId;
	
	@Column(name = "vehicle_rc_id")
	private Long vehicleRcId;
	
    @NotNull
    @Column(name = "pr_number", unique=true)
	private String prNumber;
    
    @Column(name = "pr_release_status")
	private  Boolean prReleaseStatus;
    
    @Column(name = "service_type")
    private String serviceType;
    
    @Column(name = "app_no")
    private String applicationNumber;

	public Long getPrReleaseId() {
		return prReleaseId;
	}

	public void setPrReleaseId(Long prReleaseId) {
		this.prReleaseId = prReleaseId;
	}

	public Long getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(Long vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public String getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}

	public Boolean getPrReleaseStatus() {
		return prReleaseStatus;
	}

	public void setPrReleaseStatus(Boolean prReleaseStatus) {
		this.prReleaseStatus = prReleaseStatus;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	
}
