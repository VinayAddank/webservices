package org.rta.core.entity.vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.enums.citizen.SlotServiceType;
import org.rta.core.enums.citizen.SlotStatus;

@Entity
@Table(name = "application_slots")
public class ApplicationSlotsEntity extends BaseEntity {

    private static final long serialVersionUID = 4913215932068583204L;

    @Id
    @Column(name = "application_slot_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_slot_seq")
    @SequenceGenerator(name = "application_slot_seq", sequenceName = "application_slot_seq", allocationSize = 1)
    private Long applicationSlotId;
    
    @Column(name = "application_number")
    private String applicationNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_rc_id")
    private VehicleRCEntity vehicleRc;

    @Column(name = "slot_id")
    private Long slotId;

    @Column(name = "start_time")
    private Long startTime;
    
    @Column(name = "end_time")
    private Long endTime;
    
    @Column(name = "scheduled_date")
    private Long scheduledDate;
    
    @Column(name = "scheduled_time")
    private Long scheduledTime;
    
    @Column(name = "service_code")
    private String serviceCode;
    
    @Column(name = "is_enabled")
    private Boolean isEnabled;
    
    @Column(name = "slot_status")
    @Enumerated(value = EnumType.STRING)
    private SlotStatus slotStatus;
    
    @Column(name = "slot_service_type")
    @Enumerated(EnumType.STRING)
    private SlotServiceType slotServiceType;

    public Long getApplicationSlotId() {
        return applicationSlotId;
    }

    public void setApplicationSlotId(Long applicationSlotId) {
        this.applicationSlotId = applicationSlotId;
    }

    public VehicleRCEntity getVehicleRc() {
        return vehicleRc;
    }

    public void setVehicleRc(VehicleRCEntity vehicleRc) {
        this.vehicleRc = vehicleRc;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Long scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Long getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Long scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public SlotServiceType getSlotServiceType() {
        return slotServiceType;
    }

    public void setSlotServiceType(SlotServiceType slotServiceType) {
        this.slotServiceType = slotServiceType;
    }

    public SlotStatus getSlotStatus() {
        return slotStatus;
    }

    public void setSlotStatus(SlotStatus slotStatus) {
        this.slotStatus = slotStatus;
    }

}
