package org.rta.core.model.user;

import java.util.List;

import org.rta.core.enums.ServiceType;
import org.rta.core.model.UserActionModel;
import org.rta.core.model.docs.DocSyncModel;
import org.rta.core.model.legalhier.LegalHierModel;

/**
 * @Author sohan.maurya created on Jan 4, 2017.
 */

public class AddressChangeModel extends AddressModel {

    /**
     * 
     */
    private static final long serialVersionUID = 9090005552396315585L;
    private String emailId;
    private String mobileNo;
    private Long withEffectFrom;
    private Long vehicleRcId;
    private ServiceType serviceType;
    private String ownershipTypeCode;
    private String ownershipTypeName;
    private String aadharNumber;
    private String displayName;
    private String co;// care-off
    // for saving permit options : like tranfer or surrender
    private String permitTransferType;
    // companyName, representativeName case of transfer type to company.
    private String companyName;
    private String representativeName;
    private List<DocSyncModel> docList;
    private LegalHierModel legalHierDetails;
    private String qualification;
    private String bloodGroup;
    private String panNumber;
    private Boolean isDifferentlyAbled;
    private String alternateMobileNumber;
    private String applicationNumber;
    private List<UserActionModel> actionModelList;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Long getWithEffectFrom() {
        return withEffectFrom;
    }

    public void setWithEffectFrom(Long withEffectFrom) {
        this.withEffectFrom = withEffectFrom;
    }

    public Long getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(Long vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getOwnershipTypeCode() {
        return ownershipTypeCode;
    }

    public void setOwnershipTypeCode(String ownershipTypeCode) {
        this.ownershipTypeCode = ownershipTypeCode;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getPermitTransferType() {
        return permitTransferType;
    }

    public void setPermitTransferType(String permitTransferType) {
        this.permitTransferType = permitTransferType;
    }

    public String getOwnershipTypeName() {
        return ownershipTypeName;
    }

    public void setOwnershipTypeName(String ownershipTypeName) {
        this.ownershipTypeName = ownershipTypeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public List<DocSyncModel> getDocList() {
        return docList;
    }

    public void setDocList(List<DocSyncModel> docList) {
        this.docList = docList;
    }

    public LegalHierModel getLegalHierDetails() {
        return legalHierDetails;
    }

    public void setLegalHierDetails(LegalHierModel legalHierDetails) {
        this.legalHierDetails = legalHierDetails;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public Boolean getIsDifferentlyAbled() {
        return isDifferentlyAbled;
    }

    public void setIsDifferentlyAbled(Boolean isDifferentlyAbled) {
        this.isDifferentlyAbled = isDifferentlyAbled;
    }
    
    public String getAlternateMobileNumber() {
        return alternateMobileNumber;
    }

    public void setAlternateMobileNumber(String alternateMobileNumber) {
        this.alternateMobileNumber = alternateMobileNumber;
    }

    public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public List<UserActionModel> getActionModelList() {
		return actionModelList;
	}

	public void setActionModelList(List<UserActionModel> actionModelList) {
		this.actionModelList = actionModelList;
	}

	@Override
    public String toString() {
        return "AddressChangeModel [emailId=" + emailId + ", mobileNo=" + mobileNo + ", withEffectFrom="
                + withEffectFrom + ", vehicleRcId=" + vehicleRcId + ", serviceType=" + serviceType
                + ", ownershipTypeCode=" + ownershipTypeCode + ", ownershipTypeName=" + ownershipTypeName
                + ", aadharNumber=" + aadharNumber + ", displayName=" + displayName + ", co=" + co
                + ", permitTransferType=" + permitTransferType + ", companyName=" + companyName
                + ", representativeName=" + representativeName
                + ", legalHierDetails=" + legalHierDetails 
                + ", qualification=" + qualification
                +", bloodGroup=" +bloodGroup + " , panNumber=" + panNumber
                +" , isDifferentlyAbled=" +isDifferentlyAbled 
                +", alternateMobileNumber=" 
                + alternateMobileNumber+" ]";
    }

}
