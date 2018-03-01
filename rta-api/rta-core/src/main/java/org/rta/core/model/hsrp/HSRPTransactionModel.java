package org.rta.core.model.hsrp;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class HSRPTransactionModel {

    private Long hsrpTransactionId;
    private String otherChargesTID;
    private String otherChargesDescription;
    private double amount;
    private String creditAccount;

    public Long getHsrpTransactionId() {
        return hsrpTransactionId;
    }

    public void setHsrpTransactionId(Long hsrpTransactionId) {
        this.hsrpTransactionId = hsrpTransactionId;
    }

    public String getOtherChargesTID() {
        return otherChargesTID;
    }

    public void setOtherChargesTID(String otherChargesTID) {
        this.otherChargesTID = otherChargesTID;
    }

    public String getOtherChargesDescription() {
        return otherChargesDescription;
    }

    public void setOtherChargesDescription(String otherChargesDescription) {
        this.otherChargesDescription = otherChargesDescription;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }


}
