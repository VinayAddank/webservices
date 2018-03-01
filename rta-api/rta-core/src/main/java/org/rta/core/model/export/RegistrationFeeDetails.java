package org.rta.core.model.export;

/**
 * this is Registration model use within service package to covert data other model
 * 
 * @author sohan.maurya
 *
 */
public class RegistrationFeeDetails {

    private String registrationFees;
    private String postalCharge;
    private String smartCardFees;
    private String serviceCharge;


    public String getRegistrationFees() {
        return registrationFees;
    }

    public void setRegistrationFees(String registrationFees) {
        this.registrationFees = registrationFees;
    }

    public String getPostalCharge() {
        return postalCharge;
    }

    public void setPostalCharge(String postalCharge) {
        this.postalCharge = postalCharge;
    }

    public String getSmartCardFees() {
        return smartCardFees;
    }

    public void setSmartCardFees(String smartCardFees) {
        this.smartCardFees = smartCardFees;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }


}
