package org.rta.core.entity.finance;

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
import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.user.UserEntity;

@Entity
@Table(name = "finance_app_status")
public class FinancerAppStatusEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -226623859405782064L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financer_app_status_seq")
	@SequenceGenerator(name = "financer_app_status_seq", sequenceName = "financer_app_status_seq", allocationSize = 1)
	private Long financerAppStatusId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id")
	private UserEntity financerId;

	// @Column(name = "vehicle_rc_id")
	// private Long vehicleRcId;
	//
	// @Column(name = "dealer_id")
	// private Long dealerId;

	@Column(name = "token_id")
	private String tokenId;

	// first time when financer receives request
	@Column(name = "requested_date")
	private Long requestedDate;

	// If dealer selects this financer for final financing
	@Column(name = "finance_status")
	private Integer financeStatus;

	// whenever status changes latest
	@Column(name = "status_changed_date")
	private Long statusChangedDate;

	@Column(name = "sanction_letter_url")
	private String sanctionletter;

	@Column(name = "agrement_letter_url")
	private String agreementletter;

	
	
	public String getAgreementletter() {
		return agreementletter;
	}

	public void setAgreementletter(String agreementletter) {
		this.agreementletter = agreementletter;
	}

	public Long getFinancerAppStatusId() {
		return financerAppStatusId;
	}

	public void setFinancerAppStatusId(Long financerAppStatusId) {
		this.financerAppStatusId = financerAppStatusId;
	}

	public String getSanctionletter() {
		return sanctionletter;
	}

	public void setSanctionletter(String sanctionletter) {
		this.sanctionletter = sanctionletter;
	}

	public UserEntity getFinancerId() {
		return financerId;
	}

	public void setFinancerId(UserEntity financerId) {
		this.financerId = financerId;
	}

	/*
	 * public Long getVehicleRcId() { return vehicleRcId; }
	 * 
	 * public void setVehicleRcId(Long vehicleRcId) { this.vehicleRcId =
	 * vehicleRcId; }
	 * 
	 * public Long getDealerId() { return dealerId; }
	 * 
	 * public void setDealerId(Long dealerId) { this.dealerId = dealerId; }
	 */
	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Long getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Long requested_date) {
		this.requestedDate = requested_date;
	}

	public Integer getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(Integer financeStatus) {
		this.financeStatus = financeStatus;
	}

	public Long getStatusChangedDate() {
		return statusChangedDate;
	}

	public void setStatusChangedDate(Long statusChangedDate) {
		this.statusChangedDate = statusChangedDate;
	}

}
