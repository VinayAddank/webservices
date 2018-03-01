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
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.user.UserEntity;

@Entity
@Table(name = "finance_yard")
public class FinanceYardEntity extends BaseEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finance_yard_seq")
	@SequenceGenerator(name = "finance_yard_seq", sequenceName = "finance_yard_seq", allocationSize = 1)
	private Long financeYardId;

	@JoinColumn(name = "id")
	private FinanceBranchEntity finnaceBranch;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity headFinancer;

	@Column(name = "name")
	private String name;

	@Column(name = "status")
	private Integer activeStatus;

	@Column(name = "yard_address")
	private String yardAddress;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	private StateEntity state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "district_id")
	private DistrictEntity district;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mandal_id")
	private MandalEntity mandal;

	@Column(name = "city")
	private String city;

	@Column(name = "yard_area")
	private Long yardArea;

	public Long getFinanceYardId() {
		return financeYardId;
	}

	public void setFinanceYardId(Long financeYardId) {
		this.financeYardId = financeYardId;
	}

	public FinanceBranchEntity getFinnaceBranch() {
		return finnaceBranch;
	}

	public void setFinnaceBranch(FinanceBranchEntity finnaceBranch) {
		this.finnaceBranch = finnaceBranch;
	}

	public UserEntity getHeadFinancer() {
		return headFinancer;
	}

	public void setHeadFinancer(UserEntity headFinancer) {
		this.headFinancer = headFinancer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getYardAddress() {
		return yardAddress;
	}

	public void setYardAddress(String yardAddress) {
		this.yardAddress = yardAddress;
	}

	public StateEntity getState() {
		return state;
	}

	public void setState(StateEntity state) {
		this.state = state;
	}

	public DistrictEntity getDistrict() {
		return district;
	}

	public void setDistrict(DistrictEntity district) {
		this.district = district;
	}

	public MandalEntity getMandal() {
		return mandal;
	}

	public void setMandal(MandalEntity mandal) {
		this.mandal = mandal;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getYardArea() {
		return yardArea;
	}

	public void setYardArea(Long yardArea) {
		this.yardArea = yardArea;
	}

	

}
