package org.rta.core.model.payment.invoice;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.rta.core.entity.payment.registfee.RegFeeDetailsEntity;
import org.rta.core.entity.user.DealerEntity;

@XmlRootElement
public class DealerInvoiceModel {

    private Long dealerInvcId;
    private DealerEntity dealerId;
    private RegFeeDetailsEntity regFeeDtlId;
    private Double total_amount;
    private double hsrpFee;
    private double invoiceNo;
    private Double invoiceAmt;
    private Date invoiceDate;
    private Boolean status;

    public Long getDealerInvcId() {
        return dealerInvcId;
    }

    public void setDealerInvcId(Long dealerInvcId) {
        this.dealerInvcId = dealerInvcId;
    }

    public DealerEntity getDealerId() {
        return dealerId;
    }

    public void setDealerId(DealerEntity dealerId) {
        this.dealerId = dealerId;
    }

    public RegFeeDetailsEntity getRegFeeDtlId() {
        return regFeeDtlId;
    }

    public void setRegFeeDtlId(RegFeeDetailsEntity regFeeDtlId) {
        this.regFeeDtlId = regFeeDtlId;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public double getHsrpFee() {
        return hsrpFee;
    }

    public void setHsrpFee(double hsrpFee) {
        this.hsrpFee = hsrpFee;
    }

    public double getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(double invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Double getInvoiceAmt() {
        return invoiceAmt;
    }

    public void setInvoiceAmt(Double invoiceAmt) {
        this.invoiceAmt = invoiceAmt;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


}
