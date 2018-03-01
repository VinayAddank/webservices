package org.rta.core.model.reports.invoice;

import java.io.Serializable;

import org.rta.core.model.certificate.NocDetailsModel;
import org.rta.core.model.certificate.OtherVehicleDetailModel;
import org.rta.core.model.customer.CustomerDetailsRequestModel;
import org.rta.core.model.finance.FinanceModel;
import org.rta.core.model.payment.registfee.RegFeeDetailModel;
import org.rta.core.model.payment.tax.TaxDetailModel;
import org.rta.core.model.vehicle.VehicleBaseModel;

public class VehicleInvoiceModel implements Serializable {

	private static final long serialVersionUID = -53376655105801310L;

	private VehicleBaseModel vehicleBaseModel;
	private CustomerDetailsRequestModel customerDetailsRequestModel;
	private String nationality;
	private String address;
	private String registOffice;
	private String vehicleRcId;
	private String trNo;
	private Long trIssueDate;
	private String prNo;
	private Long prIssueDate;
	private String descOfVehicle;
	private TaxDetailModel taxDetailModel;
	private RegFeeDetailModel regFeeDetailModel;
	private FinanceModel financeModel;
	private Long validFrom;
	private Long validTo;
	private String transactionNo;
	private String invoiceAmt;
	private String hsrp;
	private String vehicleDiscription;
	private String bodyType;
	private String transactionType ;
	private String transactionDate;
	private String fronAxle;
	private String RearAxle;
	private String anyOtherAxle;
	private String tandemAxle;
	private String noOfCylinder;
	private String horsePower;
	private String cubicCapacity;
	private String wheelBase;
	private String grossVehicleWgt;
	private NocDetailsModel nocDetails;
	private OtherVehicleDetailModel  otherNocDetails;
	
	
	public VehicleBaseModel getVehicleBaseModel() {
		return vehicleBaseModel;
	}

	public void setVehicleBaseModel(VehicleBaseModel vehicleBaseModel) {
		this.vehicleBaseModel = vehicleBaseModel;
	}

	public CustomerDetailsRequestModel getCustomerDetailsRequestModel() {
		return customerDetailsRequestModel;
	}

	public void setCustomerDetailsRequestModel(CustomerDetailsRequestModel customerDetailsRequestModel) {
		this.customerDetailsRequestModel = customerDetailsRequestModel;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegistOffice() {
		return registOffice;
	}

	public void setRegistOffice(String registOffice) {
		this.registOffice = registOffice;
	}

	public String getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(String vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public String getTrNo() {
		return trNo;
	}

	public void setTrNo(String trNo) {
		this.trNo = trNo;
	}

	public String getDescOfVehicle() {
		return descOfVehicle;
	}

	public void setDescOfVehicle(String descOfVehicle) {
		this.descOfVehicle = descOfVehicle;
	}

	public TaxDetailModel getTaxDetailModel() {
		return taxDetailModel;
	}

	public void setTaxDetailModel(TaxDetailModel taxDetailModel) {
		this.taxDetailModel = taxDetailModel;
	}

	public RegFeeDetailModel getRegFeeDetailModel() {
		return regFeeDetailModel;
	}

	public void setRegFeeDetailModel(RegFeeDetailModel regFeeDetailModel) {
		this.regFeeDetailModel = regFeeDetailModel;
	}

	public Long getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Long validFrom) {
		this.validFrom = validFrom;
	}

	public Long getValidTo() {
		return validTo;
	}

	public void setValidTo(Long validTo) {
		this.validTo = validTo;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(String invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public FinanceModel getFinanceModel() {
		return financeModel;
	}

	public void setFinanceModel(FinanceModel financeModel) {
		this.financeModel = financeModel;
	}

	public Long getTrIssueDate() {
		return trIssueDate;
	}

	public void setTrIssueDate(Long trIssueDate) {
		this.trIssueDate = trIssueDate;
	}

	public String getPrNo() {
		return prNo;
	}

	public void setPrNo(String prNo) {
		this.prNo = prNo;
	}

	public Long getPrIssueDate() {
		return prIssueDate;
	}

	public void setPrIssueDate(Long prIssueDate) {
		this.prIssueDate = prIssueDate;
	}

	public String getHsrp() {
		return hsrp;
	}

	public void setHsrp(String hsrp) {
		this.hsrp = hsrp;
	}

	public String getVehicleDiscription() {
		return vehicleDiscription;
	}

	public void setVehicleDiscription(String vehicleDiscription) {
		this.vehicleDiscription = vehicleDiscription;
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getFronAxle() {
		return fronAxle;
	}

	public void setFronAxle(String fronAxle) {
		this.fronAxle = fronAxle;
	}

	public String getRearAxle() {
		return RearAxle;
	}

	public void setRearAxle(String rearAxle) {
		RearAxle = rearAxle;
	}

	public String getAnyOtherAxle() {
		return anyOtherAxle;
	}

	public void setAnyOtherAxle(String anyOtherAxle) {
		this.anyOtherAxle = anyOtherAxle;
	}

	public String getTandemAxle() {
		return tandemAxle;
	}

	public void setTandemAxle(String tandemAxle) {
		this.tandemAxle = tandemAxle;
	}

	public String getNoOfCylinder() {
		return noOfCylinder;
	}

	public void setNoOfCylinder(String noOfCylinder) {
		this.noOfCylinder = noOfCylinder;
	}

	public String getHorsePower() {
		return horsePower;
	}

	public void setHorsePower(String horsePower) {
		this.horsePower = horsePower;
	}

	public String getCubicCapacity() {
		return cubicCapacity;
	}

	public void setCubicCapacity(String cubicCapacity) {
		this.cubicCapacity = cubicCapacity;
	}

	public String getWheelBase() {
		return wheelBase;
	}

	public void setWheelBase(String wheelBase) {
		this.wheelBase = wheelBase;
	}

	public String getGrossVehicleWgt() {
		return grossVehicleWgt;
	}

	public void setGrossVehicleWgt(String grossVehicleWgt) {
		this.grossVehicleWgt = grossVehicleWgt;
	}

	public NocDetailsModel getNocDetails() {
		return nocDetails;
	}

	public void setNocDetails(NocDetailsModel nocDetails) {
		this.nocDetails = nocDetails;
	}

	public OtherVehicleDetailModel getOtherNocDetails() {
		return otherNocDetails;
	}

	public void setOtherNocDetails(OtherVehicleDetailModel otherNocDetails) {
		this.otherNocDetails = otherNocDetails;
	}

}
