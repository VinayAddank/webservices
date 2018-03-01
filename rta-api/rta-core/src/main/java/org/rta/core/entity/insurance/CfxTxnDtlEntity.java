/**
 * 
 */
package org.rta.core.entity.insurance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

/**
 * @author arun.verma
 *
 */

@Entity
@Table(name = "cfx_txn_dtl")
public class CfxTxnDtlEntity {

    @Id
    @Column(name = "cfx_txn_dtl_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cfx_txn_dtl_seq")
    @SequenceGenerator(name = "cfx_txn_dtl_seq", sequenceName = "cfx_txn_dtl_seq", allocationSize = 1)
    private Long cfxTxnDtlId;

    /**
     * rta transactionId
     */
    @Column(name = "rta_cfx_txn_id", unique = true)
    private String rtaCfxTxnId;

    /**
     * coverfox txn id
     */
    @Column(name = "cfx_txn_id", unique = true)
    private String cfxTxnId;

    /**
     * coverfox payment id
     */
    @Column(name = "cfx_payment_id", unique = true)
    private String cfxPaymentId;

    /**
     * sbi payment reference number(transaction number)
     */
    @Column(name = "pg_payment_token", unique = true)
    private String pgPaymentToken;

    @Column(name = "policy_number", unique = true)
    private String policyNumber;

    @Column(name = "date_time")
    private String dateTime;

    /**
     * policy doc: inprogress or url
     */
    @Column(name = "policy_doc")
    private String policyDoc;

    /**
     * pooling account number
     */
    @Column(name = "pooling_ac_number")
    private String poolingAcNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private double amount;

    /**
     * payment status to trace payment with coverfox
     */
    @Column(name = "payment_status")
    private Integer paymentStatus;

    /**
     * policy status
     */
    @Column(name = "policy_status")
    private Integer policyStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_rc_id")
    private VehicleRCEntity vehicleRcId;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_detail")
    private TransactionDetailEntity transactionDetail;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "customer_phone")
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

    public VehicleRCEntity getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public TransactionDetailEntity getTransactionDetail() {
        return transactionDetail;
    }

    public void setTransactionDetail(TransactionDetailEntity transactionDetail) {
        this.transactionDetail = transactionDetail;
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

}
