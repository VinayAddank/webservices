package org.rta.core.entity.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name="finance_approve_details")
public class FinanceApproveDetailsEntity extends BaseEntity{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finance_approve_details_seq")
	@SequenceGenerator(name = "finance_approve_details_seq", sequenceName = "finance_approve_details_seq", allocationSize = 1)
	private Long financeApproveDetailsId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "finance_other_service")
	private FinancerAppStatusEntity financeAppStatus;

	@Column(name = "approved_date")
	private Long approvedDate;
	
	@Column(name = "first_approver_name")
	private String firstApproverName;
	
	@Column(name = "first_approver_aadhar")
	private String firstApproverAadhar;
	
	@Column(name = "second_approver_name")
	private String secondApproverName;
	
	@Column(name = "second_approver_aadhar")
	private String secondApproverAadhar;

	@Column(name = "aggrement_url"/*,columnDefinition="character varying (300)"*/ )
	private String aggrementUrl;

	public Long getFinanceApproveDetailsId() {
		return financeApproveDetailsId;
	}

	public void setFinanceApproveDetailsId(Long financeApproveDetailsId) {
		this.financeApproveDetailsId = financeApproveDetailsId;
	}

	

	public FinancerAppStatusEntity getFinanceAppStatus() {
		return financeAppStatus;
	}

	public void setFinanceAppStatus(FinancerAppStatusEntity financeAppStatus) {
		this.financeAppStatus = financeAppStatus;
	}

	public Long getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Long approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getFirstApproverName() {
		return firstApproverName;
	}

	public void setFirstApproverName(String firstApproverName) {
		this.firstApproverName = firstApproverName;
	}

	public String getFirstApproverAadhar() {
		return firstApproverAadhar;
	}

	public void setFirstApproverAadhar(String firstApproverAadhar) {
		this.firstApproverAadhar = firstApproverAadhar;
	}

	public String getSecondApproverName() {
		return secondApproverName;
	}

	public void setSecondApproverName(String secondApproverName) {
		this.secondApproverName = secondApproverName;
	}

	public String getSecondApproverAadhar() {
		return secondApproverAadhar;
	}

	public void setSecondApproverAadhar(String secondApproverAadhar) {
		this.secondApproverAadhar = secondApproverAadhar;
	}

	public String getAggrementUrl() {
		return aggrementUrl;
	}

	public void setAggrementUrl(String aggrementUrl) {
		this.aggrementUrl = aggrementUrl;
	}
	

}
