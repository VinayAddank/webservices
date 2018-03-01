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
import org.rta.core.entity.vehicle.VehicleRCEntity;


@Entity
@Table(name="finance_history")
public class FinanceHistoryEntity extends BaseEntity{
	  
	    @Id
	    @Column(name = "id")
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financehistory_seq")
	    @SequenceGenerator(name = "financehistory_seq", sequenceName = "financehistory_seq", allocationSize = 1)
	    private Long financeHistoryId;

	    @Column(name = "token_id")
	    private String tokenId;
	    
	    @NotNull
	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "vehicle_rc_id")
	    private VehicleRCEntity vehicleRcId;

	    @Column(name = "finance_id")
	    private Long financerId;

	    @Column(name = "status")
	    private Integer financeStatus;

	    @Column(name = "comments")
	    private String comments;
	    
	    @Column(name = "service_type")
	    private Integer serviceType;
	    
	    @Column(name = "attachments")
	    private String attachments;


	    
		public String getTokenId() {
			return tokenId;
		}

		public void setTokenId(String tokenId) {
			this.tokenId = tokenId;
		}

		public Long getFinanceHistoryId() {
			return financeHistoryId;
		}

		public void setFinanceHistoryId(Long financeHistoryId) {
			this.financeHistoryId = financeHistoryId;
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

		public Integer getFinanceStatus() {
			return financeStatus;
		}

		public void setFinanceStatus(Integer financeStatus) {
			this.financeStatus = financeStatus;
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

		public String getAttachments() {
			return attachments;
		}

		public void setAttachments(String attachments) {
			this.attachments = attachments;
		}
	    
	 }
