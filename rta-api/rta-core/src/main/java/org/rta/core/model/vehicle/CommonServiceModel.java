package org.rta.core.model.vehicle;

import java.util.List;

import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.model.UserActionModel;
import org.rta.core.model.master.RegistrationCategoryModel;
import org.rta.core.model.payment.tax.TaxModel;

/**
 *	@Author sohan.maurya created on Jan 10, 2017.
 */
public class CommonServiceModel extends RegistrationCategoryModel{

    private String prNumber;
    private Boolean status;
    private Long approvedDate;
    private ServiceType serviceType;
    private String comment;
    private Status suspensionType;
    private Integer suspensionTime;
    private Long startTime;
    private Long endTime;
    private TaxModel taxModel;
    private String reason;
    private String raisedBy;
    private List<UserActionModel> actionModelList;

    public String getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(String prNumber) {
        this.prNumber = prNumber;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

	public Long getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Long approvedDate) {
		this.approvedDate = approvedDate;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}
	
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Status getSuspensionType() {
		return suspensionType;
	}

	public void setSuspensionType(Status suspensionType) {
		this.suspensionType = suspensionType;
	}

	public Integer getSuspensionTime() {
		return suspensionTime;
	}

	public void setSuspensionTime(Integer suspensionTime) {
		this.suspensionTime = suspensionTime;
	}

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

	public TaxModel getTaxModel() {
		return taxModel;
	}

	public void setTaxModel(TaxModel taxModel) {
		this.taxModel = taxModel;
	}

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(String raisedBy) {
        this.raisedBy = raisedBy;
    }

	public List<UserActionModel> getActionModelList() {
		return actionModelList;
	}

	public void setActionModelList(List<UserActionModel> actionModelList) {
		this.actionModelList = actionModelList;
	}

	@Override
	public String toString() {
		return "CommonServiceModel [prNumber=" + prNumber + ", status=" + status + ", approvedDate=" + approvedDate
				+ ", serviceType=" + serviceType + ", comment=" + comment + ", suspensionType=" + suspensionType
				+ ", suspensionTime=" + suspensionTime + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", taxModel=" + taxModel + ", reason=" + reason + ", raisedBy=" + raisedBy + "]";
	}

}
