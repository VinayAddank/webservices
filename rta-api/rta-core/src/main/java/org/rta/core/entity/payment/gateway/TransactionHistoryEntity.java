package org.rta.core.entity.payment.gateway;

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
@Table(name = "transaction_history")
public class TransactionHistoryEntity extends BaseEntity {

    private static final long serialVersionUID = -8979737467004206861L;

    @Id
    @Column(name = "trans_hist_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trans_hist_seq")
    @SequenceGenerator(name = "trans_hist_seq", sequenceName = "trans_hist_seq", allocationSize = 1)
    private Long transactionHistoryId;

    @Column(name = "vehicle_rc_id")
    private long vehicleRcId;

    @Column(name = "request_parameter", columnDefinition = "TEXT")
    private String requestParameter;

    @Column(name = "transaction_no")
    private String transactionNo;

    @Column(name = "response_parameter", columnDefinition = "TEXT")
    private String responseParameter;

    @Column(name = "status")
    private Integer status;

    @Column(name = "payment_type")
    private Integer paymentType;
    
    @Column(name = "pg_type")
	private String pgType;

	public Long getTransactionHistoryId() {
		return transactionHistoryId;
	}

	public void setTransactionHistoryId(Long transactionHistoryId) {
		this.transactionHistoryId = transactionHistoryId;
	}

	public long getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(long vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public String getRequestParameter() {
		return requestParameter;
	}

	public void setRequestParameter(String requestParameter) {
		this.requestParameter = requestParameter;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getResponseParameter() {
		return responseParameter;
	}

	public void setResponseParameter(String responseParameter) {
		this.responseParameter = responseParameter;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

    
	public String getPgType() {
		return pgType;
	}

	public void setPgType(String pgType) {
		this.pgType = pgType;
	}

}
