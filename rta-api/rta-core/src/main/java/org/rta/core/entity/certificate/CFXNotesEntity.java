package org.rta.core.entity.certificate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseCommonEntity;

@Entity
@Table(name = "cfx_notes")
public class CFXNotesEntity extends BaseCommonEntity {

    private static final long serialVersionUID = -9109959867119055454L;

    @Id
    @Column(name = "cfx_note_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cfx_note_seq")
    @SequenceGenerator(name = "cfx_note_seq", sequenceName = "cfx_note_seq", allocationSize = 1)
    private Long cfxNoteId;
    
    @Column(name = "pr_number")
    private String prNumber;
    
    @Column(name = "dl_number")
    private String dlNumber;
    
    @Column(name = "driver_name")
    private String driverName;
    
    @Column(name = "vcr_number")
    private String vcrNumber;
    
    @Column(name = "defect_noticed")
    private String defectNoticed;
    
    @Column(name = "place_of_checking")
    private String placeOfChecking;
    
    @Column(name = "max_speed")
    private Integer maximumSpeed;
    
    @Column(name = "destination")
    private String destination;
    
    @Column(name = "is_accident")
    private Boolean isAccident;
    
    @Column(name = "is_cfx_endorse_in_rc")
    private Boolean cfxEndorseInRC;
    
    @Column(name = "cfx_number")
    private String cfxNumber;
    
    @Column(name = "application_number")
    private String applicationNumber;
    
    @Column(name = "status")
    private Integer status;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "time_of_checking")
    private Long timeOfChecking;

    public Long getCfxNoteId() {
        return cfxNoteId;
    }

    public void setCfxNoteId(Long cfxNoteId) {
        this.cfxNoteId = cfxNoteId;
    }

    public String getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(String prNumber) {
        this.prNumber = prNumber;
    }

    public String getDlNumber() {
        return dlNumber;
    }

    public void setDlNumber(String dlNumber) {
        this.dlNumber = dlNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVcrNumber() {
        return vcrNumber;
    }

    public void setVcrNumber(String vcrNumber) {
        this.vcrNumber = vcrNumber;
    }

    public String getDefectNoticed() {
        return defectNoticed;
    }

    public void setDefectNoticed(String defectNoticed) {
        this.defectNoticed = defectNoticed;
    }

    public String getPlaceOfChecking() {
        return placeOfChecking;
    }

    public void setPlaceOfChecking(String placeOfChecking) {
        this.placeOfChecking = placeOfChecking;
    }

    public Integer getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(Integer maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Boolean getIsAccident() {
        return isAccident;
    }

    public void setIsAccident(Boolean isAccident) {
        this.isAccident = isAccident;
    }

    public Boolean getCfxEndorseInRC() {
        return cfxEndorseInRC;
    }

    public void setCfxEndorseInRC(Boolean cfxEndorseInRC) {
        this.cfxEndorseInRC = cfxEndorseInRC;
    }

    public String getCfxNumber() {
        return cfxNumber;
    }

    public void setCfxNumber(String cfxNumber) {
        this.cfxNumber = cfxNumber;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTimeOfChecking() {
        return timeOfChecking;
    }

    public void setTimeOfChecking(Long timeOfChecking) {
        this.timeOfChecking = timeOfChecking;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
}
