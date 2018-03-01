/**
 * 
 */
package org.rta.core.model.insurance;

/**
 * @author arun.verma
 *
 */
public class CfxTxnDtlModel {

    private Long cfxTxnDtlId;
    /**
     * rta transactionId
     */
    private String rtaCfxTxnId;
    /**
     * coverfox txn id
     */
    private String cfxTxnId;
    /**
     * coverfox payment id
     */
    private String cfxPaymentId;
    /**
     * sbi payment reference number(transaction number)
     */
    private String pgPaymentToken;
    private String policyNumber;
    private String dateTime;
    /**
     * policy doc: inprogress or url
     */
    private String policyDoc;
    /**
     * pooling account number
     */
    private String poolingAcNumber;
    private String description;
    private double amount;
    /**
     * payment status to trace payment with coverfox
     */
    private Integer paymentStatus;
    /**
     * policy status
     */
    private Integer policyStatus;
    private Long vehicleRcId;
    private Long transactionDetailId;

    private String customerName;
    private String customerEmail;
    private String customerPhone;

    public Long getCfxTxnDtlId() {
        return cfxTxnDtlId;
    }

    public void setCfxTxnDtlId(Long cfxTxnDtlId) {
        this.cfxTxnDtlId = cfxTxnDtlId;
    }

    public String getRtaCfxTxnId() {
        return rtaCfxTxnId;
    }

    public void setRtaCfxTxnId(String rtaCfxTxnId) {
        this.rtaCfxTxnId = rtaCfxTxnId;
    }

    public String getCfxTxnId() {
        return cfxTxnId;
    }

    public void setCfxTxnId(String cfxTxnId) {
        this.cfxTxnId = cfxTxnId;
    }

    public String getCfxPaymentId() {
        return cfxPaymentId;
    }

    public void setCfxPaymentId(String cfxPaymentId) {
        this.cfxPaymentId = cfxPaymentId;
    }

    public String getPgPaymentToken() {
        return pgPaymentToken;
    }

    public void setPgPaymentToken(String pgPaymentToken) {
        this.pgPaymentToken = pgPaymentToken;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPolicyDoc() {
        return policyDoc;
    }

    public void setPolicyDoc(String policyDoc) {
        this.policyDoc = policyDoc;
    }

    public String getPoolingAcNumber() {
        return poolingAcNumber;
    }

    public void setPoolingAcNumber(String poolingAcNumber) {
        this.poolingAcNumber = poolingAcNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(Integer policyStatus) {
        this.policyStatus = policyStatus;
    }

    public Long getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(Long vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public Long getTransactionDetailId() {
        return transactionDetailId;
    }

    public void setTransactionDetailId(Long transactionDetailId) {
        this.transactionDetailId = transactionDetailId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    @Override
    public String toString() {
        return "CfxTxnDtlModel [cfxTxnDtlId=" + cfxTxnDtlId + ", rtaCfxTxnId=" + rtaCfxTxnId + ", cfxTxnId=" + cfxTxnId
                + ", cfxPaymentId=" + cfxPaymentId + ", pgPaymentToken=" + pgPaymentToken + ", policyNumber="
                + policyNumber + ", dateTime=" + dateTime + ", policyDoc=" + policyDoc + ", poolingAcNumber="
                + poolingAcNumber + ", description=" + description + ", amount=" + amount + ", paymentStatus="
                + paymentStatus + ", policyStatus=" + policyStatus + ", vehicleRcId=" + vehicleRcId
                + ", transactionDetailId=" + transactionDetailId + ", customerName=" + customerName + ", customerEmail="
                + customerEmail + ", customerPhone=" + customerPhone + "]";
    }

}
