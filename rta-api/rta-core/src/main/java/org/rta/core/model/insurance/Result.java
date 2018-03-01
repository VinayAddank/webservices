package org.rta.core.model.insurance;

public class Result
{
    private String insurerCode;
    
    private String insurerName;
    
    private String actStatus;

    private String engineNo;

    private String policyDetailsId;

    private String mappingDoneOn;

    private String policyStatus;

    private String policyNo;

    private String  vehicleRcId;

    private String chasisNo;

    private String regNo;

    private String regIssueDate;

    private String policyStartdate;

    private String policyEndDate;

    public String getInsurerCode ()
    {
        return insurerCode;
    }

    public void setInsurerCode (String insurerCode)
    {
        this.insurerCode = insurerCode;
    }

    public String getActStatus ()
    {
        return actStatus;
    }

    public void setActStatus (String actStatus)
    {
        this.actStatus = actStatus;
    }

    public String getEngineNo ()
    {
        return engineNo;
    }

    public void setEngineNo (String engineNo)
    {
        this.engineNo = engineNo;
    }

    public String getPolicyDetailsId ()
    {
        return policyDetailsId;
    }

    public void setPolicyDetailsId (String policyDetailsId)
    {
        this.policyDetailsId = policyDetailsId;
    }

    public String getMappingDoneOn ()
    {
        return mappingDoneOn;
    }

    public void setMappingDoneOn (String mappingDoneOn)
    {
        this.mappingDoneOn = mappingDoneOn;
    }

    public String getPolicyStatus ()
    {
        return policyStatus;
    }

    public void setPolicyStatus (String policyStatus)
    {
        this.policyStatus = policyStatus;
    }

    public String getPolicyNo ()
    {
        return policyNo;
    }

    public void setPolicyNo (String policyNo)
    {
        this.policyNo = policyNo;
    }

    public String  getVehicleRcId ()
    {
        return vehicleRcId;
    }

    public void setVehicleRcId (String  vehicleRcId)
    {
        this.vehicleRcId = vehicleRcId;
    }

    public String getChasisNo ()
    {
        return chasisNo;
    }

    public void setChasisNo (String chasisNo)
    {
        this.chasisNo = chasisNo;
    }

    public String getRegNo ()
    {
        return regNo;
    }

    public void setRegNo (String regNo)
    {
        this.regNo = regNo;
    }

    public String getRegIssueDate ()
    {
        return regIssueDate;
    }

    public void setRegIssueDate (String regIssueDate)
    {
        this.regIssueDate = regIssueDate;
    }

    public String getPolicyStartdate ()
    {
        return policyStartdate;
    }

    public void setPolicyStartdate (String policyStartdate)
    {
        this.policyStartdate = policyStartdate;
    }

    public String getPolicyEndDate ()
    {
        return policyEndDate;
    }

    public void setPolicyEndDate (String policyEndDate)
    {
        this.policyEndDate = policyEndDate;
    }

    
    public String getInsurerName() {
		return insurerName;
	}

	public void setInsurerName(String insurerName) {
		this.insurerName = insurerName;
	}

	@Override
	public String toString() {
		return "Result [insurerCode=" + insurerCode + ", insurerName=" + insurerName + ", actStatus=" + actStatus
				+ ", engineNo=" + engineNo + ", policyDetailsId=" + policyDetailsId + ", mappingDoneOn=" + mappingDoneOn
				+ ", policyStatus=" + policyStatus + ", policyNo=" + policyNo + ", vehicleRcId=" + vehicleRcId
				+ ", chasisNo=" + chasisNo + ", regNo=" + regNo + ", regIssueDate=" + regIssueDate
				+ ", policyStartdate=" + policyStartdate + ", policyEndDate=" + policyEndDate + "]";
	}

	
}
			