package org.rta.core.model.export;

public class SinkTaxDetails extends RegistrationFeeDetails {

    private String application;
    private String purpose;
    private String officeCode;
    private String districtCode;
    private String transactionDate;
    private String taxType;
    private String penalty;
    private String secondVehicleTax;
    private String TrFee;
    private String hpaFee;
    private String paidAmount;
    private String taxAmount;
    private String crtddt;
    private String trServiceCharge;

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getSecondVehicleTax() {
        return secondVehicleTax;
    }

    public void setSecondVehicleTax(String secondVehicleTax) {
        this.secondVehicleTax = secondVehicleTax;
    }

    public String getTrFee() {
        return TrFee;
    }

    public void setTrFee(String trFee) {
        TrFee = trFee;
    }

    public String getHpaFee() {
        return hpaFee;
    }

    public void setHpaFee(String hpaFee) {
        this.hpaFee = hpaFee;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getCrtddt() {
        return crtddt;
    }

    public void setCrtddt(String crtddt) {
        this.crtddt = crtddt;
    }

    public String getTrServiceCharge() {
        return trServiceCharge;
    }

    public void setTrServiceCharge(String trServiceCharge) {
        this.trServiceCharge = trServiceCharge;
    }

}
