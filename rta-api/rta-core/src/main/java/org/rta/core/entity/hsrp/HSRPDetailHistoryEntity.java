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
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name = "hsrp_detail_history")
public class HSRPDetailHistoryEntity extends BaseEntity{

    private static final long serialVersionUID = 55235489902764709L;


    @Id
    @Column(name = "hsrp_detail_hist_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hsrp_detail_hist_seq")
    @SequenceGenerator(name = "hsrp_detail_hist_seq", sequenceName = "hsrp_detail_hist_seq", allocationSize = 1)
    private Long hsrp_detail_hist_Id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_rc_id")
    private VehicleRCEntity vehicleRc;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "hsrp_detail_id")
    private HSRPDetailEntity hsrp_detail;

    @Column(name = "amount")
    private double amount;

    @Column(name = "transaction_no")
    private String transactionNo;

    @Column(name = "auth_ref_no")
    private String authRefNo;

    @Column(name = "status")
    private Integer status;

    @Column(name = "request_parameter")
    private String requestParameter;

    @Column(name = "response_parameter")
    private String responseParameter;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @Column(name = "order_no")
    private String orderNo;

    public Long getHsrp_detail_hist_Id() {
        return hsrp_detail_hist_Id;
    }

    public void setHsrp_detail_hist_Id(Long hsrp_detail_hist_Id) {
        this.hsrp_detail_hist_Id = hsrp_detail_hist_Id;
    }

    public VehicleRCEntity getVehicleRc() {
        return vehicleRc;
    }

    public void setVehicleRc(VehicleRCEntity vehicleRc) {
        this.vehicleRc = vehicleRc;
    }

    public HSRPDetailEntity getHsrp_detail() {
        return hsrp_detail;
    }

    public void setHsrp_detail(HSRPDetailEntity hsrp_detail) {
        this.hsrp_detail = hsrp_detail;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getAuthRefNo() {
        return authRefNo;
    }

    public void setAuthRefNo(String authRefNo) {
        this.authRefNo = authRefNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRequestParameter() {
        return requestParameter;
    }

    public void setRequestParameter(String requestParameter) {
        this.requestParameter = requestParameter;
    }

    public String getResponseParameter() {
        return responseParameter;
    }

    public void setResponseParameter(String responseParameter) {
        this.responseParameter = responseParameter;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }



}
