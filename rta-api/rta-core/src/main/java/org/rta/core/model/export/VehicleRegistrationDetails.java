package org.rta.core.model.export;

/**
 * Get Vehicle Registration Details after PR Generated
 * 
 * @Author sohan.maurya created on Sep 16, 2016.
 */
public class VehicleRegistrationDetails {
    private String vehicleNumber;
    private String vehicleRegisteredDate;
    private String vehicleRegistrationValidUpto;
    private String vehicleIssuePlace;
    /* Vehicle status Active or Inactive (A/I) A */
    private String vehicleStatus;
    private String remarks;
    /* Vehicle Old No (T/R No., or previous registration Number) */
    private String vehicleOldNo;
    /* If vehicle was registered - previous Registered Office Name */
    private String previousRegisteredOfficeName;
    /* If vehicle was registered - previous Registered Office State */
    private String previousRegisteredOfficeState;
    /* If it is Government Vehicle - govt N */
    private String governmentVehicle;
    /* If it is reserved special No. N */
    private String reservedSpecialNo;
    /* If vehicle is RTC N */
    private String vehicleIsRTC;
    /* office code */
    private String nocOfficeCode;

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleRegisteredDate() {
        return vehicleRegisteredDate;
    }

    public void setVehicleRegisteredDate(String vehicleRegisteredDate) {
        this.vehicleRegisteredDate = vehicleRegisteredDate;
    }

    public String getVehicleRegistrationValidUpto() {
        return vehicleRegistrationValidUpto;
    }

    public void setVehicleRegistrationValidUpto(String vehicleRegistrationValidUpto) {
        this.vehicleRegistrationValidUpto = vehicleRegistrationValidUpto;
    }

    public String getVehicleIssuePlace() {
        return vehicleIssuePlace;
    }

    public void setVehicleIssuePlace(String vehicleIssuePlace) {
        this.vehicleIssuePlace = vehicleIssuePlace;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getVehicleOldNo() {
        return vehicleOldNo;
    }

    public void setVehicleOldNo(String vehicleOldNo) {
        this.vehicleOldNo = vehicleOldNo;
    }

    public String getPreviousRegisteredOfficeName() {
        return previousRegisteredOfficeName;
    }

    public void setPreviousRegisteredOfficeName(String previousRegisteredOfficeName) {
        this.previousRegisteredOfficeName = previousRegisteredOfficeName;
    }

    public String getPreviousRegisteredOfficeState() {
        return previousRegisteredOfficeState;
    }

    public void setPreviousRegisteredOfficeState(String previousRegisteredOfficeState) {
        this.previousRegisteredOfficeState = previousRegisteredOfficeState;
    }

    public String getGovernmentVehicle() {
        return governmentVehicle;
    }

    public void setGovernmentVehicle(String governmentVehicle) {
        this.governmentVehicle = governmentVehicle;
    }

    public String getReservedSpecialNo() {
        return reservedSpecialNo;
    }

    public void setReservedSpecialNo(String reservedSpecialNo) {
        this.reservedSpecialNo = reservedSpecialNo;
    }

    public String getVehicleIsRTC() {
        return vehicleIsRTC;
    }

    public void setVehicleIsRTC(String vehicleIsRTC) {
        this.vehicleIsRTC = vehicleIsRTC;
    }

    public String getNocOfficeCode() {
        return nocOfficeCode;
    }

    public void setNocOfficeCode(String nocOfficeCode) {
        this.nocOfficeCode = nocOfficeCode;
    }

}
