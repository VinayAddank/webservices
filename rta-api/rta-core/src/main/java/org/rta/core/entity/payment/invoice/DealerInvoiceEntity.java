package org.rta.core.entity.payment.invoice;

import javax.persistence.CascadeType;
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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name = "Dealer_invoice")
public class DealerInvoiceEntity extends BaseEntity {

    private static final long serialVersionUID = 5207142240567316930L;

    @Id
    @Column(name = "dealer_invc_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dealer_inv_seq")
    @SequenceGenerator(name = "dealer_inv_seq", sequenceName = "dealer_inv_seq", allocationSize = 1)
    private Long dealerInvcId;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_rc_id" , unique=true)
    private VehicleRCEntity vehicleRcId;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "hsrp_fee")
    private double hsrpFee;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "invoice_amt")
    private double invoiceAmt;

    @Column(name = "invoice_date")
    private Long invoiceDate;

    @Column(name = "status")
    private Integer status;

    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;
    
    public Long getDealerInvcId() {
        return dealerInvcId;
    }

    public void setDealerInvcId(Long dealerInvcId) {
        this.dealerInvcId = dealerInvcId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public VehicleRCEntity getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double total_amount) {
        this.totalAmount = total_amount;
    }

    public double getHsrpFee() {
        return hsrpFee;
    }

    public void setHsrpFee(double hsrpFee) {
        this.hsrpFee = hsrpFee;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Double getInvoiceAmt() {
        return invoiceAmt;
    }

    public void setInvoiceAmt(Double invoiceAmt) {
        this.invoiceAmt = invoiceAmt;
    }

    public Long getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Long invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

 }
