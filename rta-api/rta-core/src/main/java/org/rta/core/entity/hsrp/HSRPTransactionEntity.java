package org.rta.core.entity.hsrp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.payment.gateway.TransactionDetailEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name = "hsrp_transaction")
public class HSRPTransactionEntity extends BaseEntity{

    private static final long serialVersionUID = -2201381509110083816L;

    @Id
    @Column(name = "hsrp_transaction_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hsrp_trans_seq")
    @SequenceGenerator(name = "hsrp_trans_seq", sequenceName = "hsrp_trans_seq", allocationSize = 1)
    private Long hsrpTransactionId;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_rc_id")
    private VehicleRCEntity vehicleRcId;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_detail")
    private TransactionDetailEntity transactionDetail;

    @Column(name = "Other_Charges_TID_2")
    private String otherChargesTID;

    @Column(name = "Other_charges_Description_2")
    private String otherChargesDescription;

    @Column(name = "Amount_7")
    private double amount;

    @Column(name = "Credit_Account_7")
    private String creditAccount;

    @Column(name = "status")
    private String status;

    @Column(name = "authorization_code")
    private String authorizationCode;

    @Column(name = "registration_type")
    private int registrationType;

    public Long getHsrpTransactionId() {
        return hsrpTransactionId;
    }

    public void setHsrpTransactionId(Long hsrpTransactionId) {
        this.hsrpTransactionId = hsrpTransactionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public int getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(int registrationType) {
        this.registrationType = registrationType;
    }



}
