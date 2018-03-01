package org.rta.core.model.certificate;

import org.rta.core.enums.Status;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CFXModel {

    private Long cfxNoteId;
    private String prNumber;
    private String dlNumber;
    private String driverName;
    private String vcrNumber;
    private String defectNoticed;
    private String placeOfChecking;
    private Integer maximumSpeed;
    private String destination;
    private Boolean isAccident;
    private Boolean cfxEndorseInRC;
    private String cfxNumber;
    private Long userId;
    private String officerName;
    private String officerSignature;
    private Status status;
    private String applicationNumber;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Long getTimeOfChecking() {
        return timeOfChecking;
    }

    public void setTimeOfChecking(Long timeOfChecking) {
        this.timeOfChecking = timeOfChecking;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public String getOfficerSignature() {
        return officerSignature;
    }

    public void setOfficerSignature(String officerSignature) {
        this.officerSignature = officerSignature;
    }



}
