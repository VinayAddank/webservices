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
@Table(name = "hsrp_detail")
public class HSRPDetailEntity extends BaseEntity {

	private static final long serialVersionUID = 1278293574727208416L;

	@Id
	@Column(name = "hsrp_detail_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hsrp_detail_seq")
	@SequenceGenerator(name = "hsrp_detail_seq", sequenceName = "hsrp_detail_seq", allocationSize = 1)
	private Long hsrp_detail_Id;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRcId;

	@Column(name = "hsrp_transaction_id")
	private Long hsrpTransaction;

	@Column(name = "amount")
	private double amount;

	@Column(name = "transaction_no")
	private String transactionNo;

	@Column(name = "auth_ref_no")
	private String authRefNo;

	@Column(name = "rta_status")
	private Integer rta_status;

	@Column(name = "hsrp_status")
	private Integer hsrp_status;

	@Column(name = "status")
	private Integer status;

	@Column(name = "embossing_date")
	private Long embossingDate;

	@Column(name = "front_laser_code")
	private String frontLaserCode;

	@Column(name = "rear_laser_code")
	private String rearLaserCode;

	@Column(name = "hsrp_availability_date")
	private Long hsrpAvailabilityDate;

	@Column(name = "affixation_date")
	private Long affixationDate;

	@Column(name = "affixation_center_code")
	private String affixationCenterCode;

	@Column(name = "transaction_status")
	private String transactionStatus;

	@Column(name = "payment_recieve_date")
	private Long paymentRecieveDate;

	@Column(name = "order_no")
	private String orderNo;

	@Column(name = "order_date")
	private Long orderDate;

	@Column(name = "message")
	private String message;

	@Column(name = "iteration")
	private Integer iteration;

	public Long getHsrp_detail_Id() {
		return hsrp_detail_Id;
	}

	public void setHsrp_detail_Id(Long hsrp_detail_Id) {
		this.hsrp_detail_Id = hsrp_detail_Id;
	}

	public VehicleRCEntity getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
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

	public Integer getRta_status() {
		return rta_status;
	}

	public void setRta_status(Integer rta_status) {
		this.rta_status = rta_status;
	}

	public Integer getHsrp_status() {
		return hsrp_status;
	}

	public void setHsrp_status(Integer hsrp_status) {
		this.hsrp_status = hsrp_status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getEmbossingDate() {
		return embossingDate;
	}

	public void setEmbossingDate(Long embossingDate) {
		this.embossingDate = embossingDate;
	}

	public String getFrontLaserCode() {
		return frontLaserCode;
	}

	public void setFrontLaserCode(String frontLaserCode) {
		this.frontLaserCode = frontLaserCode;
	}

	public String getRearLaserCode() {
		return rearLaserCode;
	}

	public void setRearLaserCode(String rearLaserCode) {
		this.rearLaserCode = rearLaserCode;
	}

	public Long getHsrpAvailabilityDate() {
		return hsrpAvailabilityDate;
	}

	public void setHsrpAvailabilityDate(Long hsrpAvailabilityDate) {
		this.hsrpAvailabilityDate = hsrpAvailabilityDate;
	}

	public Long getAffixationDate() {
		return affixationDate;
	}

	public void setAffixationDate(Long affixationDate) {
		this.affixationDate = affixationDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Long getPaymentRecieveDate() {
		return paymentRecieveDate;
	}

	public void setPaymentRecieveDate(Long paymentRecieveDate) {
		this.paymentRecieveDate = paymentRecieveDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Long orderDate) {
		this.orderDate = orderDate;
	}

	public String getAffixationCenterCode() {
		return affixationCenterCode;
	}

	public void setAffixationCenterCode(String affixationCenterCode) {
		this.affixationCenterCode = affixationCenterCode;
	}

	public Long getHsrpTransaction() {
		return hsrpTransaction;
	}

	public void setHsrpTransaction(Long hsrpTransaction) {
		this.hsrpTransaction = hsrpTransaction;
	}

	public Integer getIteration() {
		return iteration;
	}

	public void setIteration(Integer iteration) {
		this.iteration = iteration;
	}

}
