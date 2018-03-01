package org.rta.core.model.citizen;

import java.util.List;

import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.model.base.BaseModel;
import org.rta.core.enums.citizen.KeyType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CitizenApplicationModel extends BaseModel {

    private static final long serialVersionUID = -7778202924412619697L;

    private Long appId;
    private String applicationNumber;
    private ServiceType serviceType;
    private String serviceTypeText;
    private Long sessionId;
    private Integer iteration;
    private String firstName;
    private String surName;
    private String fatherName;
    private String vehicleClass;
    private Status appStatus;
    private String uniqueKey;
    private KeyType keyType;
    private List<SlotModel> slot;

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getIteration() {
        return iteration;
    }

    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(String vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public Status getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Status appStatus) {
        this.appStatus = appStatus;
    }

    public String getServiceTypeText() {
        return serviceTypeText;
    }

    public void setServiceTypeText(String serviceTypeText) {
        this.serviceTypeText = serviceTypeText;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public KeyType getKeyType() {
        return keyType;
    }

    public void setKeyType(KeyType keyType) {
        this.keyType = keyType;
    }

    public List<SlotModel> getSlot() {
        return slot;
    }

    public void setSlot(List<SlotModel> slot) {
        this.slot = slot;
    }

}
