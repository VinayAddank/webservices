package org.rta.core.entity.payment.tax;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;

	@Entity
	@Table(name = "tax_detail_history")
	public class TaxDetailHistoryEntity extends BaseEntity {

	private static final long serialVersionUID = -9175161210873425047L;

	@Id
	@Column(name = "tax_hist_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_hist_seq")
	@SequenceGenerator(name = "tax_hist_seq", sequenceName = "tax_hist_seq", allocationSize = 1)
	private Long taxHistId;

	@NotNull
	@Column(name = "vehicle_rc_id")
	private Long vehicleRcId;

	@NotNull
	@Column(name = "tax_type_id")
	private Long taxType;

	@NotNull
	@Column(name = "tax_master_id")
	private Long taxMasterType;

	@Column(name = "tax_amt")
	private double taxAmt;

	@Column(name = "penalty")
	private double penalty;

	@Column(name = "hpa_fee")
	private double hpaFee;

	@Column(name = "tr_fee")
	private double trFee;

	@Column(name = "tr_service_charge")
	private double trServiceCharge;

	@Column(name = "total_amt")
	private double totalAmt;

	@Column(name = "second_vehicle_tax")
	private double secondVehicleTax;

	@Column(name = "second_vehicle_tax_amt")
	private double secondVehicleTaxAmt;

	@Column(name = "second_vehicle_tax_on")
	private Long secondVehicleTaxOn;

	@Column(name = "hsrpFee")
	private double hsrpFee;

	@Column(name = "tax_valid_upto")
	private Long taxValidUpto;

    @Column(name = "cess_fee")
	private double cessFee;
    
    @Column(name = "cessFee_valid_upto")
	private Long cessFeeValidUpto;

    @Column(name = "columnregistration_type")
    private Integer columnregistrationType;
    
    @Column(name = "exe_flg", length = 1)
    private Character exeFlg;
    
    @Column(name = "pms_remarks", length = 1000)
    private String pmsRemarks;
    
    @Column(name = "releaseorder_fee")
    private BigDecimal releaseorderFee;
    
    @Column(name = "lttent_tdt")
    private Long lttentTdt;
    
    @Column(name = "lttent_fdt")
    private Long lttentFdt;
    
    @Column(name = "lttenttax_amnt")
    private BigDecimal lttenttaxAmnt;
    
    @Column(name = "gt_flag" , length=1)
    private Character gtFlag;
    
    @Column(name = "demandtax_arrears")
    private BigDecimal demandtaxArrears;
    
    @Column(name = "twotire" , length=1)
    private Character twoTire;
    
    @Column(name = "vcr_no" , length=35)
    private String vcrNo;
    
    @Column(name = "demandpenalty_arrears")
    private BigDecimal demandpenaltyArrears;
    
    @Column(name = "refundable_flg" , length=1)
    private Character refundableFlg;
    
    @Column(name = "tax_pymntlastdate" )
    private Long taxPymntlastdate;
    
    @Column(name = "remarks" , length=1000)
    private String remarks;
    
    @Column(name = "exmpt_flg" , length=1)
    private Character exmptFlg;
    
    @Column(name = "pymnt_start_dt" )
    private Long pymntStartDt;
    
    @Column(name = "refund_done_flg" , length=1)
    private Character refundDoneFlg;
    
    @Column(name = "court_flg" , length=1)
    private String courtFlg;
    
    @Column(name = "appln_no" , length=19)
    private String applnNo;
    
    @Column(name = "go_no" , length=20)
    private String goNo;
    
    @Column(name = "transactn_status" , length=1)
    private String transactnStatus;
    
    @Column(name = "proportionate_amt" )
    private BigDecimal proportionate_amt;
    
    @Column(name = "paymt_dt" )
    private Long paymtDt;
    
    @Column(name = "balance_amt")
    private BigDecimal balanceAmt;
    
    @Column(name = "permit_type" , length=5)
    private String permitType;
    
    @Column(name = "demand_dt" )
    private Long demandDt;
    
    @Column(name = "class_of_veh" , length=10)
    private String classOfVeh;
    
    @Column(name = "late_fee_amt" )
    private BigDecimal lateFeeAmt;
    
    @Column(name = "regn_no" , length=32)
    private String regnNo;
    
    @Column(name = "permit_no" , length=31)
    private String permitNo;
    
    @Column(name = "compound_fee" )
    private BigDecimal compoundFee;
    
    @Column(name = "chalan_no" , length=50)
    private String chalanNo;
    
    @Column(name = "office_cd" )
    private Long officeCd;
    
    @Column(name = "demand_amt" )
    private BigDecimal demandAmt;
    
    @Column(name = "green_tax_amt" )
	private double greenTaxAmt;

	@Column(name = "green_tax_valid_to")
	private Long greenTaxValidTo;

    
	
	public Long getTaxHistId() {
		return taxHistId;
	}

	public void setTaxHistId(Long taxHistId) {
		this.taxHistId = taxHistId;
	}

	public Long getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(Long vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public Long getTaxType() {
		return taxType;
	}

	public void setTaxType(Long taxType) {
		this.taxType = taxType;
	}

	public Long getTaxMasterType() {
		return taxMasterType;
	}

	public void setTaxMasterType(Long taxMasterType) {
		this.taxMasterType = taxMasterType;
	}

	public double getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(double taxAmt) {
		this.taxAmt = taxAmt;
	}

	public double getPenalty() {
		return penalty;
	}

	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}

	public double getHpaFee() {
		return hpaFee;
	}

	public void setHpaFee(double hpaFee) {
		this.hpaFee = hpaFee;
	}

	public double getTrFee() {
		return trFee;
	}

	public void setTrFee(double trFee) {
		this.trFee = trFee;
	}

	public double getTrServiceCharge() {
		return trServiceCharge;
	}

	public void setTrServiceCharge(double trServiceCharge) {
		this.trServiceCharge = trServiceCharge;
	}

	public double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public double getSecondVehicleTax() {
		return secondVehicleTax;
	}

	public void setSecondVehicleTax(double secondVehicleTax) {
		this.secondVehicleTax = secondVehicleTax;
	}

	public double getSecondVehicleTaxAmt() {
		return secondVehicleTaxAmt;
	}

	public void setSecondVehicleTaxAmt(double secondVehicleTaxAmt) {
		this.secondVehicleTaxAmt = secondVehicleTaxAmt;
	}

	public Long getSecondVehicleTaxOn() {
		return secondVehicleTaxOn;
	}

	public void setSecondVehicleTaxOn(Long secondVehicleTaxOn) {
		this.secondVehicleTaxOn = secondVehicleTaxOn;
	}

	public double getHsrpFee() {
		return hsrpFee;
	}

	public void setHsrpFee(double hsrpFee) {
		this.hsrpFee = hsrpFee;
	}

	public Long getTaxValidUpto() {
		return taxValidUpto;
	}

	public void setTaxValidUpto(Long taxValidUpto) {
		this.taxValidUpto = taxValidUpto;
	}

    
	public double getCessFee() {
		return cessFee;
	}

	public void setCessFee(double cessFee) {
		this.cessFee = cessFee;
	}

	public Long getCessFeeValidUpto() {
		return cessFeeValidUpto;
	}

	public void setCessFeeValidUpto(Long cessFeeValidUpto) {
		this.cessFeeValidUpto = cessFeeValidUpto;
	}

	public Integer getColumnregistrationType() {
		return columnregistrationType;
	}

	public void setColumnregistrationType(Integer columnregistrationType) {
		this.columnregistrationType = columnregistrationType;
	}

	public Character getExeFlg() {
		return exeFlg;
	}

	public void setExeFlg(Character exeFlg) {
		this.exeFlg = exeFlg;
	}

	public String getPmsRemarks() {
		return pmsRemarks;
	}

	public void setPmsRemarks(String pmsRemarks) {
		this.pmsRemarks = pmsRemarks;
	}

	public BigDecimal getReleaseorderFee() {
		return releaseorderFee;
	}

	public void setReleaseorderFee(BigDecimal releaseorderFee) {
		this.releaseorderFee = releaseorderFee;
	}

	public Long getLttentTdt() {
		return lttentTdt;
	}

	public void setLttentTdt(Long lttentTdt) {
		this.lttentTdt = lttentTdt;
	}

	public Long getLttentFdt() {
		return lttentFdt;
	}

	public void setLttentFdt(Long lttentFdt) {
		this.lttentFdt = lttentFdt;
	}

	public BigDecimal getLttenttaxAmnt() {
		return lttenttaxAmnt;
	}

	public void setLttenttaxAmnt(BigDecimal lttenttaxAmnt) {
		this.lttenttaxAmnt = lttenttaxAmnt;
	}

	public Character getGtFlag() {
		return gtFlag;
	}

	public void setGtFlag(Character gtFlag) {
		this.gtFlag = gtFlag;
	}

	public BigDecimal getDemandtaxArrears() {
		return demandtaxArrears;
	}

	public void setDemandtaxArrears(BigDecimal demandtaxArrears) {
		this.demandtaxArrears = demandtaxArrears;
	}

	public Character getTwoTire() {
		return twoTire;
	}

	public void setTwoTire(Character twoTire) {
		this.twoTire = twoTire;
	}

	public String getVcrNo() {
		return vcrNo;
	}

	public void setVcrNo(String vcrNo) {
		this.vcrNo = vcrNo;
	}

	public BigDecimal getDemandpenaltyArrears() {
		return demandpenaltyArrears;
	}

	public void setDemandpenaltyArrears(BigDecimal demandpenaltyArrears) {
		this.demandpenaltyArrears = demandpenaltyArrears;
	}

	public Character getRefundableFlg() {
		return refundableFlg;
	}

	public void setRefundableFlg(Character refundableFlg) {
		this.refundableFlg = refundableFlg;
	}

	public Long getTaxPymntlastdate() {
		return taxPymntlastdate;
	}

	public void setTaxPymntlastdate(Long taxPymntlastdate) {
		this.taxPymntlastdate = taxPymntlastdate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Character getExmptFlg() {
		return exmptFlg;
	}

	public void setExmptFlg(Character exmptFlg) {
		this.exmptFlg = exmptFlg;
	}

	public Long getPymntStartDt() {
		return pymntStartDt;
	}

	public void setPymntStartDt(Long pymntStartDt) {
		this.pymntStartDt = pymntStartDt;
	}

	public Character getRefundDoneFlg() {
		return refundDoneFlg;
	}

	public void setRefundDoneFlg(Character refundDoneFlg) {
		this.refundDoneFlg = refundDoneFlg;
	}

	public String getCourtFlg() {
		return courtFlg;
	}

	public void setCourtFlg(String courtFlg) {
		this.courtFlg = courtFlg;
	}

	public String getApplnNo() {
		return applnNo;
	}

	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}

	public String getGoNo() {
		return goNo;
	}

	public void setGoNo(String goNo) {
		this.goNo = goNo;
	}

	public String getTransactnStatus() {
		return transactnStatus;
	}

	public void setTransactnStatus(String transactnStatus) {
		this.transactnStatus = transactnStatus;
	}

	public BigDecimal getProportionate_amt() {
		return proportionate_amt;
	}

	public void setProportionate_amt(BigDecimal proportionate_amt) {
		this.proportionate_amt = proportionate_amt;
	}

	public Long getPaymtDt() {
		return paymtDt;
	}

	public void setPaymtDt(Long paymtDt) {
		this.paymtDt = paymtDt;
	}

	public BigDecimal getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(BigDecimal balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public String getPermitType() {
		return permitType;
	}

	public void setPermitType(String permitType) {
		this.permitType = permitType;
	}

	public Long getDemandDt() {
		return demandDt;
	}

	public void setDemandDt(Long demandDt) {
		this.demandDt = demandDt;
	}

	public String getClassOfVeh() {
		return classOfVeh;
	}

	public void setClassOfVeh(String classOfVeh) {
		this.classOfVeh = classOfVeh;
	}

	public BigDecimal getLateFeeAmt() {
		return lateFeeAmt;
	}

	public void setLateFeeAmt(BigDecimal lateFeeAmt) {
		this.lateFeeAmt = lateFeeAmt;
	}

	public String getRegnNo() {
		return regnNo;
	}

	public void setRegnNo(String regnNo) {
		this.regnNo = regnNo;
	}

	public String getPermitNo() {
		return permitNo;
	}

	public void setPermitNo(String permitNo) {
		this.permitNo = permitNo;
	}

	public BigDecimal getCompoundFee() {
		return compoundFee;
	}

	public void setCompoundFee(BigDecimal compoundFee) {
		this.compoundFee = compoundFee;
	}

	public String getChalanNo() {
		return chalanNo;
	}

	public void setChalanNo(String chalanNo) {
		this.chalanNo = chalanNo;
	}

	public Long getOfficeCd() {
		return officeCd;
	}

	public void setOfficeCd(Long officeCd) {
		this.officeCd = officeCd;
	}

	public BigDecimal getDemandAmt() {
		return demandAmt;
	}

	public void setDemandAmt(BigDecimal demandAmt) {
		this.demandAmt = demandAmt;
	}

	public double getGreenTaxAmt() {
		return greenTaxAmt;
	}

	public void setGreenTaxAmt(double greenTaxAmt) {
		this.greenTaxAmt = greenTaxAmt;
	}

	public Long getGreenTaxValidTo() {
		return greenTaxValidTo;
	}

	public void setGreenTaxValidTo(Long greenTaxValidTo) {
		this.greenTaxValidTo = greenTaxValidTo;
	}
}
