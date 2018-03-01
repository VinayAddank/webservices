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
import javax.validation.constraints.NotNull;

import org.rta.core.entity.base.BaseEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

@Entity
@Table(name="finance_approve_history")
public class FinanceApprovalHistoryEntity extends BaseEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finance_approval_history_seq")
	@SequenceGenerator(name = "finance_approval_history_seq", sequenceName = "finance_approval_history_seq", allocationSize = 1)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vehicle_rc_id")
	private VehicleRCEntity vehicleRcId;

	@Column(name = "finance_id")
	private Long financerId;

	@Column(name = "status")
	private Integer approverStatus;

	@Column(name = "comments")
	private String comments;

	@Column(name = "service_type")
	private Integer serviceType;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "approver_id", referencedColumnName = "user_id")
	private UserEntity approver_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VehicleRCEntity getVehicleRcId() {
		return vehicleRcId;
	}

	public void setVehicleRcId(VehicleRCEntity vehicleRcId) {
		this.vehicleRcId = vehicleRcId;
	}

	public Long getFinancerId() {
		return financerId;
	}

	public void setFinancerId(Long financerId) {
		this.financerId = financerId;
	}

	public Integer getApproverStatus() {
		return approverStatus;
	}

	public void setApproverStatus(Integer approverStatus) {
		this.approverStatus = approverStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public UserEntity getApprover_id() {
		return approver_id;
	}

	public void setApprover_id(UserEntity approver_id) {
		this.approver_id = approver_id;
	}
	
	

}
