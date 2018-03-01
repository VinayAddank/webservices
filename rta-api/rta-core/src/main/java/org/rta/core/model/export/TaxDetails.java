package org.rta.core.model.export;
/**
 *	@Author sohan.maurya created on Sep 16, 2016.
 */
public class TaxDetails {
    
   /* Vehicle is in Tax Exemption (Y/N) */
    private String taxExemption;
    private String taxPaidOfficeCode;
    private String taxPaidStateCode;
    private String taxPaymentPeriod;
    private String taxDemandAmount;
    private String taxPenaltyAmount;
    private String taxCollectedAmount;
    private String taxDemandDate;
    private String taxQuarterStartDate;
    private String taxValidUpto;


    public String getTaxExemption() {
        return taxExemption;
    }

    public void setTaxExemption(String taxExemption) {
        this.taxExemption = taxExemption;
    }

    public String getTaxPaidOfficeCode() {
        return taxPaidOfficeCode;
    }

    public void setTaxPaidOfficeCode(String taxPaidOfficeCode) {
        this.taxPaidOfficeCode = taxPaidOfficeCode;
    }

    public String getTaxPaidStateCode() {
        return taxPaidStateCode;
    }

    public void setTaxPaidStateCode(String taxPaidStateCode) {
        this.taxPaidStateCode = taxPaidStateCode;
    }

    public String getTaxPaymentPeriod() {
        return taxPaymentPeriod;
    }

    public void setTaxPaymentPeriod(String taxPaymentPeriod) {
        this.taxPaymentPeriod = taxPaymentPeriod;
    }

    public String getTaxDemandAmount() {
        return taxDemandAmount;
    }

    public void setTaxDemandAmount(String taxDemandAmount) {
        this.taxDemandAmount = taxDemandAmount;
    }

    public String getTaxPenaltyAmount() {
        return taxPenaltyAmount;
    }

    public void setTaxPenaltyAmount(String taxPenaltyAmount) {
        this.taxPenaltyAmount = taxPenaltyAmount;
    }

    public String getTaxCollectedAmount() {
        return taxCollectedAmount;
    }

    public void setTaxCollectedAmount(String taxCollectedAmount) {
        this.taxCollectedAmount = taxCollectedAmount;
    }

    public String getTaxDemandDate() {
        return taxDemandDate;
    }

    public void setTaxDemandDate(String taxDemandDate) {
        this.taxDemandDate = taxDemandDate;
    }

    public String getTaxQuarterStartDate() {
        return taxQuarterStartDate;
    }

    public void setTaxQuarterStartDate(String taxQuarterStartDate) {
        this.taxQuarterStartDate = taxQuarterStartDate;
    }

    public String getTaxValidUpto() {
        return taxValidUpto;
    }

    public void setTaxValidUpto(String taxValidUpto) {
        this.taxValidUpto = taxValidUpto;
    }

}
