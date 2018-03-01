package org.rta.core.model.secondVechicle;

import org.rta.core.model.base.BaseModel;
import org.rta.core.utils.ObjectsUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SecondVehicleDetailsModel extends BaseModel {

    private static final long serialVersionUID = 2708107857595670326L;

    private Long vehicleRcId;
    private Long userId;
    private String engineNo;
    private String chassisNo;
    private String registrationNumber;
    private String ownerName;
    private String ownerLastName;
    private String ownerFatherName;
    private String dateOfBirth;
    private String address;
    private String comment;
    private Integer iteration;
    private Boolean paidTax;
    private Boolean isValidSecondVehicle;
    
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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
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

    public void setOwnerFatherName(String fatherName) {
        this.ownerFatherName = fatherName;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonIgnore
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

    @Override
    public String toString() {
        return "SecondVehicleDetailsModel [vehicleRcId=" + vehicleRcId + ", userId=" + userId + ", engineNo=" + engineNo
                + ", chassisNo=" + chassisNo + ", registrationNumber=" + registrationNumber + ", ownerName=" + ownerName
                + ", ownerLastName=" + ownerLastName + ", ownerFatherName=" + ownerFatherName + ", dateOfBirth="
                + dateOfBirth + ", address=" + address + ", comment=" + comment + ", iteration=" + iteration
                + ", paidTax=" + paidTax + "]";
    }

    /**
     * Default value is true
     * @return
     */
    public Boolean getIsValidSecondVehicle() {
    	if(ObjectsUtil.isNull(isValidSecondVehicle)){
    		return true;
    	}
        return isValidSecondVehicle;
    }

    public void setIsValidSecondVehicle(Boolean isValidSecondVehicle) {
        this.isValidSecondVehicle = isValidSecondVehicle;
    }
}
