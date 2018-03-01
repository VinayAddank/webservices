package org.rta.core.entity.vehicle;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "vehicle_billing_details")
public class VehicleBillingDetailsEntity extends BaseEntity {

    private static final long serialVersionUID = 7808072084401980887L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_billing_details_seq")
    @SequenceGenerator(name = "vehicle_billing_details_seq", sequenceName = "vehicle_billing_details_seq",
            allocationSize = 1)
    @Column(name = "vehicle_billing_id")
    private Long vehicleBillingId;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_dtl_id")
    private VehicleDetailsEntity vehicleDtlId;

    @NotNull
    @Column(name = "invoice_no")
    private String invoiceNumber;

    @NotNull
    @Column(name = "invoice_date")
    private Long invoiceDate;

    @NotNull
    @Column(name = "invoice_value")
    private double invoiceValue;

    @Column(name = "helmet_invoice_amount")
    private Double helmetInvoiceAmount;

    @Version
    @Column(name = "version" , columnDefinition = "Integer DEFAULT 0", nullable = false)
    private Integer version;
    
    public Long getVehicleBillingId() {
        return vehicleBillingId;
    }

    public void setVehicleBillingId(Long vehicleBillingId) {
        this.vehicleBillingId = vehicleBillingId;
    }

    public VehicleDetailsEntity getVehicleDtlId() {
        return vehicleDtlId;
    }

    public void setVehicleDtlId(VehicleDetailsEntity vehicleDtlId) {
        this.vehicleDtlId = vehicleDtlId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Long invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getInvoiceValue() {
        return invoiceValue;
    }

    public void setInvoiceValue(double invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public Double getHelmetInvoiceAmount() {
        return helmetInvoiceAmount;
    }

    public void setHelmetInvoiceAmount(Double helmetInvoiceAmount) {
        this.helmetInvoiceAmount = helmetInvoiceAmount;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    
}
