package org.rta.core.model.payment.tax;

import javax.xml.bind.annotation.XmlRootElement;

import org.rta.core.model.master.TaxMasterModel;
import org.rta.core.model.master.TaxTypeModel;
import org.rta.core.model.payment.registfee.RegFeeDetailModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class TaxDetailModel {

	private Long taxDtlId;
	private String custName;
	private String invoiceNo;
	private Long invoiceDate;
	private String invoiceAmt;
	private String taxType;
	private String taxPercentage;
	private String taxAmt;
	private String penalty;
	private String hpaFee;
	private String trFee;
	private String trServiceCharge;
	private String totalAmt;
	private String hsrpFee;
	private String grandTotal;
	private String vehicleRcId;
	private RegFeeDetailModel regFeeDetailModel;
	private String totalTaxAmt;
    private int status;
	private Long taxValidUpto;
	private String cessFee;
	private String secondVehicleTaxAmt;
	private String secondVehicleTax;
	private boolean paySecondVehicle;
	private Boolean isSBIVerification;
	private Boolean isPayUVerification;

	private int sbiVerifyStatus ;


	public Long getTaxDtlId() {
		return taxDtlId;
	}

	public void setTaxDtlId(Long taxDtlId) {
		this.taxDtlId = taxDtlId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Long getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Long invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(String invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(String taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public String getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(String taxAmt) {
		this.taxAmt = taxAmt;
	}

	public String getPenalty() {
		return penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public String getHpaFee() {
		return hpaFee;
	}

	public void setHpaFee(String hpaFee) {
		this.hpaFee = hpaFee;
	}

	public String getTrFee() {
		return trFee;
	}

	public void setTrFee(String trFee) {
		this.trFee = trFee;
	}

	public String getTrServiceCharge() {
		return trServiceCharge;
	}

	public void setTrServiceCharge(String trServiceCharge) {
		this.trServiceCharge = trServiceCharge;
	}

	public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getHsrpFee() {
		return hsrpFee;
	}

	public void setHsrpFee(String hsrpFee) {
		this.hsrpFee = hsrpFee;
	}

	public String getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(String vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public RegFeeDetailModel getRegFeeDetailModel() {
		return regFeeDetailModel;
	}

	public void setRegFeeDetailModel(RegFeeDetailModel regFeeDetailModel) {
		this.regFeeDetailModel = regFeeDetailModel;
	}

	public String getTotalTaxAmt() {
		return totalTaxAmt;
	}

	public void setTotalTaxAmt(String totalTaxAmt) {
		this.totalTaxAmt = totalTaxAmt;
	}

	public Long getTaxValidUpto() {
		return taxValidUpto;
	}

	public void setTaxValidUpto(Long taxValidUpto) {
		this.taxValidUpto = taxValidUpto;
	}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

	public String getCessFee() {
		return cessFee;
	}

	public void setCessFee(String cessFee) {
		this.cessFee = cessFee;
	}
	public String getSecondVehicleTaxAmt() {
		return secondVehicleTaxAmt;
	}

	public void setSecondVehicleTaxAmt(String secondVehicleTaxAmt) {
		this.secondVehicleTaxAmt = secondVehicleTaxAmt;
	}

	public String getSecondVehicleTax() {
		return secondVehicleTax;
	}

	public void setSecondVehicleTax(String secondVehicleTax) {
		this.secondVehicleTax = secondVehicleTax;
	}

	public boolean paySecondVehicle() {
		return paySecondVehicle;
	}

	public void setPaySecondVehicle(boolean paySecondVehicle) {
		this.paySecondVehicle = paySecondVehicle;
	}


	public Boolean getIsSBIVerification() {
		return isSBIVerification;
	}

	public void setIsSBIVerification(Boolean isSBIVerification) {
		this.isSBIVerification = isSBIVerification;
	}

	public Boolean getIsPayUVerification() {
		return isPayUVerification;
	}

	public void setIsPayUVerification(Boolean isPayUVerification) {
		this.isPayUVerification = isPayUVerification;
	}
	


	public int getSbiVerifyStatus() {
		return sbiVerifyStatus;
	}

	public void setSbiVerifyStatus(int sbiVerifyStatus) {
		this.sbiVerifyStatus = sbiVerifyStatus;
	}


}
