package org.rta.core.model.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class FinanceOtherServiceModel {

    private String appNo;
    private String appStatus;
    private Integer appStat;
    private String financierName;
    private Long financierId;
    private String prNumber;
    private String serviceCode;
    private Integer serviceType;
    private Long agreementDate;
    private Boolean isTerminated;
    private Boolean isDeclared;
    
    
    
    public String getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	public Integer getAppStat() {
		return appStat;
	}

	public void setAppStat(Integer appStat) {
		this.appStat = appStat;
	}

	public Boolean getIsDeclared() {
		return isDeclared;
	}

	public void setIsDeclared(Boolean isDeclared) {
		this.isDeclared = isDeclared;
	}

	public Long getAgreementDate() {
		return agreementDate;
	}

	public void setAgreementDate(Long agreementDate) {
		this.agreementDate = agreementDate;
	}

	public Boolean getIsTerminated() {
		return isTerminated;
	}

	public void setIsTerminated(Boolean isTerminated) {
		this.isTerminated = isTerminated;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public String getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}

	public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public String getFinancierName() {
        return financierName;
    }

    public void setFinancierName(String financierName) {
        this.financierName = financierName;
    }

    public Long getFinancierId() {
        return financierId;
    }

    public void setFinancierId(Long financierId) {
        this.financierId = financierId;
    }

   
  
}
