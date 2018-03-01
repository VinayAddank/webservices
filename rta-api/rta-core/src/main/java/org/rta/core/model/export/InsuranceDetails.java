package org.rta.core.model.export;

/**
 * Get Vehicle Insurance Details after PR Generated
 * 
 * @Author sohan.maurya created on Sep 16, 2016.
 */
public class InsuranceDetails {

    private String insuranceNo;
    private String insuranceCompanyName;
    private String insuranceValidFrom;
    private String insuranceValidTo;

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public String getInsuranceCompanyName() {
        return insuranceCompanyName;
    }

    public void setInsuranceCompanyName(String insuranceCompanyName) {
        this.insuranceCompanyName = insuranceCompanyName;
    }

    public String getInsuranceValidFrom() {
        return insuranceValidFrom;
    }

    public void setInsuranceValidFrom(String insuranceValidFrom) {
        this.insuranceValidFrom = insuranceValidFrom;
    }

    public String getInsuranceValidTo() {
        return insuranceValidTo;
    }

    public void setInsuranceValidTo(String insuranceValidTo) {
        this.insuranceValidTo = insuranceValidTo;
    }


}
