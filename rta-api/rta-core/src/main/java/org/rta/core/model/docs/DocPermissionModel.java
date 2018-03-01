package org.rta.core.model.docs;

import java.util.List;

import org.rta.core.enums.AlterationCategory;
import org.rta.core.enums.OwnershipType;
import org.rta.core.enums.UserType;

/**
 * @author sohan.maurya
 *
 */
/**
 * @author sohan.maurya
 *
 */
public class DocPermissionModel {
	
	private Integer registrationCategoryType;
	private Integer financeMode;
	private OwnershipType ownershipType;
	private UserType userType;
	private String vehicleClass;
	private String vehicleSubClass;
	private Double invoiceValue;
	private Integer customerAge;
	private Boolean isFinance;
	private Boolean isCustomerDisabled;
	private Boolean isOldVehicle;
	private List<AlterationCategory> alterationCategory;
	private String serviceTypeCode;
	
	
	public Integer getRegistrationCategoryType() {
		return registrationCategoryType;
	}
	public void setRegistrationCategoryType(Integer registrationCategoryType) {
		this.registrationCategoryType = registrationCategoryType;
	}
	public OwnershipType getOwnershipType() {
		return ownershipType;
	}
	public void setOwnershipType(OwnershipType ownershipType) {
		this.ownershipType = ownershipType;
	}
	
    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public String getVehicleClass() {
		return vehicleClass;
	}
	public void setVehicleClass(String vehicleClass) {
		this.vehicleClass = vehicleClass;
	}
	public String getVehicleSubClass() {
		return vehicleSubClass;
	}
	public void setVehicleSubClass(String vehicleSubClass) {
		this.vehicleSubClass = vehicleSubClass;
	}
	public Double getInvoiceValue() {
		return invoiceValue;
	}
	public void setInvoiceValue(Double invoiceValue) {
		this.invoiceValue = invoiceValue;
	}
	public Integer getCustomerAge() {
		return customerAge;
	}
	public void setCustomerAge(Integer customerAge) {
		this.customerAge = customerAge;
	}
	public Boolean getIsFinance() {
		return isFinance;
	}
	public void setIsFinance(Boolean isFinance) {
		this.isFinance = isFinance;
	}
	public Boolean getIsCustomerDisabled() {
		return isCustomerDisabled;
	}
	public void setIsCustomerDisabled(Boolean isCustomerDisabled) {
		this.isCustomerDisabled = isCustomerDisabled;
	}
	
	public Boolean getIsOldVehicle() {
		return isOldVehicle;
	}
	public void setIsOldVehicle(Boolean isOldVehicle) {
		this.isOldVehicle = isOldVehicle;
	}
	public List<AlterationCategory> getAlterationCategory() {
		return alterationCategory;
	}
	public void setAlterationCategory(List<AlterationCategory> alterationCategory) {
		this.alterationCategory = alterationCategory;
	}
	public String getServiceTypeCode() {
		return serviceTypeCode;
	}
	public void setServiceTypeCode(String serviceTypeCode) {
		this.serviceTypeCode = serviceTypeCode;
	}
	public Integer getFinanceMode() {
		return financeMode;
	}
	public void setFinanceMode(Integer financeMode) {
		this.financeMode = financeMode;
	}
	@Override
	public String toString() {
		return "DocPermissionModel [registrationCategoryType=" + registrationCategoryType + ", ownershipType="
				+ ownershipType + ", userType=" + userType + ", vehicleClass=" + vehicleClass + ", vehicleSubClass="
				+ vehicleSubClass + ", invoiceValue=" + invoiceValue + ", customerAge=" + customerAge + ", isFinance="
				+ isFinance + ", isCustomerDisabled=" + isCustomerDisabled + "]";
	}
	
	
}
