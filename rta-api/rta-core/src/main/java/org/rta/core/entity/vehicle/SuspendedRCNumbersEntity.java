package org.rta.core.entity.vehicle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.BaseEntity;

@Entity
@Table(name = "suspended_rc_no")
public class SuspendedRCNumbersEntity extends BaseEntity {

	private static final long serialVersionUID = -6756825524937164051L;

	@Id
	@Column(name = "suspended_rc_no_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suspended_rc_no_seq")
	@SequenceGenerator(name = "suspended_rc_no_seq", sequenceName = "suspended_rc_no_seq", allocationSize = 1)
	private Long suspendedRcNumbersId;

	@Column(name = "pr_number")
	private String prNumber;

	@Column(name = "is_revoked")
	private Boolean isRevoked;

	@Column(name = "start_date")
	private Long startDate;

	@Column(name = "end_date")
	private Long endDate;

	@Column(name = "status")
	private Integer status;

	@Column(name = "comment")
	private String comment;

	/**
	 * reason
	 */
	@Column(name = "description", length = 100)
	private String description;
	
	@Column(name = "raised_by", length = 100)
    private String raisedBy;

	@Column(name = "section_rule")
    private String sectionRule;
    
    @Column(name = "source")
    private String source;
	
	public Long getSuspendedRcNumbersId() {
		return suspendedRcNumbersId;
	}

	public void setSuspendedRcNumbersId(Long suspendedRcNumbersId) {
		this.suspendedRcNumbersId = suspendedRcNumbersId;
	}

	public String getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}

	public Boolean getIsRevoked() {
		return isRevoked;
	}

	public void setIsRevoked(Boolean isRevoked) {
		this.isRevoked = isRevoked;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public String getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(String raisedBy) {
        this.raisedBy = raisedBy;
    }

    public String getSectionRule() {
        return sectionRule;
    }

    public void setSectionRule(String sectionRule) {
        this.sectionRule = sectionRule;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
