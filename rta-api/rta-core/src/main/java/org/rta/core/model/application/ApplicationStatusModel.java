package org.rta.core.model.application;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.rta.core.model.base.SerializableModel;

/**
 * this model conversion of VehicleEntity
 * 
 * @Author sohan.maurya created on Aug 2, 2016.
 */

@XmlRootElement
public class ApplicationStatusModel extends SerializableModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long applicationNo;
    private String firstName;
    private String lastName;
    private String chassisNumber;
    private String makerClass;
    private String invoiceNo;
    private String stepNo;
    private Map<String, String> appApprovalStatus;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getMakerClass() {
        return makerClass;
    }

    public void setMakerClass(String makerClass) {
        this.makerClass = makerClass;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getStepNo() {
        return stepNo;
    }

    public void setStepNo(String stepNo) {
        this.stepNo = stepNo;
    }

    public Map<String, String> getAppApprovalStatus() {
        return appApprovalStatus;
    }

    public void setAppApprovalStatus(Map<String, String> appApprovalStatus) {
        this.appApprovalStatus = appApprovalStatus;
    }

    public Long getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(Long applicationNo) {
        this.applicationNo = applicationNo;
    }

    @Override
    public String toString() {
        return "ApplicationStatusModel [applicationNo=" + applicationNo + ", firstName=" + firstName + ", lastName="
                + lastName + ", chassisNumber=" + chassisNumber + ", makerClass=" + makerClass + ", invoiceNo="
                + invoiceNo + ", stepNo=" + stepNo + ", appApprovalStatus=" + appApprovalStatus + "]";
    }

}
