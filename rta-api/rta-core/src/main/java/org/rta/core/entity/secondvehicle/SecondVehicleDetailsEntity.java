package org.rta.core.entity.secondvehicle;

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
import javax.persistence.Version;

import org.rta.core.entity.application.rejection.RejectionHistoryEntity;
import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "second_vehicle_details")
public class SecondVehicleDetailsEntity extends BaseEntity {

    private static final long serialVersionUID = -1960110118288601855L;

    @Id
    @Column(name = "second_vehicle_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "second_vehicle_seq")
    @SequenceGenerator(name = "second_vehicle_seq", sequenceName = "second_vehicle_seq", allocationSize = 1)
    private Long id;

    @Column(name = "vehicle_rc_id", unique = true)
    private Long vehicleRcId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "engine_no")
    private String engineNo;

    @Column(name = "chassis_no")
    private String chassisNo;

    @Column(name = "registration_no")
    private String registrationNumber;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_last_name")
    private String ownerLastName;

    @Column(name = "owner_father_name")
    private String ownerFatherName;

    @Column(name = "dob")
    private String dateOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "iteration")
    private Integer iteration;
    
    @Column(name = "paid_tax")
    private Boolean paidTax;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rejection_history_id")
    private RejectionHistoryEntity rejectionHistory;
    
    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;
    
    @Column(name = "is_valid_second_vehicle", columnDefinition = "Boolean DEFAULT true")
    private Boolean isValidSecondVehicle;
    
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getChassisNo() {
        return chassisNo;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getOwnerFatherName() {
        return ownerFatherName;
    }

    public void setOwnerFatherName(String ownerFatherName) {
        this.ownerFatherName = ownerFatherName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIteration() {
        return iteration;
    }

    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }
    
    public Boolean getPaidTax() {
        return paidTax;
    }

    public void setPaidTax(Boolean paidTax) {
        this.paidTax = paidTax;
    }

    public RejectionHistoryEntity getRejectionHistory() {
        return rejectionHistory;
    }

    public void setRejectionHistory(RejectionHistoryEntity rejectionHistory) {
        this.rejectionHistory = rejectionHistory;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

	public Boolean getIsValidSecondVehicle() {
		return isValidSecondVehicle;
	}

	public void setIsValidSecondVehicle(Boolean isValidSecondVehicle) {
		this.isValidSecondVehicle = isValidSecondVehicle;
	}

}
