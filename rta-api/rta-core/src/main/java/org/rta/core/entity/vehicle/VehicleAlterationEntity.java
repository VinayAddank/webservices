package org.rta.core.entity.vehicle;

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
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;

/**
 *	@Author sohan.maurya created on Dec 29, 2016.
 */

@Entity
@Table(name = "vehicle_alteration_details")
public class VehicleAlterationEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "alteration_details_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_alteration_details_seq")
    @SequenceGenerator(name = "vehicle_alteration_details_seq", sequenceName = "vehicle_alteration_details_seq", allocationSize = 1)
    private Long alterationDtlsId;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "vehicle_rc_id")
    private VehicleRCEntity vehicleRcId;

    @Column(name = "vehicle_sub_class")
    private String vehicleSubClass;

    @Column(name = "seating_capacity")
    private Integer seatingCapacity;

    @Column(name = "length")
    private Long lengthUpdated;

    @Column(name = "height")
    private Long heightUpdated;

    @Column(name = "width")
    private Long widthUpdated;

    @Column(name = "completion_date")
    private Long completionDate;

    @Column(name = "body_type")
    private String bodyTypeUpdated;

    @Column(name = "fuel_used")
    private String fuelUsedUpdated;

    @Column(name = "alteration_category")
    private Integer alterationCategory;
    
    @Column(name = "status", columnDefinition = "int default 1")
    private Integer status;
    
    @Column(name = "rlw")
	private Long rlwUpdated;
    
    @Column(name = "ulw")
	private Long ulwUpdated;
    
    @Column(name = "color")
	private String colorUpdated;

    @Column(name = "request_id", columnDefinition = "int default 0")
    private Integer requestId;
    
    @Column(name = "app_no", length = 100)
	private String applicationNumber;
    
    public Long getAlterationDtlsId() {
        return alterationDtlsId;
    }

	public void setAlterationDtlsId(Long alterationDtlsId) {
        this.alterationDtlsId = alterationDtlsId;
    }

    public VehicleRCEntity getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public String getVehicleSubClass() {
        return vehicleSubClass;
    }

    public void setVehicleSubClass(String vehicleSubClass) {
        this.vehicleSubClass = vehicleSubClass;
    }

    public Integer getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public Long getLengthUpdated() {
        return lengthUpdated;
    }

    public void setLengthUpdated(Long lengthUpdated) {
        this.lengthUpdated = lengthUpdated;
    }

    public Long getHeightUpdated() {
        return heightUpdated;
    }

    public void setHeightUpdated(Long heightUpdated) {
        this.heightUpdated = heightUpdated;
    }

    public Long getWidthUpdated() {
        return widthUpdated;
    }

    public void setWidthUpdated(Long widthUpdated) {
        this.widthUpdated = widthUpdated;
    }

    public Long getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Long completionDate) {
        this.completionDate = completionDate;
    }

    public String getBodyTypeUpdated() {
        return bodyTypeUpdated;
    }

    public void setBodyTypeUpdated(String bodyTypeUpdated) {
        this.bodyTypeUpdated = bodyTypeUpdated;
    }

    public String getFuelUsedUpdated() {
        return fuelUsedUpdated;
    }

    public void setFuelUsedUpdated(String fuelUsedUpdated) {
        this.fuelUsedUpdated = fuelUsedUpdated;
    }

    public Integer getAlterationCategory() {
        return alterationCategory;
    }

    public void setAlterationCategory(Integer alterationCategory) {
        this.alterationCategory = alterationCategory;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public Long getRlwUpdated() {
		return rlwUpdated;
	}

	public void setRlwUpdated(Long rlwUpdated) {
		this.rlwUpdated = rlwUpdated;
	}

	public Long getUlwUpdated() {
		return ulwUpdated;
	}

	public void setUlwUpdated(Long ulwUpdated) {
		this.ulwUpdated = ulwUpdated;
	}

	public String getColorUpdated() {
		return colorUpdated;
	}

	public void setColorUpdated(String colorUpdated) {
		this.colorUpdated = colorUpdated;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	
}
