package org.rta.core.entity.certificate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.office.NocAddressEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

/**
 *	@Author sohan.maurya created on May 24, 2017.
 */

@Entity
@Table(name = "vehicle_noc_history")
public class NocDetailsHistoryEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -1568685583267144067L;

    @Id
    @Column(name = "noc_history_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "noc_details_history_seq")
    @SequenceGenerator(name = "noc_details_history_seq", sequenceName = "noc_details_history_seq", allocationSize = 1)
    private Long nocDetailsHistoryId;

    @Column(name = "noc_id")
    private Long nocDetailsId;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_rc_id")
    private VehicleRCEntity vehicleRcId;

    @Column(name = "noc_issue_date")
    private Long issueDate;

    @Column(name = "noc_cancellation_date")
    private Long cancellationDate;

    @Column(name = "noc_application_date")
    private Long appliedDate;

    @Column(name = "status")
    private Boolean status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noc_address_id")
    private NocAddressEntity addressId;
    
    @Column(name = "service_code")
    private String serviceCode;

    public Long getNocDetailsId() {
        return nocDetailsId;
    }

    public void setNocDetailsId(Long nocDetailsId) {
        this.nocDetailsId = nocDetailsId;
    }

    public VehicleRCEntity getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public Long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Long issueDate) {
        this.issueDate = issueDate;
    }

    public Long getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Long cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public Long getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Long appliedDate) {
        this.appliedDate = appliedDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public NocAddressEntity getAddressId() {
        return addressId;
    }

    public void setAddressId(NocAddressEntity addressId) {
        this.addressId = addressId;
    }

	public Long getNocDetailsHistoryId() {
		return nocDetailsHistoryId;
	}

	public void setNocDetailsHistoryId(Long nocDetailsHistoryId) {
		this.nocDetailsHistoryId = nocDetailsHistoryId;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

}
