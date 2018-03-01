package org.rta.core.entity.payment.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "sbi_distribution")
public class SBIDistributionEntity extends BaseEntity {

	private static final long serialVersionUID = -5789082949150620137L;

	@Id
	@Column(name = "sbi_dist_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sbi_dist_seq")
	@SequenceGenerator(name = "sbi_dist_seq", sequenceName = "sbi_dist_seq", allocationSize = 1)
	private Long sbiDistId;

	@Column(name = "major_head")
	private String majorHead;

	@Column(name = "major_sub_head")
	private String majorSubHead;

	@Column(name = "minor_sub_head")
	private String minorSubHead;

	@Column(name = "group_sub_head")
	private String groupSubHead;

	@Column(name = "sub_head")
	private String subHead;

	@Column(name = "detailed_head")
	private String detailedHead;

	@Column(name = "sub_detailed_head")
	private String subDetailedHead;
	
	@Column(name = "head_end_code")
	private String headEndCode;
	
	@Column(name = "head_type")
	private String headType;

	@Column(name = "credit_polling_account")
	private String creditPollingAccount;

	@Column(name = "hoa_description")
	private String hoaDescription;

	@Column(name = "status")
	private Integer status;

	public Long getSbiDistId() {
		return sbiDistId;
	}

	public void setSbiDistId(Long sbiDistId) {
		this.sbiDistId = sbiDistId;
	}

	public String getMajorHead() {
		return majorHead;
	}

	public void setMajorHead(String majorHead) {
		this.majorHead = majorHead;
	}

	public String getMajorSubHead() {
		return majorSubHead;
	}

	public void setMajorSubHead(String majorSubHead) {
		this.majorSubHead = majorSubHead;
	}

	public String getMinorSubHead() {
		return minorSubHead;
	}

	public void setMinorSubHead(String minorSubHead) {
		this.minorSubHead = minorSubHead;
	}

	public String getGroupSubHead() {
		return groupSubHead;
	}

	public void setGroupSubHead(String groupSubHead) {
		this.groupSubHead = groupSubHead;
	}

	public String getSubHead() {
		return subHead;
	}

	public void setSubHead(String subHead) {
		this.subHead = subHead;
	}

	public String getDetailedHead() {
		return detailedHead;
	}

	public void setDetailedHead(String detailedHead) {
		this.detailedHead = detailedHead;
	}

	public String getSubDetailedHead() {
		return subDetailedHead;
	}

	public void setSubDetailedHead(String subDetailedHead) {
		this.subDetailedHead = subDetailedHead;
	}

	public String getHeadEndCode() {
		return headEndCode;
	}

	public void setHeadEndCode(String headEndCode) {
		this.headEndCode = headEndCode;
	}

	public String getHeadType() {
		return headType;
	}

	public void setHeadType(String headType) {
		this.headType = headType;
	}

	public String getCreditPollingAccount() {
		return creditPollingAccount;
	}

	public void setCreditPollingAccount(String creditPollingAccount) {
		this.creditPollingAccount = creditPollingAccount;
	}

	public String getHoaDescription() {
		return hoaDescription;
	}

	public void setHoaDescription(String hoaDescription) {
		this.hoaDescription = hoaDescription;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
}	